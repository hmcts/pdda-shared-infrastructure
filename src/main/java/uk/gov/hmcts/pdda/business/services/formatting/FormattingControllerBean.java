package uk.gov.hmcts.pdda.business.services.formatting;

import jakarta.ejb.ApplicationException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.framework.scheduler.RemoteTask;
import uk.gov.hmcts.pdda.business.AbstractControllerBean;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListDao;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingDao;
import uk.gov.hmcts.pdda.business.vos.formatting.FormattingValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Stateless
@Service
@Transactional
@LocalBean
@ApplicationException(rollback = true)
public class FormattingControllerBean extends AbstractControllerBean implements RemoteTask {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(FormattingControllerBean.class);

    private static final String ENTERED = " : entered";
    private static final String METHOD_SUFFIX = ") - ";

    private FormattingServices formattingServices;

    public FormattingControllerBean(EntityManager entityManager) {
        super(entityManager);
    }

    public FormattingControllerBean() {
        super();
    }

    // /////////////////////////////////////////////////////////////////////////
    // DEFINE ALL REMOTE METHODS...
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Implementation of RemoteTask so that this process is called by the timer process. This method
     * must have the same transactional behaviour as processFormattingDocument.
     */
    @Override
    public void doTask() {
        final String methodName = "doTask() - ";
        LOG.debug(methodName + ENTERED);
        processFormattingDocument();
    }

    /**
     * Format the xml.
     * 
     * @param formattingValue formattingValue
     * @param listStartDate LocalDateTime
     * @param xml String
     * @return an array containg the formated document data
     */
    public byte[] formatDocument(final FormattingValue formattingValue, LocalDateTime listStartDate, String xml) {
        final String methodName = "formatDocument(" + formattingValue.getDistributionType() + ","
            + formattingValue.getMimeType() + "," + formattingValue.getDocumentType() + ","
            + formattingValue.getMajorVersion() + "," + formattingValue.getLocale().getLanguage() + ","
            + formattingValue.getLocale().getCountry() + "," + xml + "," + formattingValue.getCourtId() + METHOD_SUFFIX;
        LOG.debug(methodName + ENTERED);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

        String listType = formattingValue.getDocumentType().substring(0, 1);

        // Get the latest list (the query is ordered so take the first one returned in the list)
        XhbCppListDao cppList = getLatestCppList(formattingValue.getCourtId(), listType, listStartDate);

        getFormattingServices()
            .processDocument(new FormattingValue(formattingValue.getDistributionType(), formattingValue.getMimeType(),
                formattingValue.getDocumentType(), formattingValue.getMajorVersion(), formattingValue.getMajorVersion(),
                formattingValue.getLocale().getLanguage(), formattingValue.getLocale().getCountry(),
                new StringReader(xml), out, formattingValue.getCourtId(), cppList), getEntityManager());

        return out.toByteArray();
    }

