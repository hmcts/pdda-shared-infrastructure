package uk.gov.hmcts.pdda.business.services.publicdisplay.database.query;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdRepository;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.VipDisplayConfigurationDisplayDocument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title: VIP display documents for court site query.
 * </p>
 * <p>
 * Description: This runs the stored procedure that for a given court site, it finds all display
 * documents configured for the View Information Pages.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Bal Bhamra
 * @version $Id: VIPDisplayDocumentQuery.java,v 1.2 2005/11/17 10:55:48 bzjrnl Exp $
 */

public class VipDisplayDocumentQuery {
    /** Logger object. */
    private static final Logger LOG = LoggerFactory.getLogger(VipDisplayDocumentQuery.class);

    private final EntityManager entityManager;

    private XhbDisplayRepository xhbDisplayRepository;
    private XhbDisplayLocationRepository xhbDisplayLocationRepository;
    private XhbDisplayDocumentRepository xhbDisplayDocumentRepository;
    private XhbRotationSetDdRepository xhbRotationSetDdRepository;

    public VipDisplayDocumentQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public VipDisplayDocumentQuery(EntityManager entityManager, XhbDisplayRepository xhbDisplayRepository,
        XhbDisplayLocationRepository xhbDisplayLocationRepository,
        XhbDisplayDocumentRepository xhbDisplayDocumentRepository,
        XhbRotationSetDdRepository xhbRotationSetDdRepository) {
        this.entityManager = entityManager;
        this.xhbDisplayRepository = xhbDisplayRepository;
        this.xhbDisplayLocationRepository = xhbDisplayLocationRepository;
        this.xhbDisplayDocumentRepository = xhbDisplayDocumentRepository;
        this.xhbRotationSetDdRepository = xhbRotationSetDdRepository;
    }

    /**
     * Returns an array of CourtListValue.
     * 
     * @param courtSiteId court site id
     * 
     * @return Summary by name data for the specified court rooms
     */
    public Collection<VipDisplayConfigurationDisplayDocument> getData(Integer courtSiteId) {
        LOG.debug("getData({})", courtSiteId);
        Collection<VipDisplayConfigurationDisplayDocument> results = new ArrayList<>();
        List<XhbDisplayLocationDao> dlDaos = getXhbDisplayLocationRepository().findByVipCourtSite(courtSiteId);
        if (!dlDaos.isEmpty()) {
            // Loop the VIP courtSites
            for (XhbDisplayLocationDao dlDao : dlDaos) {
                // Loop the displays
                results.addAll(getDisplays(dlDao));
            }
        }
        return results;
    }

    private List<VipDisplayConfigurationDisplayDocument> getDisplays(XhbDisplayLocationDao dlDao) {
        List<VipDisplayConfigurationDisplayDocument> results = new ArrayList<>();
        List<XhbDisplayDao> daos = getXhbDisplayRepository().findByDisplayLocationId(dlDao.getDisplayLocationId());
        if (!daos.isEmpty()) {
            for (XhbDisplayDao dao : daos) {
                // Loop the display rotation sets
                List<XhbRotationSetDdDao> rsDaos =
                    getXhbRotationSetDdRepository().findByRotationSetId(dao.getRotationSetId());
                for (XhbRotationSetDdDao rsDao : rsDaos) {
                    // Get the document
                    Optional<XhbDisplayDocumentDao> ddDao =
                        getXhbDisplayDocumentRepository().findById(rsDao.getDisplayDocumentId());
                    if (ddDao.isPresent()) {
                        VipDisplayConfigurationDisplayDocument result = getVipDisplayConfigurationDisplayDocument(
                            ddDao.get().getDescriptionCode(), "Y".equals(ddDao.get().getMultipleCourtYn()),
                            ddDao.get().getLanguage(), ddDao.get().getCountry());
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }

    private VipDisplayConfigurationDisplayDocument getVipDisplayConfigurationDisplayDocument(String descriptionCode,
        boolean multipleCourt, String language, String country) {
        return new VipDisplayConfigurationDisplayDocument(descriptionCode, multipleCourt, language, country);
    }

    private XhbDisplayLocationRepository getXhbDisplayLocationRepository() {
        if (xhbDisplayLocationRepository == null) {
            xhbDisplayLocationRepository = new XhbDisplayLocationRepository(entityManager);
        }
        return xhbDisplayLocationRepository;
    }

    private XhbDisplayRepository getXhbDisplayRepository() {
        if (xhbDisplayRepository == null) {
            xhbDisplayRepository = new XhbDisplayRepository(entityManager);
        }
        return xhbDisplayRepository;
    }

    private XhbDisplayDocumentRepository getXhbDisplayDocumentRepository() {
        if (xhbDisplayDocumentRepository == null) {
            xhbDisplayDocumentRepository = new XhbDisplayDocumentRepository(entityManager);
        }
        return xhbDisplayDocumentRepository;
    }

    private XhbRotationSetDdRepository getXhbRotationSetDdRepository() {
        if (xhbRotationSetDdRepository == null) {
            xhbRotationSetDdRepository = new XhbRotationSetDdRepository(entityManager);
        }
        return xhbRotationSetDdRepository;
    }
}
