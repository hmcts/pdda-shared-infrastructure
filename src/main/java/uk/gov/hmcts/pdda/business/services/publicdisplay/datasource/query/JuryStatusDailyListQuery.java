package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.query;

import jakarta.persistence.EntityManager;
import uk.gov.hmcts.framework.util.DateTimeUtilities;
import uk.gov.hmcts.pdda.business.entities.xhbcase.XhbCaseDao;
import uk.gov.hmcts.pdda.business.entities.xhbcase.XhbCaseRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcasereference.XhbCaseReferenceRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtlogentry.XhbCourtLogEntryRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdefendant.XhbDefendantDao;
import uk.gov.hmcts.pdda.business.entities.xhbdefendant.XhbDefendantRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase.XhbDefendantOnCaseDao;
import uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase.XhbDefendantOnCaseRepository;
import uk.gov.hmcts.pdda.business.entities.xhbhearing.XhbHearingDao;
import uk.gov.hmcts.pdda.business.entities.xhbhearing.XhbHearingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbhearinglist.XhbHearingListDao;
import uk.gov.hmcts.pdda.business.entities.xhbhearinglist.XhbHearingListRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrefhearingtype.XhbRefHearingTypeDao;
import uk.gov.hmcts.pdda.business.entities.xhbrefhearingtype.XhbRefHearingTypeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrefjudge.XhbRefJudgeDao;
import uk.gov.hmcts.pdda.business.entities.xhbrefjudge.XhbRefJudgeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbschedhearingdefendant.XhbSchedHearingDefendantDao;
import uk.gov.hmcts.pdda.business.entities.xhbschedhearingdefendant.XhbSchedHearingDefendantRepository;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingDao;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbsitting.XhbSittingDao;
import uk.gov.hmcts.pdda.business.entities.xhbsitting.XhbSittingRepository;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.DefendantName;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.JudgeName;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.JuryStatusDailyListValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * This class wraps the stored procedure that provides the data for the daily list and jury status
 * document.
 * 
 * @author pznwc5
 */
@SuppressWarnings("PMD.ExcessiveParameterList")
public class JuryStatusDailyListQuery extends PublicDisplayQuery {

    /**
     * Constructor compiles the query (originally called
     * XHB_PUBLIC_DISPLAY_PKG.GET_JURY_STATUS_DAILY_LIST).
     */
    public JuryStatusDailyListQuery(EntityManager entityManager) {
        super(entityManager);
        log.debug("Query object created");
    }

    public JuryStatusDailyListQuery(EntityManager entityManager, XhbCaseRepository xhbCaseRepository,
        XhbCaseReferenceRepository xhbCaseReferenceRepository, XhbHearingListRepository xhbHearingListRepository,
        XhbSittingRepository xhbSittingRepository, XhbScheduledHearingRepository xhbScheduledHearingRepository,
        XhbCourtSiteRepository xhbCourtSiteRepository, XhbCourtRoomRepository xhbCourtRoomRepository,
        XhbSchedHearingDefendantRepository xhbSchedHearingDefendantRepository,
        XhbHearingRepository xhbHearingRepository, XhbDefendantOnCaseRepository xhbDefendantOnCaseRepository,
        XhbDefendantRepository xhbDefendantRepository, XhbCourtLogEntryRepository xhbCourtLogEntryRepository,
        XhbRefHearingTypeRepository xhbRefHearingTypeRepository, XhbRefJudgeRepository xhbRefJudgeRepository) {
        super(entityManager, xhbCaseRepository, xhbCaseReferenceRepository, xhbHearingListRepository,
            xhbSittingRepository, xhbScheduledHearingRepository, xhbCourtSiteRepository, xhbCourtRoomRepository,
            xhbSchedHearingDefendantRepository, xhbHearingRepository, xhbDefendantOnCaseRepository,
            xhbDefendantRepository, xhbCourtLogEntryRepository, xhbRefHearingTypeRepository, xhbRefJudgeRepository);
    }

    /**
     * Returns an collection of JuryStatusDailyListValue.
     * 
     * @param date LocaldateTime
     * @param courtId room ids for which the data is required
     * @param courtRoomIds Court room ids
     * 
     * @return Suumary by name data for the specified court rooms
     */
    @Override
    public Collection<?> getData(LocalDateTime date, int courtId, int... courtRoomIds) {

        LocalDateTime startDate = DateTimeUtilities.stripTime(date);

        List<JuryStatusDailyListValue> results = new ArrayList<>();

        // Loop the hearing lists
        List<XhbHearingListDao> hearingListDaos = getHearingListDaos(courtId, startDate);
        if (hearingListDaos.isEmpty()) {
            log.debug("JuryStatusDailyListQuery - No Hearing Lists found for today");
        } else {
            for (XhbHearingListDao hearingListDao : hearingListDaos) {
                // Loop the sittings
                List<XhbSittingDao> sittingDaos;
                if (isFloatingIncluded()) {
                    sittingDaos = getSittingListDaos(hearingListDao.getListId());
                } else {
                    sittingDaos = getNonFloatingSittingListDaos(hearingListDao.getListId());
                }
                if (!sittingDaos.isEmpty()) {
                    results.addAll(getSittingData(sittingDaos, courtRoomIds));
                }
            }
        }

        return results;
    }

    protected boolean isFloatingIncluded() {
        // Only show isFloating = '0'
        return false;
    }

