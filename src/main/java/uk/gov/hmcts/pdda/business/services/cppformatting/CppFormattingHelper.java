package uk.gov.hmcts.pdda.business.services.cppformatting;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingDao;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingDao;

import java.time.LocalDateTime;

/**
 * <p>
 * Title: CppFormattingHelper.
 * </p>
 * <p>
 * Description: Helper class for CppFormatting
 * </p>
 * <p>
 * Copyright: Copyright (c) 2022
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Chris
 * @version 1.0
 */
public class CppFormattingHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CppFormattingHelper.class);

    private static final String DOC_TYPE_PUBLIC_DISPLAY = "PD";

    public static final String FORMAT_STATUS_SUCCESS = "MS";

    public static final String FORMAT_STATUS_FAIL = "MF";

    public static final String FORMAT_STATUS_NOT_PROCESSED = "ND";

    /**
     * Description: Returns the latest unprocessed XHB_CPP_FORMATTING record for Public Display.
     * 
     * @param courtId Integer 
     * @return XhbCppFormattingDAO 
     * @throws CppFormattingControllerException Exception
     */
    public XhbCppFormattingDao getLatestPublicDisplayDocument(final Integer courtId,
        final EntityManager entityManager) {
        String methodName = "getLatestPublicDisplayDocument(" + courtId + ")";
        LOG.debug(methodName + " called");

        XhbCppFormattingRepository repo = new XhbCppFormattingRepository(entityManager);
        return repo.getLatestDocumentByCourtIdAndType(courtId, DOC_TYPE_PUBLIC_DISPLAY,
            LocalDateTime.now());
    }

    /**
     * Description: Updates the XHB_CPP_FORMATTING object provided with a new status.
     * 
     * @param cppFormattingDao XhbCppFormattingDao
     * @param newStatus String
     */
    public void updateCppFormattingStatus(XhbCppFormattingDao cppFormattingDao, String newStatus,
        final EntityManager entityManager) {
        String methodName = "updateCppFormattingStatus(" + cppFormattingDao + ") - ";
        LOG.debug(methodName + " called");

        // Validate the XhbCppFormattingDAO record has an Id
        if (cppFormattingDao.getPrimaryKey() == null) {
            throw new IllegalArgumentException("cppFormattingId cannot be null");
        }
        // Update the Format Status with the new value
        cppFormattingDao.setFormatStatus(newStatus);

        // Update the database record
        XhbCppFormattingRepository repo = new XhbCppFormattingRepository(entityManager);
        repo.update(cppFormattingDao);
    }
    
    
    public static XhbFormattingDao createXhbFormattingRecord(Integer courtId, LocalDateTime dateIn,
        String documentType, String language) {
        XhbFormattingDao xfbv = new XhbFormattingDao();
        xfbv.setCourtId(courtId);
        xfbv.setDateIn(dateIn);
        xfbv.setDistributionType("FTP");
        xfbv.setDocumentType(documentType);
        xfbv.setFormatStatus(CppFormattingHelper.FORMAT_STATUS_NOT_PROCESSED);
        xfbv.setMimeType("HTM");
        xfbv.setXmlDocumentClobId(Long.valueOf(0));
        xfbv.setCountry("GB");
        xfbv.setLanguage(language);
        return xfbv;
    }
}
