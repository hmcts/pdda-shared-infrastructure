package uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3;

import jakarta.ejb.ApplicationException;
import jakarta.ejb.EJBException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.services.validation.ValidationException;
import uk.gov.hmcts.pdda.business.services.validation.ValidationResult;
import uk.gov.hmcts.pdda.business.services.validation.ValidationService;

import java.util.List;
import java.util.Optional;

@Stateless
@Service
@Transactional
@LocalBean
@ApplicationException(rollback = true)
public class CppStagingInboundControllerBean extends AbstractCppStagingInboundControllerBean
    implements CppStagingInboundController {

    private static final long serialVersionUID = -1482124759093214736L;

    private static final Logger LOG = LoggerFactory.getLogger(CppStagingInboundControllerBean.class);


    public CppStagingInboundControllerBean(EntityManager entityManager, XhbConfigPropRepository xhbConfigPropRepository,
        CppStagingInboundHelper cppStagingInboundHelper, XhbCourtRepository xhbCourtRepository,
        XhbClobRepository xhbClobRepository, ValidationService validationService) {
        super(entityManager, xhbConfigPropRepository, cppStagingInboundHelper, xhbCourtRepository, xhbClobRepository,
            validationService);
    }

    public CppStagingInboundControllerBean() {
        super();
    }

    /**
     * <p>
     * Returns the latest unprocessed XHB_CPP_STAGING_INBOUND record for processing.
     * </p>
     * 
     * @return CppStagingInboundDao
     * @throws CppStagingInboundControllerException Exception
     * 
     */
    @Override
    public List<XhbCppStagingInboundDao> getLatestUnprocessedDocument() {
        String methodName = "getLatestUnprocessedDocument() - ";
        LOG.debug(methodName + ENTERED);

        try {
            LOG.debug("EM is null or is it:" + getEntityManager().toString());
            LOG.info("Can you see this?");
            LOG.debug("Checking for null");
            return getCppStagingInboundHelper()
                .findNextDocumentByStatus(CppStagingInboundHelper.VALIDATION_STATUS_NOTPROCESSED, null);
        } catch (CppStagingInboundControllerException cfce) {
            // Any error handling needed here now as part of ejb3 or will annotation at top
            // of class suffice
            LOG.error(cfce.getMessage());
            throw cfce;
        }
    }

    /**
     * <p>
     * Returns the latest record from XHB_CPP_STAGING_INBOUND that has been validated successfully and
     * not processed.
     * </p>
     * 
     * @return CppStagingInboundDao
     * @throws CppStagingInboundControllerException Exception
     * 
     */
    @Override
    public List<XhbCppStagingInboundDao> getNextValidatedDocument() {
        String methodName = "getNextValidatedDocument() - ";
        LOG.debug(methodName + ENTERED);

        try {
            // Find documents where VALIDATION_STATUS='VS' and PROCESSING_STATUS='NP'
            return getCppStagingInboundHelper().findNextDocumentByStatus(
                CppStagingInboundHelper.VALIDATION_STATUS_SUCCESS,
                CppStagingInboundHelper.PROCESSING_STATUS_NOTPROCESSED);
        } catch (CppStagingInboundControllerException cfce) {
            LOG.error(cfce.getMessage());
            throw cfce;
        }
    }

    /**
     * <p>
     * Returns the earliest XHB_CPP_STAGING_INBOUND from today that is to be validated.
     * </p>
     * 
     * @return CppStagingInboundDao
     * @throws CppStagingInboundControllerException Exception
     * 
     */
    @Override
    public List<XhbCppStagingInboundDao> getNextDocumentToValidate() {
        String methodName = "getNextDocumentToValidate() - ";
        LOG.debug(methodName + ENTERED);
        try {
            String processingStatus = "";
            // Not doing any searches by processingStatus but passing
            // a necessary empty string
            return getCppStagingInboundHelper()
                .findNextDocumentByStatus(CppStagingInboundHelper.VALIDATION_STATUS_INPROCESS, processingStatus);
        } catch (CppStagingInboundControllerException cfce) {
            LOG.error(cfce.getMessage());
            throw cfce;
        }
    }

    /**
     * Updates an XHB_CPP_STAGING_INBOUND record with a status of successfully validated.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public void updateStatusSuccess(XhbCppStagingInboundDao cppStagingInboundDao, String userDisplayName) {
        String methodName = "updateStatusSuccess(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        try {
            cppStagingInboundDao.setProcessingStatus(CppStagingInboundHelper.PROCESSING_STATUS_NOTPROCESSED);
            cppStagingInboundDao.setValidationStatus(CppStagingInboundHelper.VALIDATION_STATUS_SUCCESS);
            cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
            getCppStagingInboundHelper().updateCppStagingInbound(cppStagingInboundDao, userDisplayName);

        } catch (EJBException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an XHB_CPP_STAGING_INBOUND record with a status of validation failed.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public void updateStatusFailed(XhbCppStagingInboundDao cppStagingInboundDao, String reasonForFail,
        String userDisplayName) {
        String methodName = "updateStatusFailed(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        try {
            String reasonForFailToUse = reasonForFail;
            cppStagingInboundDao.setValidationStatus(CppStagingInboundHelper.VALIDATION_STATUS_FAIL);

            if (reasonForFailToUse.length() > REASON_LIMIT) {
                reasonForFailToUse = reasonForFailToUse.substring(0, REASON_LIMIT - 1);
            }
            cppStagingInboundDao.setValidationErrorMessage(reasonForFailToUse);
            // Column limited to 4000 chars so truncate to avoid unrecoverable error
            cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
            getCppStagingInboundHelper().updateCppStagingInbound(cppStagingInboundDao, userDisplayName);

        } catch (EJBException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an XHB_CPP_STAGING_INBOUND record with a status of In Progress.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public Optional<XhbCppStagingInboundDao> updateStatusInProcess(XhbCppStagingInboundDao cppStagingInboundDao,
        String userDisplayName) {
        String methodName =
            "updateStatusInProcess(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        try {
            cppStagingInboundDao.setValidationStatus(CppStagingInboundHelper.VALIDATION_STATUS_INPROCESS);
            cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
            return getCppStagingInboundHelper().updateCppStagingInbound(cppStagingInboundDao, userDisplayName);

        } catch (EJBException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an XHB_CPP_STAGING_INBOUND record with a processing status of fail.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public void updateStatusProcessingFail(XhbCppStagingInboundDao cppStagingInboundDao, String reasonForFail,
        String userDisplayName) {
        String methodName =
            "updateStatusProcessingFail(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        String reasonForFailToUse = reasonForFail;
        cppStagingInboundDao.setProcessingStatus(CppStagingInboundHelper.PROCESSING_STATUS_FAIL);
        if (reasonForFailToUse.length() > REASON_LIMIT) {
            reasonForFailToUse = reasonForFailToUse.substring(0, REASON_LIMIT - 1);
        }
        cppStagingInboundDao.setValidationErrorMessage(reasonForFailToUse);
        // Column limited to 4000 chars so truncate to avoid unrecoverable error
        cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
        getCppStagingInboundHelper().updateCppStagingInbound(cppStagingInboundDao, userDisplayName);
    }

    /**
     * Updates an XHB_CPP_STAGING_INBOUND record with a processing status of fail.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public void updateStatusProcessingSuccess(XhbCppStagingInboundDao cppStagingInboundDao, String userDisplayName) {
        String methodName =
            "updateStatusProcessingSuccess(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        try {
            cppStagingInboundDao.setProcessingStatus(CppStagingInboundHelper.PROCESSING_STATUS_SENT);
            cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
            getCppStagingInboundHelper().updateCppStagingInbound(cppStagingInboundDao, userDisplayName);

        } catch (EJBException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Validates document from XHB_STAGING_INBOUND where the current VALIDATION_STATUS='IP' This
     * document is validated as follows: 1. The DOCUMENT_NAME is checked to follow a valid format 2. The
     * DOCUMENT_TYPE is checked to be valid 3. The appropriate schema to validate the XML against will
     * be determined 4. Validation of the XML will be done against the appropriate schema.
     * 
     * @param cppStagingInboundDao CppStagingInboundDao
     * @param userDisplayName String
     * 
     */
    @Override
    public boolean validateDocument(XhbCppStagingInboundDao cppStagingInboundDao, String userDisplayName)
        throws ValidationException {
        String methodName = "validateDocument(" + cppStagingInboundDao + "," + userDisplayName + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);
        // Get schema to validate against
        String schemaName = getSchemaName(cppStagingInboundDao.getDocumentType());
        try {
            if (DocumentValidationUtils.isValidDocumentName(cppStagingInboundDao.getDocumentName())) {
                LOG.debug("Document Name is valid");
            } else {
                updateStatusFailed(cppStagingInboundDao, "Document Name is invalid", userDisplayName);
                return false;
            }
            if (DocumentValidationUtils.isValidDocumentType(cppStagingInboundDao.getDocumentType())) {
                LOG.debug("Document Type is valid");
            } else {
                updateStatusFailed(cppStagingInboundDao, "Document Type is invalid", userDisplayName);
                return false;
            }

            // Get the XML
            String xmlToValidate = getClobXmlAsString(cppStagingInboundDao.getClobId());

            // Validate the XML
            ValidationResult validDoc = getValidationService().validate(xmlToValidate, SCHEMA_DIR_DEFAULT + schemaName);
            if (validDoc.isValid()) {
                LOG.debug("Document XML is valid");
            } else {
                updateStatusFailed(cppStagingInboundDao,
                    "Validation failed: Schema name:" + schemaName + "; error::" + validDoc.toString(),
                    userDisplayName);
                return false;
            }

            // Do a check to make sure the court is a cpp court if not we want to fail
            List<XhbCourtDao> courts =
                getXhbCourtRepository().findByCrestCourtIdValue(cppStagingInboundDao.getCourtCode());
            if (CourtUtils.isCppCourt(courts)) {
                if (CppDocumentTypes.WP == CppDocumentTypes.fromString(cppStagingInboundDao.getDocumentType())
                    && !CourtUtils.hasCourtSites(xmlToValidate)) {
                    updateStatusFailed(cppStagingInboundDao, "Validation failed: error:: No court sites in document ",
                        userDisplayName);
                    return false;
                } else {
                    updateStatusSuccess(cppStagingInboundDao, userDisplayName);
                    return true;
                }
            } else {
                updateStatusFailed(cppStagingInboundDao, "Validation failed: error:: CPP court flag not set ",
                    userDisplayName);
                // if its iwp and has zero courtsites the fail validation
                return true;
            }

        } catch (ValidationException ve) {
            updateStatusFailed(cppStagingInboundDao,
                "Validation failed: Schema name:" + schemaName + "; error::" + ve.getCause().getMessage(),
                userDisplayName);
        }
        return false;
    }

    /**
     * Given a clob Id get the Xml.
     * 
     * @param clobId Long
     * @return String
     * 
     */
    @Override
    public String getClobXmlAsString(Long clobId) {
        String methodName = "getClobXmlAsString(" + clobId.longValue() + METHOD_NAME_SUFFIX;
        LOG.debug(methodName + ENTERED);

        Optional<XhbClobDao> clobObj = getXhbClobRepository().findById(clobId);
        if (clobObj.isPresent()) {
            return clobObj.get().getClobData();
        } else {
            LOG.debug("There is no CLOB object returned for clobId=" + clobId);
            return null;
        }
    }
}
