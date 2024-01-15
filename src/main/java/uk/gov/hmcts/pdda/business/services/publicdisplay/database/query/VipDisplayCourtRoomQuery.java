package uk.gov.hmcts.pdda.business.services.publicdisplay.database.query;


import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaycourtroom.XhbDisplayCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaycourtroom.XhbDisplayCourtRoomRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationRepository;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.VipDisplayConfigurationCourtRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title: VIP court rooms for court site query.
 * </p>
 * <p>
 * Description: This runs the stored procedure that for a given court site, it finds all court rooms
 * configured for the View Information Pages. Information on unassigned cases is also provided.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Bal Bhamra
 * @version $Id: VIPDisplayCourtRoomQuery.java,v 1.2 2005/11/17 10:55:48 bzjrnl Exp $
 */

public class VipDisplayCourtRoomQuery {
    /** Logger object. */
    private static final Logger LOG = LoggerFactory.getLogger(VipDisplayCourtRoomQuery.class);

    private static final String EMPTY_STRING = "";
    private final EntityManager entityManager;
    private XhbDisplayRepository xhbDisplayRepository;
    private XhbDisplayLocationRepository xhbDisplayLocationRepository;
    private XhbDisplayCourtRoomRepository xhbDisplayCourtRoomRepository;
    private XhbCourtSiteRepository xhbCourtSiteRepository;
    private XhbCourtRoomRepository xhbCourtRoomRepository;

    private boolean showUnassignedCases;

    public VipDisplayCourtRoomQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public VipDisplayCourtRoomQuery(EntityManager entityManager, XhbDisplayRepository xhbDisplayRepository,
        XhbDisplayLocationRepository xhbDisplayLocationRepository,
        XhbDisplayCourtRoomRepository xhbDisplayCourtRoomRepository, XhbCourtSiteRepository xhbCourtSiteRepository,
        XhbCourtRoomRepository xhbCourtRoomRepository) {
        this.entityManager = entityManager;
        this.xhbDisplayRepository = xhbDisplayRepository;
        this.xhbDisplayLocationRepository = xhbDisplayLocationRepository;
        this.xhbDisplayCourtRoomRepository = xhbDisplayCourtRoomRepository;
        this.xhbCourtSiteRepository = xhbCourtSiteRepository;
        this.xhbCourtRoomRepository = xhbCourtRoomRepository;
    }

    /**
     * Returns an array of VIPDisplayConfigurationCourtRoom objects.
     * 
     * @param courtSiteId court site id
     * 
     * @return Collection
     */
    public Collection<VipDisplayConfigurationCourtRoom> getData(Integer courtSiteId) {
        LOG.debug("getData({})", courtSiteId);
        List<VipDisplayConfigurationCourtRoom> results = new ArrayList<>();
        List<XhbDisplayLocationDao> dlDaos = getXhbDisplayLocationRepository().findByVipCourtSite(courtSiteId);
        if (!dlDaos.isEmpty()) {
            // Loop the VIP courtSites
            for (XhbDisplayLocationDao dlDao : dlDaos) {
                // Get the courtSite
                Optional<XhbCourtSiteDao> ocsDao = getXhbCourtSiteRepository().findById(dlDao.getCourtSiteId());
                if (ocsDao.isPresent()) {
                    results.addAll(getDisplays(dlDao, ocsDao));
                }
            }

        }

        return results;
    }

    private List<VipDisplayConfigurationCourtRoom> getDisplays(XhbDisplayLocationDao dlDao,
        Optional<XhbCourtSiteDao> ocsDao) {
        List<VipDisplayConfigurationCourtRoom> results = new ArrayList<>();
        // Loop the displays
        List<XhbDisplayDao> daos = getXhbDisplayRepository().findByDisplayLocationId(dlDao.getDisplayLocationId());
        if (!daos.isEmpty()) {
            for (XhbDisplayDao dao : daos) {
                // Loop the courtRooms
                List<XhbDisplayCourtRoomDao> dcrDaos =
                    getXhbDisplayCourtRoomRepository().findByDisplayId(dao.getDisplayId());
                if (!dcrDaos.isEmpty()) {
                    results.addAll(getCourtRooms(dcrDaos, ocsDao));
                }
            }
        }
        return results;
    }

    private List<VipDisplayConfigurationCourtRoom> getCourtRooms(List<XhbDisplayCourtRoomDao> dcrDaos,
        Optional<XhbCourtSiteDao> ocsDao) {
        List<VipDisplayConfigurationCourtRoom> results = new ArrayList<>();
        for (XhbDisplayCourtRoomDao dcrDao : dcrDaos) {
            Optional<XhbCourtRoomDao> ocrDao = getXhbCourtRoomRepository().findById(dcrDao.getCourtRoomId());
            if (ocrDao.isPresent()) {
                String shortName = ocsDao.isPresent() ? ocsDao.get().getShortName() : EMPTY_STRING;
                String displayName = ocsDao.isPresent() ? ocrDao.get().getDisplayName() : EMPTY_STRING;
                VipDisplayConfigurationCourtRoom result = getVipDisplayConfigurationCourtRoom(dcrDao.getCourtRoomId(),
                    shortName, displayName);
                results.add(result);
            }
        }
        return results;
    }

    private VipDisplayConfigurationCourtRoom getVipDisplayConfigurationCourtRoom(Integer courtRoomId,
        String courtSiteShortName, String courtRoomDisplayName) {
        return new VipDisplayConfigurationCourtRoom(courtRoomId, courtSiteShortName, courtRoomDisplayName);
    }

    /**
     * Returns boolean for Unassigned Cases.
     * 
     * @return boolean
     */
    public boolean isShowUnassignedCases() {
        return showUnassignedCases;
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

    private XhbDisplayCourtRoomRepository getXhbDisplayCourtRoomRepository() {
        if (xhbDisplayCourtRoomRepository == null) {
            xhbDisplayCourtRoomRepository = new XhbDisplayCourtRoomRepository(entityManager);
        }
        return xhbDisplayCourtRoomRepository;
    }

    private XhbCourtSiteRepository getXhbCourtSiteRepository() {
        if (xhbCourtSiteRepository == null) {
            xhbCourtSiteRepository = new XhbCourtSiteRepository(entityManager);
        }
        return xhbCourtSiteRepository;
    }

    private XhbCourtRoomRepository getXhbCourtRoomRepository() {
        if (xhbCourtRoomRepository == null) {
            xhbCourtRoomRepository = new XhbCourtRoomRepository(entityManager);
        }
        return xhbCourtRoomRepository;
    }
}
