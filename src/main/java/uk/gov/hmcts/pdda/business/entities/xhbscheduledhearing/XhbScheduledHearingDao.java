package uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import uk.gov.hmcts.pdda.business.entities.AbstractDao;
import uk.gov.hmcts.pdda.business.entities.xhbcrlivedisplay.XhbCrLiveDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbsitting.XhbSittingDao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@SuppressWarnings({"PMD.TooManyFields", "PMD.LinguisticNaming"})
@Entity(name = "XHB_SCHEDULED_HEARING")
@NamedQuery(name = "XHB_SCHEDULED_HEARING.findByHearingId",
    query = "SELECT o from XHB_SCHEDULED_HEARING o WHERE o.hearingId = :hearingId")
@NamedQuery(name = "XHB_SCHEDULED_HEARING.findActiveCasesInRoom",
    query = "SELECT o from XHB_SCHEDULED_HEARING o, XHB_SITTING s WHERE o.scheduledHearingId != :scheduledHearingId "
        + "AND o.isCaseActive = 'Y' AND s.sittingId = o.sittingId AND s.listId = :listId AND s.courtRoomId = "
        + ":courtRoomId ORDER BY o.scheduledHearingId")
@NamedQuery(name = "XHB_SCHEDULED_HEARING.findBySittingId",
    query = "SELECT o from XHB_SCHEDULED_HEARING o WHERE o.sittingId = :sittingId ORDER BY o.scheduledHearingId")
public class XhbScheduledHearingDao extends AbstractDao implements Serializable {

    private static final long serialVersionUID = -6844793990175522946L;

    @Id
    @GeneratedValue(generator = "xhb_scheduled_hearing_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_scheduled_hearing_seq",
        sequenceName = "xhb_scheduled_hearing_seq", allocationSize = 1)
    @Column(name = "SCHEDULED_HEARING_ID")
    private Integer scheduledHearingId;

    @Column(name = "SEQUENCE_NO")
    private Integer sequenceNo;

    @Column(name = "NOT_BEFORE_TIME")
    private LocalDateTime notBeforeTime;

    @Column(name = "ORIGINAL_TIME")
    private LocalDateTime originalTime;

    @Column(name = "LISTING_NOTE")
    private String listingNote;

    @Column(name = "HEARING_PROGRESS")
    private Integer hearingProgress;

    @Column(name = "SITTING_ID")
    private Integer sittingId;

    @Column(name = "HEARING_ID")
    private Integer hearingId;

    @Column(name = "MOVED_FROM")
    private String movedFrom;

    @Column(name = "MOVED_FROM_COURT_ROOM_ID")
    private Integer movedFromCourtRoomId;

    @Column(name = "IS_CASE_ACTIVE")
    private String isCaseActive;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "VERSION")
    private Integer version;

    // Non-db columns
    @jakarta.persistence.Transient
    private XhbSittingDao sitting;

    @jakarta.persistence.Transient
    private Collection<XhbCrLiveDisplayDao> crLiveDisplays;

    public XhbScheduledHearingDao() {
        super();
    }

    public XhbScheduledHearingDao(XhbScheduledHearingDao otherData) {
        this();
        setScheduledHearingId(otherData.getScheduledHearingId());
        setSequenceNo(otherData.getSequenceNo());
        setNotBeforeTime(otherData.getNotBeforeTime());
        setOriginalTime(otherData.getOriginalTime());
        setListingNote(otherData.getListingNote());
        setHearingProgress(otherData.getHearingProgress());
        setSittingId(otherData.getSittingId());
        setHearingId(otherData.getHearingId());
        setIsCaseActive(otherData.getIsCaseActive());
        setMovedFrom(otherData.getMovedFrom());
        setMovedFromCourtRoomId(otherData.getMovedFromCourtRoomId());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
    }

    public Integer getPrimaryKey() {
        return getScheduledHearingId();
    }

    public Integer getScheduledHearingId() {
        return scheduledHearingId;
    }

    public final void setScheduledHearingId(Integer scheduledHearingId) {
        this.scheduledHearingId = scheduledHearingId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public final void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public LocalDateTime getNotBeforeTime() {
        return notBeforeTime;
    }

    public final void setNotBeforeTime(LocalDateTime notBeforeTime) {
        this.notBeforeTime = notBeforeTime;
    }

    public LocalDateTime getOriginalTime() {
        return originalTime;
    }

    public final void setOriginalTime(LocalDateTime originalTime) {
        this.originalTime = originalTime;
    }

    public String getListingNote() {
        return listingNote;
    }

    public final void setListingNote(String listingNote) {
        this.listingNote = listingNote;
    }

    public Integer getHearingProgress() {
        return hearingProgress;
    }

    public final void setHearingProgress(Integer hearingProgress) {
        this.hearingProgress = hearingProgress;
    }

    public Integer getSittingId() {
        return sittingId;
    }

    public final void setSittingId(Integer sittingId) {
        this.sittingId = sittingId;
    }

    public Integer getHearingId() {
        return hearingId;
    }

    public final void setHearingId(Integer hearingId) {
        this.hearingId = hearingId;
    }

    public String getMovedFrom() {
        return movedFrom;
    }

    public final void setMovedFrom(String movedFrom) {
        this.movedFrom = movedFrom;
    }

    public Integer getMovedFromCourtRoomId() {
        return movedFromCourtRoomId;
    }

    public final void setMovedFromCourtRoomId(Integer movedFromCourtRoomId) {
        this.movedFromCourtRoomId = movedFromCourtRoomId;
    }

    public String getIsCaseActive() {
        return isCaseActive;
    }

    public final void setIsCaseActive(String isCaseActive) {
        this.isCaseActive = isCaseActive;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public final void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public final void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public final void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public final void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public final void setVersion(Integer version) {
        this.version = version;
    }

    public XhbSittingDao getXhbSitting() {
        return sitting;
    }

    public final void setXhbSitting(XhbSittingDao sitting) {
        this.sitting = sitting;
    }

    public Collection<XhbCrLiveDisplayDao> getXhbCrLiveDisplays() {
        return crLiveDisplays;
    }

    public final void setXhbCrLiveDisplays(Collection<XhbCrLiveDisplayDao> crLiveDisplays) {
        this.crLiveDisplays = crLiveDisplays;
    }

}