    private List<JuryStatusDailyListValue> getSittingData(List<XhbSittingDao> sittingDaos, int... courtRoomIds) {
        List<JuryStatusDailyListValue> results = new ArrayList<>();
        for (XhbSittingDao sittingDao : sittingDaos) {

            // Is this a floating sitting
            String floating = getIsFloating(sittingDao.getIsFloating());

            // Loop the scheduledHearings
            List<XhbScheduledHearingDao> scheduledHearingDaos = getScheduledHearingDaos(sittingDao.getSittingId());
            if (!scheduledHearingDaos.isEmpty()) {
                for (XhbScheduledHearingDao scheduledHearingDao : scheduledHearingDaos) {

                    // If we are including all floating then include all court rooms
                    // otherwise...
                    // Check if this courtroom has been selected
                    if (!isFloatingIncluded() && !isSelectedCourtRoom(courtRoomIds, sittingDao.getCourtRoomId(),
                        scheduledHearingDao.getMovedFromCourtRoomId())) {
                        continue;
                    }

                    JuryStatusDailyListValue result =
                        getJuryStatusDailyListValue(sittingDao, scheduledHearingDao, floating);

                    results.add(result);
                }
            }
        }
        return results;
    }

    private JuryStatusDailyListValue getJuryStatusDailyListValue(XhbSittingDao sittingDao,
        XhbScheduledHearingDao scheduledHearingDao, String floating) {
        JuryStatusDailyListValue result = new JuryStatusDailyListValue();
        boolean isCaseHidden = false;
        populateData(result, sittingDao.getCourtSiteId(), sittingDao.getCourtRoomId(),
            scheduledHearingDao.getMovedFromCourtRoomId(), scheduledHearingDao.getNotBeforeTime());
        result.setFloating(floating);
        result.setHearingProgress(
            scheduledHearingDao.getHearingProgress() != null ? scheduledHearingDao.getHearingProgress() : 0);
        result.setListCourtRoomId(sittingDao.getCourtRoomId());

        // Get the hearing
        Optional<XhbHearingDao> hearingDao = getXhbHearingDao(scheduledHearingDao.getHearingId());
        if (hearingDao.isPresent()) {
            result.setReportingRestricted(isReportingRestricted(hearingDao.get().getCaseId()));

            // Get the case
            Optional<XhbCaseDao> caseDao = getXhbCaseRepository().findById(hearingDao.get().getCaseId());
            if (caseDao.isPresent()) {
                result.setCaseNumber(caseDao.get().getCaseType() + caseDao.get().getCaseNumber());
                result.setCaseTitle(caseDao.get().getCaseTitle());
                isCaseHidden = YES.equals(caseDao.get().getPublicDisplayHide());

                // Populate the event
                populateEventData(result, hearingDao.get().getCaseId());
            }

            // Get the ref hearing type
            result.setHearingDescription(getHearingTypeDesc(hearingDao));
        }

        // Loop the schedHearingDefendants
        List<XhbSchedHearingDefendantDao> schedHearingDefDaos =
            getSchedHearingDefendantDaos(scheduledHearingDao.getScheduledHearingId());
        if (!schedHearingDefDaos.isEmpty()) {
            for (XhbSchedHearingDefendantDao schedHearingDefendantDao : schedHearingDefDaos) {
                // Get the defendant on case
                result.addDefendantName(getDefendantNameFromSchedule(schedHearingDefendantDao, isCaseHidden));
            }
        }

        // Populate the judge
        result.setJudgeName(getJudgeName(scheduledHearingDao));
        return result;
    }

    private DefendantName getDefendantNameFromSchedule(XhbSchedHearingDefendantDao schedHearingDefendantDao,
        boolean isCaseHidden) {
        Optional<XhbDefendantOnCaseDao> defendantOnCaseDao =
            getXhbDefendantOnCaseRepository().findById(schedHearingDefendantDao.getDefendantOnCaseId());
        if (defendantOnCaseDao.isPresent() && !YES.equals(defendantOnCaseDao.get().getObsInd())) {

            // Get the defendant
            Optional<XhbDefendantDao> defendantDao =
                getXhbDefendantRepository().findById(defendantOnCaseDao.get().getDefendantId());
            if (defendantDao.isPresent()) {
                return getDefendantName(defendantDao.get().getFirstName(), defendantDao.get().getMiddleName(),
                    defendantDao.get().getSurname(), isDefendantHidden(defendantDao, defendantOnCaseDao, isCaseHidden));
            }
        }
        return null;
    }

    private boolean isDefendantHidden(Optional<XhbDefendantDao> defendantDao,
        Optional<XhbDefendantOnCaseDao> defendantOnCaseDao, boolean isCaseHidden) {
        return isCaseHidden
            || defendantOnCaseDao.isPresent() && YES.equals(defendantOnCaseDao.get().getPublicDisplayHide())
            || defendantDao.isPresent() && YES.contentEquals(defendantDao.get().getPublicDisplayHide());
    }

    private DefendantName getDefendantName(String firstName, String middleName, String surname, boolean hide) {
        return new DefendantName(firstName, middleName, surname, hide);
    }

    private JudgeName getJudgeName(XhbScheduledHearingDao scheduledHearingDao) {
        Optional<XhbRefJudgeDao> refJudgeDao = getXhbRefJudgeDao(scheduledHearingDao.getScheduledHearingId());
        if (refJudgeDao.isPresent()) {
            return new JudgeName(refJudgeDao.get().getFullListTitle1(), refJudgeDao.get().getSurname());
        }
        return null;
    }

    private String getHearingTypeDesc(Optional<XhbHearingDao> hearingDao) {
        if (hearingDao.isPresent()) {
            Optional<XhbRefHearingTypeDao> refHearingTypeDao =
                getXhbRefHearingTypeRepository().findById(hearingDao.get().getRefHearingTypeId());
            if (refHearingTypeDao.isPresent()) {
                return refHearingTypeDao.get().getHearingTypeDesc();
            }
        }
        return null;
    }
}
