package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.AbstractControllerBean;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.database.query.VipDisplayCourtRoomQuery;
import uk.gov.hmcts.pdda.business.services.publicdisplay.database.query.VipDisplayDocumentQuery;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayNotifier;


@SuppressWarnings("PMD.ExcessiveParameterList")
public abstract class AbstractPdConfigReposControllerBean extends AbstractControllerBean {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPdConfigReposControllerBean.class);

    private PublicDisplayNotifier publicDisplayNotifier;
    private XhbCourtSiteRepository xhbCourtSiteRepository;
    private XhbDisplayRepository xhbDisplayRepository;
    private XhbRotationSetsRepository xhbRotationSetsRepository;
    private XhbDisplayDocumentRepository xhbDisplayDocumentRepository;
    private DisplayRotationSetDataHelper displayRotationSetDataHelper;
    private VipDisplayDocumentQuery vipDisplayDocumentQuery;
    private VipDisplayCourtRoomQuery vipDisplayCourtRoomQuery;
    private VipCourtRoomsQuery vipCourtRoomsQuery;

    protected AbstractPdConfigReposControllerBean() {
        super();
    }

    protected AbstractPdConfigReposControllerBean(EntityManager entityManager) {
        super(entityManager);
    }

    protected AbstractPdConfigReposControllerBean(EntityManager entityManager, XhbClobRepository xhbClobRepository,
        XhbCourtRepository xhbCourtRepository, XhbConfigPropRepository xhbConfigPropRepository,
        XhbCppFormattingRepository xhbCppFormattingRepository, XhbRotationSetsRepository xhbRotationSetsRepository,
        XhbDisplayRepository xhbDisplayRepository, PublicDisplayNotifier publicDisplayNotifier,
        VipDisplayDocumentQuery vipDisplayDocumentQuery, VipDisplayCourtRoomQuery vipDisplayCourtRoomQuery) {
        super(entityManager, xhbClobRepository, xhbCourtRepository, xhbConfigPropRepository,
            xhbCppFormattingRepository);
        this.xhbRotationSetsRepository = xhbRotationSetsRepository;
        this.xhbDisplayRepository = xhbDisplayRepository;
        this.publicDisplayNotifier = publicDisplayNotifier;
        this.vipDisplayDocumentQuery = vipDisplayDocumentQuery;
        this.vipDisplayCourtRoomQuery = vipDisplayCourtRoomQuery;
    }
    
    /**
     * Returns the publicDisplayNotifier object, initialising if currently null.
     * 
     * @return PublicDisplayNotifier
     */
    protected PublicDisplayNotifier getPublicDisplayNotifier() {
        if (publicDisplayNotifier == null) {
            publicDisplayNotifier = new PublicDisplayNotifier();
        }
        return publicDisplayNotifier;
    }

    /**
     * Returns the xhbRotationSetsRepository object, initialising if currently null.
     * 
     * @return XhbRotationSetsRepository
     */
    protected XhbRotationSetsRepository getXhbRotationSetsRepository() {
        if (xhbRotationSetsRepository == null) {
            xhbRotationSetsRepository = new XhbRotationSetsRepository(getEntityManager());
        }
        return xhbRotationSetsRepository;
    }

    /**
     * Returns the xhbDisplayDocumentRepository object, initialising if currently null.
     * 
     * @return XhbDisplayDocumentRepository
     */
    protected XhbDisplayDocumentRepository getXhbDisplayDocumentRepository() {
        if (xhbDisplayDocumentRepository == null) {
            xhbDisplayDocumentRepository = new XhbDisplayDocumentRepository(getEntityManager());
        }
        return xhbDisplayDocumentRepository;
    }

    /**
     * Returns the xhbDisplayRepository object, initialising if currently null.
     * 
     * @return XhbDisplayRepository
     */
    protected XhbDisplayRepository getXhbDisplayRepository() {
        if (xhbDisplayRepository == null) {
            xhbDisplayRepository = new XhbDisplayRepository(getEntityManager());
        }
        return xhbDisplayRepository;
    }

    /**
     * Returns the xhbCourtSiteRepository object, initialising if currently null.
     * 
     * @return XhbCourtSiteRepository
     */
    protected XhbCourtSiteRepository getXhbCourtSiteRepository() {
        if (xhbCourtSiteRepository == null) {
            xhbCourtSiteRepository = new XhbCourtSiteRepository(getEntityManager());
        }
        return xhbCourtSiteRepository;
    }

    /**
     * Returns the displayRotationSetDataHelper object, initialising if currently null.
     * 
     * @return DisplayRotationSetDataHelper
     */
    protected DisplayRotationSetDataHelper getDisplayRotationSetDataHelper() {
        if (displayRotationSetDataHelper == null) {
            displayRotationSetDataHelper = new DisplayRotationSetDataHelper();
        }
        return displayRotationSetDataHelper;
    }

    protected VipDisplayDocumentQuery getVipDisplayDocumentQuery() {
        if (vipDisplayDocumentQuery == null) {
            return new VipDisplayDocumentQuery(getEntityManager());
        }
        return vipDisplayDocumentQuery;
    }

    protected VipDisplayCourtRoomQuery getVipDisplayCourtRoomQuery() {
        if (vipDisplayCourtRoomQuery == null) {
            return new VipDisplayCourtRoomQuery(getEntityManager());
        }
        return vipDisplayCourtRoomQuery;
    }

    protected VipCourtRoomsQuery getVipCourtRoomsQuery(boolean multiSite) {
        if (vipCourtRoomsQuery == null) {
            return new VipCourtRoomsQuery(getEntityManager(), multiSite);
        }
        return vipCourtRoomsQuery;
    }

    @PreDestroy
    protected void shutdown() {
        LOG.debug("shutdown()");
        if (publicDisplayNotifier != null) {
            publicDisplayNotifier.close();
        }
    }
}