    /**
     * Only remotely accessible method, this method processes the next available document if one is
     * available. If no document is available, then method will return.
     * 
     * @throws RuntimeException if any <code>RuntimeException</code> is thrown during processing of the
     *         document, after first having the status of the document set to failed.
     */
    public void processFormattingDocument() {
        final String methodName = "processFormattingDocument() - ";
        LOG.debug(methodName + ENTERED);
        XhbFormattingDao formattingDocument = getFormattingServices().getNextFormattingDocument();

        if (formattingDocument != null) {
            final Integer formattingDocumentId = formattingDocument.getFormattingId();
            try {
                processFormattingDocument(formattingDocument);
                // indicate that formatting was successful...
                updateFormattingDocumentStatus(formattingDocumentId, true);
            } catch (final RuntimeException e) {
                // indicate that formatting failed...
                updateFormattingDocumentStatus(formattingDocumentId, false);
                // if it's a merge issue then we need to update the cppformatting row as well
                String errorMessage = e.getCause().getMessage();
                if (errorMessage.contains("Error Merging: ")) {
                    int startIndex = errorMessage.indexOf("Error Merging: ") + 15;
                    int endIndex = errorMessage.indexOf(':', startIndex) - 1;
                    Integer cppFormattingId = Integer.valueOf(errorMessage.substring(startIndex, endIndex));
                    updateCppFormatting(cppFormattingId, errorMessage);
                }
                throw e;
            }
        } else {
            LOG.debug("processFormattingDocument() - No documents to process");
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // DEFINE ALL LOCAL METHODS...
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Process the document whose primary key value is passed in.
     * 
     * <p>Only accessible via the EJB local interface.
     * 
     * @param formattingDocument XhbFormattingDao
     */
    public void processFormattingDocument(final XhbFormattingDao formattingDocument) {
        final String methodName = "processFormattingDocument(" + formattingDocument.getFormattingId() + METHOD_SUFFIX;
        LOG.debug(methodName + ENTERED);

        // Get the clob data
        Optional<XhbClobDao> clobDao = getFormattingServices().getClob(formattingDocument.getXmlDocumentClobId());
        if (clobDao.isPresent()) {
            processDocument(formattingDocument, clobDao.get().getClobData());
        }
    }

    private void processDocument(final XhbFormattingDao formattingDocument, String clobData) {
        try (Reader reader = new StringReader(clobData); OutputStream outputStream = new ByteArrayOutputStream();) {
            final FormattingValue value =
                getFormattingServices().getFormattingValue(formattingDocument, reader, outputStream);
            getFormattingServices().processDocument(value, getEntityManager());
        } catch (IOException ex) {
            LOG.debug("IO Error reading clob " + ex.getMessage());
        }
    }

    /**
     * Update the status of the formatting entity identified by the passed in formatting id primary key
     * to indicate success or failure of formatting of the document.
     * 
     * <p>Only accessible via the EJB local interface.
     * 
     * @param formattingId Primary key of formatting entity we are updating.
     * @param success <i>true</i> if the document was successfully formatted, or <i>false</i> if not
     *        successfully formatted.
     */
    public void updateFormattingDocumentStatus(final Integer formattingId, final boolean success) {
        final String methodName = "updateCppFormatting(" + formattingId + "," + success + METHOD_SUFFIX;
        LOG.debug(methodName + ENTERED);

        // validation of input parameters is performed in the called method...
        getFormattingServices().updateFormattingStatus(formattingId, success);
    }

    /**
     * Update the status of the formatting entity identified by the passed in formatting id primary key
     * to indicate success or failure of formatting of the document.
     * 
     * <p>Only accessible via the EJB local interface.
     * 
     * @param cppFormattingId Primary key of formatting entity we are updating.
     * @param errorMessage String
     */
    public void updateCppFormatting(final Integer cppFormattingId, final String errorMessage) {
        final String methodName = "updateCppFormatting(" + cppFormattingId + "," + errorMessage + ") - ";
        LOG.debug(methodName + ENTERED);

        // validation of input parameters is performed in the called method...
        getFormattingServices().updateCppFormatting(cppFormattingId, "MF", errorMessage);
    }

    private XhbCppListDao getLatestCppList(Integer courtId, String listType, LocalDateTime listStartDate) {
        List<XhbCppListDao> cppList =
            getXhbCppListRepository().findByCourtCodeAndListTypeAndListDate(courtId, listType, listStartDate);
        XhbCppListDao result = cppList != null && !cppList.isEmpty() ? cppList.get(0) : null;
        if (result != null && result.getListClobId() != null) {
            // Populate the listClob on the XhbCppListDAO
            Optional<XhbClobDao> listClob = getFormattingServices().getClob(result.getListClobId());
            if (listClob.isPresent()) {
                result.setListClob(listClob.get());
            }
        }

        return result;
    }

    private FormattingServices getFormattingServices() {
        if (formattingServices == null) {
            formattingServices = new FormattingServices(getEntityManager());
        }
        return formattingServices;
    }
}
