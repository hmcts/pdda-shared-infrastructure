package uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.framework.jdbc.core.Parameter;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class XhbCppStagingInboundRepository extends AbstractRepository<XhbCppStagingInboundDao>
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(XhbCppStagingInboundRepository.class);

    private static final String TIME_LOADED = "timeLoaded";
    private static final String VALIDATION_STATUS = "validationStatus";
    private static final String PROCESSING_STATUS = "processingStatus";

    public XhbCppStagingInboundRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCppStagingInboundDao> getDaoClass() {
        return XhbCppStagingInboundDao.class;
    }

    public List<XhbCppStagingInboundDao> findNextDocumentByValidationAndProcessingStatus(LocalDateTime timeLoaded,
        String validationStatus, String processingStatus) {
        LOG.debug("findNextDocumentByValidationAndProcessingStatus()");
        Query query = getEntityManager()
            .createNamedQuery("XHB_CPP_STAGING_INBOUND.findNextDocumentByValidationAndProcessingStatus");
        query.setParameter(TIME_LOADED, timeLoaded);
        query.setParameter(VALIDATION_STATUS, Parameter.getPostgresInParameter(validationStatus));
        query.setParameter(PROCESSING_STATUS, Parameter.getPostgresInParameter(processingStatus));
        return query.getResultList();
    }

    public List<XhbCppStagingInboundDao> findNextDocumentByProcessingStatus(LocalDateTime timeLoaded,
        String processingStatus) {
        LOG.debug("findNextDocumentByProcessingStatus()");
        Query query = getEntityManager().createNamedQuery("XHB_CPP_STAGING_INBOUND.findNextDocumentByProcessingStatus");
        query.setParameter(TIME_LOADED, timeLoaded);
        query.setParameter(PROCESSING_STATUS, Parameter.getPostgresInParameter(processingStatus));
        return query.getResultList();
    }

    public List<XhbCppStagingInboundDao> findNextDocument(String validationStatus, String processingStatus) {
        LOG.debug("findNextDocument()");
        Query query = getEntityManager().createNamedQuery("XHB_CPP_STAGING_INBOUND.findNextDocument");
        query.setParameter(VALIDATION_STATUS, Parameter.getPostgresInParameter(validationStatus));
        query.setParameter(PROCESSING_STATUS, Parameter.getPostgresInParameter(processingStatus));
        return query.getResultList();
    }

    /**
     * findNextDocumentByValidationStatus.
     * 
     * @param timeLoaded LocalDateTime
     * @param validationStatus String
     * @return List
     */
    public List<XhbCppStagingInboundDao> findNextDocumentByValidationStatus(LocalDateTime timeLoaded,
        String validationStatus) {
        LOG.debug("findNextDocumentByValidationStatus()");
        Query query = getEntityManager().createNamedQuery("XHB_CPP_STAGING_INBOUND.findNextDocumentByValidationStatus");
        query.setParameter(TIME_LOADED, timeLoaded);
        query.setParameter(VALIDATION_STATUS, Parameter.getPostgresInParameter(validationStatus));
        return query.getResultList();
    }

    public List<XhbCppStagingInboundDao> findNextDocumentTest(LocalDateTime timeLoaded, String validationStatus) {
        Query query = getEntityManager().createQuery("SELECT o from XHB_CPP_STAGING_INBOUND o WHERE "
            + "o.timeLoaded >= :timeLoaded AND (o.obsInd IS NULL OR o.obsInd='N') "
            + "AND o.validationStatus = :validationStatus ORDER by o.timeLoaded");
        query.setParameter(TIME_LOADED, timeLoaded);
        query.setParameter(VALIDATION_STATUS, Parameter.getPostgresInParameter(validationStatus));
        return query.getResultList();
    }

    public List<XhbCppStagingInboundDao> findUnrespondedCppMessages() {
        Query query = getEntityManager().createNamedQuery("XHB_CPP_STAGING_INBOUND.findUnrespondedCPPMessages");
        return query.getResultList();
    }

}
