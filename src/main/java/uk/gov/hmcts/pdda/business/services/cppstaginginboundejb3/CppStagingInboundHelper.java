package uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundRepository;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title: CppStagingInboundHelper.
 * </p>
 * <p>
 * Description: Provides and abstract layer between the session facade and the maintainer class. It
 * is used to construct the necessary value objects and contains any business logic.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Scott Atwell
 * @version 1.0
 */
public class CppStagingInboundHelper implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CppStagingInboundHelper.class);

    private static final long serialVersionUID = 1L;

    public static final String VALIDATION_STATUS_SUCCESS = "VS";

    public static final String VALIDATION_STATUS_FAIL = "VF";

    public static final String VALIDATION_STATUS_INPROCESS = "IP";

    public static final String VALIDATION_STATUS_NOTPROCESSED = "NP";

    public static final String PROCESSING_STATUS_NOTPROCESSED = "NP";

    public static final String PROCESSING_STATUS_FAIL = "PF";

    public static final String PROCESSING_STATUS_SENT = "SP";

    public static final String ACKNOWLEDGMENT_STATUS_SENT = "AS";

    private static final String CALLED = " called";

    private Integer numberOfDocsToProcess;

    private String methodName;

    private EntityManager em;

    private XhbCppStagingInboundRepository cppStagingInboundRepository;


    /**
     * Default constructor that instantiate the necessary maintainers.
     */
    public CppStagingInboundHelper() {
        // Default constructor
        super();
    }

    // This is called dynamically
    public CppStagingInboundHelper(EntityManager entityManager) {
        this(entityManager, new XhbConfigPropRepository(entityManager));
    }

    public CppStagingInboundHelper(EntityManager entityManager,
        XhbConfigPropRepository xhbConfigPropRepository) {
        this();
        LOG.debug("Constructor...");
        ArrayList<XhbConfigPropDao> properties = null;
        try {
            properties = (ArrayList<XhbConfigPropDao>) xhbConfigPropRepository
                .findByPropertyName("STAGING_DOCS_TO_PROCESS");
        } catch (Exception e) {
            LOG.debug("Error...{}", e.getMessage());
        }

        try {
            if (properties == null) {
                properties = (ArrayList<XhbConfigPropDao>) xhbConfigPropRepository
                    .findByPropertyName("STAGING_DOCS_TO_PROCESS");
            }
        } catch (Exception e) {
            LOG.debug("Error2...{}", e.getMessage());
        }

        if (properties != null && !properties.isEmpty()) {
            numberOfDocsToProcess = Integer.parseInt(properties.get(0).getPropertyValue());
        } else {
            numberOfDocsToProcess = 1;
        }

        // Set entity manager for this instance
        this.em = entityManager;
    }

    /**
     * Description: Returns the latest unprocessed XHB_CPP_STAGING_INBOUND record.
     * 
     * @return List
     * @throws CppStagingInboundControllerException Exception
     */
    public List<XhbCppStagingInboundDao> findNextDocumentByStatus(String validationStatus,
        String processingStatus) {
        methodName = "findNextDocumentByStatus()";
        LOG.debug(methodName + CALLED);
        LOG.debug("Helloo - Finding a document in " + methodName);
        List<XhbCppStagingInboundDao> toReturn = new ArrayList<>();

        List<XhbCppStagingInboundDao> docs =
            new XhbCppStagingInboundRepository(em).findNextDocumentByValidationAndProcessingStatus(
                LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), validationStatus,
                processingStatus);
        if (docs != null && !docs.isEmpty()) {
            LOG.debug("Some docs were found");
            for (int i = 0; i < numberOfDocsToProcess; i++) {
                if (i >= docs.size()) {
                    break;
                }
                Optional<XhbCppStagingInboundDao> cppSI =
                    getCppStagingInboundRepository().findById(docs.get(i).getCppStagingInboundId());
                if (cppSI.isPresent()) {
                    toReturn.add(cppSI.get());
                }
            }
        } else {
            LOG.debug("No docs were found");
        }

        return toReturn;
    }

    /**
     * findUnrespondedCPPMessages.
     * 
     * @return List
     */
    public List<XhbCppStagingInboundDao> findUnrespondedCppMessages() {
        methodName = "findUnrespondedCPPMessages()";
        LOG.debug(methodName + CALLED);
        return getCppStagingInboundRepository().findUnrespondedCppMessages();
    }

    /**
     * Description: Updates the XHB_CPP_STAGING_INBOUND object provided with a new status for either
     * or both of: ValidationStatus and ProcessingStatus.
     * 
     * @param cppStagingInboundDao XhbCppStagingInboundDao
     * @param userDisplayName String
     */
    public Optional<XhbCppStagingInboundDao> updateCppStagingInbound(
        XhbCppStagingInboundDao cppStagingInboundDao, String userDisplayName) {
        methodName =
            "updateCppStagingInbound(" + cppStagingInboundDao + "," + userDisplayName + ") - ";
        LOG.debug(methodName + CALLED);
        cppStagingInboundDao.setLastUpdatedBy(userDisplayName);
        return getCppStagingInboundRepository().update(cppStagingInboundDao);
    }

    private XhbCppStagingInboundRepository getCppStagingInboundRepository() {
        if (cppStagingInboundRepository == null) {
            cppStagingInboundRepository = new XhbCppStagingInboundRepository(em);
        }
        return cppStagingInboundRepository;
    }

}
