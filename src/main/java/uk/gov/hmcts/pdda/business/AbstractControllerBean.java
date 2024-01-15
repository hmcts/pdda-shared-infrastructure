package uk.gov.hmcts.pdda.business;

import com.pdda.hb.jpa.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListRepository;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingRepository;

public class AbstractControllerBean {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractControllerBean.class);

    private EntityManager entityManager;
    private XhbClobRepository xhbClobRepository;
    private XhbCourtRepository xhbCourtRepository;
    private XhbConfigPropRepository xhbConfigPropRepository;
    private XhbCppFormattingRepository xhbCppFormattingRepository;
    private XhbCppListRepository xhbCppListRepository;
    private XhbFormattingRepository xhbFormattingRepository;

    // For unit tests.
    protected AbstractControllerBean(EntityManager entityManager, XhbClobRepository xhbClobRepository,
        XhbCourtRepository xhbCourtRepository, XhbConfigPropRepository xhbConfigPropRepository,
        XhbCppFormattingRepository xhbCppFormattingRepository) {
        this(entityManager);
        this.xhbClobRepository = xhbClobRepository;
        this.xhbCourtRepository = xhbCourtRepository;
        this.xhbConfigPropRepository = xhbConfigPropRepository;
        this.xhbCppFormattingRepository = xhbCppFormattingRepository;
    }

    protected AbstractControllerBean(EntityManager entityManager) {
        this();
        this.entityManager = entityManager;
    }

    protected AbstractControllerBean() {
        // protected constructor
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            LOG.debug("getEntityManager() - Creating new entityManager");
            entityManager = EntityManagerUtil.getEntityManager();
        }
        return entityManager;
    }

    protected XhbClobRepository getXhbClobRepository() {
        if (xhbClobRepository == null) {
            xhbClobRepository = new XhbClobRepository(getEntityManager());
        }
        return xhbClobRepository;
    }

    protected XhbCourtRepository getXhbCourtRepository() {
        if (xhbCourtRepository == null) {
            xhbCourtRepository = new XhbCourtRepository(getEntityManager());
        }
        return xhbCourtRepository;
    }

    protected XhbConfigPropRepository getXhbConfigPropRepository() {
        if (xhbConfigPropRepository == null) {
            xhbConfigPropRepository = new XhbConfigPropRepository(getEntityManager());
        }
        return xhbConfigPropRepository;
    }
    
    protected XhbCppFormattingRepository getXhbCppFormattingRepository() {
        if (xhbCppFormattingRepository == null) {
            xhbCppFormattingRepository = new XhbCppFormattingRepository(getEntityManager());
        }
        return xhbCppFormattingRepository;
    }
    
    /**
     * Retrieves a reference to the xhbCppListRepository.
     * 
     * @return XhbCppListRepository
     */
    protected XhbCppListRepository getXhbCppListRepository() {
        if (xhbCppListRepository == null) {
            xhbCppListRepository = new XhbCppListRepository(getEntityManager());
        }
        return xhbCppListRepository;
    }
    
    /**
     * Retrieves a reference to the xhbFormattingRepository.
     * 
     * @return XhbFormattingRepository
     */
    protected XhbFormattingRepository getXhbFormattingRepository() {
        if (xhbFormattingRepository == null) {
            xhbFormattingRepository = new XhbFormattingRepository(getEntityManager());
        }
        return xhbFormattingRepository;
    }
}
