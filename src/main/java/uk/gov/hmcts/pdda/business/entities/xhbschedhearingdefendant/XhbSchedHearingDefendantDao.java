package uk.gov.hmcts.pdda.business.entities.xhbschedhearingdefendant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import uk.gov.hmcts.pdda.business.entities.AbstractVersionedDao;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "XHB_SCHED_HEARING_DEFENDANT")
@NamedQuery(name = "XHB_SCHED_HEARING_DEFENDANT.findByScheduledHearingId",
    query = "SELECT o from XHB_SCHED_HEARING_DEFENDANT o WHERE o.scheduledHearingId = :scheduledHearingId")
public class XhbSchedHearingDefendantDao extends AbstractVersionedDao implements Serializable {

    private static final long serialVersionUID = -6844793990175522946L;

    @Id
    @GeneratedValue(generator = "xhb_scheduled_hearing_def_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_scheduled_hearing_def_seq",
        sequenceName = "xhb_scheduled_hearing_def_seq", allocationSize = 1)
    @Column(name = "SCHED_HEAR_DEF_ID")
    private Integer schedHearingDefendantId;

    @Column(name = "SCHEDULED_HEARING_ID")
    private Integer scheduledHearingId;

    @Column(name = "DEFENDANT_ON_CASE_ID")
    private Integer defendantOnCaseId;

    public XhbSchedHearingDefendantDao() {
        super();
    }

    public XhbSchedHearingDefendantDao(Integer schedHearingDefendantId, Integer scheduledHearingId,
        Integer defendantOnCaseId, LocalDateTime lastUpdateDate, LocalDateTime creationDate,
        String lastUpdatedBy, String createdBy, Integer version) {
        this();
        setSchedHearingDefendantId(schedHearingDefendantId);
        setScheduledHearingId(scheduledHearingId);
        setDefendantOnCaseId(defendantOnCaseId);
        setLastUpdateDate(lastUpdateDate);
        setCreationDate(creationDate);
        setLastUpdatedBy(lastUpdatedBy);
        setCreatedBy(createdBy);
        setVersion(version);
    }

    public XhbSchedHearingDefendantDao(XhbSchedHearingDefendantDao otherData) {
        this();
        setSchedHearingDefendantId(otherData.getSchedHearingDefendantId());
        setScheduledHearingId(otherData.getScheduledHearingId());
        setDefendantOnCaseId(otherData.getDefendantOnCaseId());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
    }

    public Integer getPrimaryKey() {
        return getSchedHearingDefendantId();
    }

    public Integer getSchedHearingDefendantId() {
        return schedHearingDefendantId;
    }

    private void setSchedHearingDefendantId(Integer schedHearingDefendantId) {
        this.schedHearingDefendantId = schedHearingDefendantId;
    }

    public Integer getScheduledHearingId() {
        return scheduledHearingId;
    }

    private void setScheduledHearingId(Integer scheduledHearingId) {
        this.scheduledHearingId = scheduledHearingId;
    }

    public Integer getDefendantOnCaseId() {
        return defendantOnCaseId;
    }

    private void setDefendantOnCaseId(Integer defendantOnCaseId) {
        this.defendantOnCaseId = defendantOnCaseId;
    }

}
