package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingDao;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.database.query.ActiveCasesInRoomQuery;
import uk.gov.hmcts.pdda.common.publicdisplay.events.ActivateCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CaseChangeInformation;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayNotifier;
import uk.gov.hmcts.pdda.crlivestatus.CrLiveStatusHelper;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

/**
 * <p>
 * Title: Helper class for query and setting the is_case_active flag.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Rakesh Lakhani
 * @version $Id: PublicDisplayActivationHelper.java,v 1.5 2004/07/16 14:57:13 tz0d5m Exp $
 */
public class PublicDisplayActivationHelper {
    /**
     * The DB value for an Active Display = "Y".
     */
    private static final String DISPLAY_ACTIVE = "Y";

    /**
     * The DB value for an Inctive Display = "N".
     */
    private static final String DISPLAY_INACTIVE = "N";

    private static final Logger LOG = LoggerFactory.getLogger(PublicDisplayActivationHelper.class);

    protected PublicDisplayActivationHelper() {
        // Protected constructor
    }

    /**
     * Check the Public display activation status for this particular scheduled hearing.
     * 
     * @param schedHearingId Integer
     * @return boolean true if scheduled hearing is active
     */
    public static boolean isPublicDisplayActive(final Integer schedHearingId,
        final EntityManager entityManager) {
        boolean result = false;
        Optional<XhbScheduledHearingDao> scheduledHearing =
            new XhbScheduledHearingRepository(entityManager).findById(schedHearingId);
        if (scheduledHearing.isPresent()) {
            result = DISPLAY_ACTIVE.equals(scheduledHearing.get().getIsCaseActive());
        }
        return result;
    }

    /**
     * Sets the public display for this scheduling hearing to Activate.
     * 
     * @param schedHearingId Integer
     */
    public static void activatePublicDisplay(final PublicDisplayNotifier notifier,
        final Integer schedHearingId, final Date activationDeactivationDate, final boolean activate,
        final EntityManager entityManager) {
        LOG.debug("activatePublicDisplay() with schedHearingId: " + schedHearingId);

        Optional<XhbScheduledHearingDao> scheduledHearing =
            new XhbScheduledHearingRepository(entityManager).findById(schedHearingId);
        if (scheduledHearing.isPresent()) {
            if (activate) {
                CrLiveStatusHelper.activatePublicDisplay(scheduledHearing.get(),
                    activationDeactivationDate);
            } else {
                CrLiveStatusHelper.deactivatePublicDisplay(scheduledHearing.get(),
                    activationDeactivationDate);
            }

            setActivationOfPDforSchedHearing(scheduledHearing.get(), activate, entityManager);

            // make sure no other sched hearings are active in this court room
            deactivateOtherSchedHearings(scheduledHearing.get(), entityManager);

            // Notify Public display listener
            Integer courtRoomId = scheduledHearing.get().getXhbSitting().getCourtRoomId();
            Integer courtId = scheduledHearing.get().getXhbSitting().getXhbCourtSite().getCourtId();

            sendCaseActivateEvent(notifier, courtId, courtRoomId, activate);
        }
    }

    /**
     * Send JMS message notifying of change in status.
     * 
     * @param notifier notifer to use
     * @param courtId The court event occurred in
     * @param courtRoomId The court room the event occurred in
     * @param activate Whether the display is activated or deactivated
     */
    private static void sendCaseActivateEvent(PublicDisplayNotifier notifier, Integer courtId,
        Integer courtRoomId, boolean activate) {
        CourtRoomIdentifier courtRoomIdentifier = new CourtRoomIdentifier(courtId, courtRoomId);
        ActivateCaseEvent ace =
            new ActivateCaseEvent(courtRoomIdentifier, new CaseChangeInformation(activate));
        notifier.sendMessage(ace);
    }

    /**
     * Set whether the scheduled hearing is activated or deactived.
     * 
     * @param schedHearing XhbScheduledHearingDao
     * @param isActive boolean
     */
    private static void setActivationOfPDforSchedHearing(XhbScheduledHearingDao schedHearing,
        final boolean isActive, final EntityManager entityManager) {
        schedHearing.setIsCaseActive(isActive ? DISPLAY_ACTIVE : DISPLAY_INACTIVE);
        new XhbScheduledHearingRepository(entityManager).update(schedHearing);
    }

    /**
     * For the sched hearing that is passed in, check that all other hearings in the same court room
     * have their public displays turned off.
     * 
     * @param schedHearing XhbScheduledHearingDao
     * @throws PublicDisplayControllerException Exception
     */
    private static void deactivateOtherSchedHearings(XhbScheduledHearingDao schedHearing,
        final EntityManager entityManager) {
        LOG.debug(
            "deactivateOtherSchedHearings() with schedHearingId: " + schedHearing.getPrimaryKey());

        XhbScheduledHearingRepository repo = new XhbScheduledHearingRepository(entityManager);

        Integer courtRoomId = schedHearing.getXhbSitting().getCourtRoomId();
        Integer hearingListId = schedHearing.getXhbSitting().getListId();

        ActiveCasesInRoomQuery query = new ActiveCasesInRoomQuery(entityManager);
        Collection<?> shIds =
            query.getData(hearingListId, courtRoomId, schedHearing.getScheduledHearingId());
        LOG.debug("Active Cases found: " + shIds.size());
        Iterator<?> iter = shIds.iterator();
        while (iter.hasNext()) {
            Integer thisSchedHearingId = (Integer) iter.next();
            LOG.debug("Deactivating SH ID: " + thisSchedHearingId);

            Optional<XhbScheduledHearingDao> xsh = repo.findById(thisSchedHearingId);
            if (xsh.isPresent()) {
                setActivationOfPDforSchedHearing(xsh.get(), false, entityManager);
            }
        }
    }
}
