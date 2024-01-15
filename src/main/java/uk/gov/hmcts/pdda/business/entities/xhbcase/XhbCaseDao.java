package uk.gov.hmcts.pdda.business.entities.xhbcase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings({"PMD.TooManyFields","PMD.ExcessivePublicCount","PMD.GodClass","PMD.CyclomaticComplexity"})
@Entity(name = "XHB_CASE")
@NamedQuery(name = "XHB_CASE.findByNumberTypeAndCourt",
    query = "SELECT o from XHB_CASE o WHERE o.caseNumber = :caseNumber "
        + "AND o.caseType = :caseType AND o.courtId = :courtId")
public class XhbCaseDao extends AbstractXhbCaseDao implements Serializable {

    private static final long serialVersionUID = -6788003970955114552L;

    @Id
    @GeneratedValue(generator = "xhb_case_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_case_seq", sequenceName = "xhb_case_seq", allocationSize = 1)
    @Column(name = "CASE_ID")
    private Integer caseId;

    @Column(name = "CASE_NUMBER")
    private Integer caseNumber;

    @Column(name = "CASE_TYPE")
    private String caseType;

    @Column(name = "CASE_SUB_TYPE")
    private String caseSubType;

    @Column(name = "CASE_TITLE")
    private String caseTitle;

    @Column(name = "CASE_DESCRIPTION")
    private String caseDescription;

    @Column(name = "LINKED_CASE_ID")
    private Integer linkedCaseId;

    @Column(name = "BAIL_MAG_CODE")
    private String bailMagCode;

    @Column(name = "REF_COURT_ID")
    private Integer refCourtId;

    @Column(name = "COURT_ID")
    private Integer courtId;

    @Column(name = "CHARGE_IMPORT_INDICATOR")
    private String chargeImportIndicator;

    @Column(name = "SEVERED_IND")
    private String severedInd;

    @Column(name = "INDICT_RESP")
    private String indictResp;

    @Column(name = "PROS_AGENCY_REFERENCE")
    private String prosAgencyReference;

    @Column(name = "CASE_CLASS")
    private Integer caseClass;

    @Column(name = "JUDGE_REASON_FOR_APPEAL")
    private String judgeReasonForAppeal;

    @Column(name = "RESULTS_VERIFIED")
    private String resultsVerified;

    @Column(name = "LENGTH_TAPE")
    private Integer lengthTape;

    @Column(name = "NO_PAGE_PROS_EVIDENCE")
    private Integer noPageProsEvidence;

    @Column(name = "NO_PROS_WITNESS")
    private Integer noProsWitness;

    @Column(name = "EST_PDH_TRIAL_LENGTH")
    private Integer estPdhTrialLength;

    @Column(name = "POLICE_OFFICER_ATTENDING")
    private String policeOfficerAttending;

    @Column(name = "CPS_CASE_WORKER")
    private String cpsCaseWorker;

    @Column(name = "EXPORT_CHARGES")
    private String exportCharges;

    @Column(name = "IND_CHANGE_STATUS")
    private String indChangeStatus;

    @Column(name = "MAGISTRATES_CASE_REF")
    private String magistratesCaseRef;

    @Column(name = "CLASS_CODE")
    private Integer classCode;

    @Column(name = "OFFENCE_GROUP_UPDATE")
    private String offenceGroupUpdate;

    @Column(name = "CCC_TRANS_TO_REF_COURT_ID")
    private Integer cccTransToRefCourtId;

    @Column(name = "RECEIPT_TYPE")
    private String receiptType;

    @Column(name = "CCC_TRANS_FROM_REF_COURT_ID")
    private Integer cccTransFromRefCourtId;

    @Column(name = "RETRIAL")
    private String retrial;

    @Column(name = "ORIGINAL_CASE_NUMBER")
    private String originalCaseNumber;

    @Column(name = "NO_CB_PROS_WITNESS")
    private Integer noCbProsWitness;

    @Column(name = "NO_OTHER_PROS_WITNESS")
    private Integer noOtherProsWitness;

    @Column(name = "VULNERABLE_VICTIM_INDICATOR")
    private String vulnerableVictimIndicator;

    @Column(name = "PUBLIC_DISPLAY_HIDE")
    private String publicDisplayHide;

    @Column(name = "TRANSFERRED_CASE")
    private String transferredCase;

    @Column(name = "TRANSFER_DEFERRED_SENTENCE")
    private String transferDeferredSentence;

    @Column(name = "MONITORING_CATEGORY_ID")
    private Integer monitoringCategoryId;

    @Column(name = "EITHER_WAY_TYPE")
    private String eitherWayType;

    @Column(name = "TICKET_REQUIRED")
    private String ticketRequired;

    @Column(name = "TICKET_TYPE_CODE")
    private Integer ticketTypeCode;

    @Column(name = "COURT_ID_RECEIVING_SITE")
    private Integer courtIdReceivingSite;

    @Column(name = "NO_DEFENDANTS_FOR_CASE")
    private Integer noDefendantsForCase;

    @Column(name = "SECURE_COURT")
    private String secureCourt;

    @Column(name = "POLICE_FORCE_CODE")
    private Integer policeForceCode;

    @Column(name = "MAGCOURT_HEARINGTYPE_REF_ID")
    private Integer magcourtHearingtypeRefId;

    @Column(name = "CASE_LISTED")
    private String caseListed;

    @Column(name = "CASE_STATUS")
    private String caseStatus;

    @Column(name = "VIDEO_LINK_REQUIRED")
    private String videoLinkRequired;

    @Column(name = "CRACKED_INEFFECTIVE_ID")
    private Integer crackedIneffectiveId;

    @Column(name = "DEFAULT_HEARING_TYPE")
    private Integer defaultHearingType;

    @Column(name = "CASE_GROUP_NUMBER")
    private Integer caseGroupNumber;

    @Column(name = "PUB_RUNNING_LIST_ID")
    private Integer pubRunningListId;

    @Column(name = "DAR_RETENTION_POLICY_ID")
    private Integer darRetentionPolicyId;

    @Column(name = "CRP_LAST_UPDATE_DATE")
    private LocalDateTime crpLastUpdateDate;

    @Column(name = "CIVIL_UNREST")
    private String civilUnrest;

    public XhbCaseDao() {
        super();
    }

    public XhbCaseDao(XhbCaseDao otherData) {
        this();
        setCaseId(otherData.getCaseId());
        setCaseNumber(otherData.getCaseNumber());
        setCaseType(otherData.getCaseType());
        setCaseSubType(otherData.getCaseSubType());
        setCaseDescription(otherData.getCaseDescription());
        setLinkedCaseId(otherData.getLinkedCaseId());
        setBailMagCode(otherData.getBailMagCode());
        setRefCourtId(otherData.getRefCourtId());
        setCourtId(otherData.getCourtId());
        setChargeImportIndicator(otherData.getChargeImportIndicator());
        setSeveredInd(otherData.getSeveredInd());
        setIndictResp(otherData.getIndictResp());
        setProsAgencyReference(otherData.getProsAgencyReference());
        setCaseClass(otherData.getCaseClass());
        setJudgeReasonForAppeal(otherData.getJudgeReasonForAppeal());
        setResultsVerified(otherData.getResultsVerified());
        setLengthTape(otherData.getLengthTape());
        setNoPageProsEvidence(otherData.getNoPageProsEvidence());
        setNoProsWitness(otherData.getNoProsWitness());
        setEstPdhTrialLength(otherData.getEstPdhTrialLength());
        setPoliceOfficerAttending(otherData.getPoliceOfficerAttending());
        setCpsCaseWorker(otherData.getCpsCaseWorker());
        setExportCharges(otherData.getExportCharges());
        setIndChangeStatus(otherData.getIndChangeStatus());
        setMagistratesCaseRef(otherData.getMagistratesCaseRef());
        setClassCode(otherData.getClassCode());
        setOffenceGroupUpdate(otherData.getOffenceGroupUpdate());
        setCccTransToRefCourtId(otherData.getCccTransToRefCourtId());
        setReceiptType(otherData.getReceiptType());
        setCccTransFromRefCourtId(otherData.getCccTransFromRefCourtId());
        setRetrial(otherData.getRetrial());
        setOriginalCaseNumber(otherData.getOriginalCaseNumber());
        setNoCbProsWitness(otherData.getNoCbProsWitness());
        setNoOtherProsWitness(otherData.getNoOtherProsWitness());
        setVulnerableVictimIndicator(otherData.getVulnerableVictimIndicator());
        setPublicDisplayHide(otherData.getPublicDisplayHide());
        setTransferredCase(otherData.getTransferredCase());
        setTransferDeferredSentence(otherData.getTransferDeferredSentence());
        setMonitoringCategoryId(otherData.getMonitoringCategoryId());
        setEitherWayType(otherData.getEitherWayType());
        setTicketRequired(otherData.getTicketRequired());

        setAdditionalData(otherData);
    }

    @Override
    protected final void setAdditionalData(XhbCaseDao otherData) {
        super.setAdditionalData(otherData);
        setTicketTypeCode(otherData.getTicketTypeCode());
        setCourtIdReceivingSite(otherData.getCourtIdReceivingSite());
        setNoDefendantsForCase(otherData.getNoDefendantsForCase());
        setSecureCourt(otherData.getSecureCourt());
        setPoliceForceCode(otherData.getPoliceForceCode());
        setMagcourtHearingtypeRefid(otherData.getMagcourtHearingtypeRefid());
        setCaseListed(otherData.getCaseListed());
        setCaseStatus(otherData.getCaseStatus());
        setVideoLinkRequired(otherData.getVideoLinkRequired());
        setCrackedIneffectiveId(otherData.getCrackedIneffectiveId());
        setDefaultHearingType(otherData.getDefaultHearingType());
        setCaseGroupNumber(otherData.getCaseGroupNumber());
        setPubRunningListId(otherData.getPubRunningListId());
        setDarRetentionPolicyId(otherData.getDarRetentionPolicyId());
        setCrpLastUpdateDate(otherData.getCrpLastUpdateDate());
        setCivilUnrest(otherData.getCivilUnrest());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
    }

    public Integer getPrimaryKey() {
        return getCaseId();
    }

    public Integer getCaseId() {
        return caseId;
    }

    public final void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getCaseNumber() {
        return caseNumber;
    }

    public final void setCaseNumber(Integer caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseType() {
        return caseType;
    }

    public final void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseSubType() {
        return caseSubType;
    }

    public final void setCaseSubType(String caseSubType) {
        this.caseSubType = caseSubType;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public final void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public final void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public Integer getLinkedCaseId() {
        return linkedCaseId;
    }

    public final void setLinkedCaseId(Integer linkedCaseId) {
        this.linkedCaseId = linkedCaseId;
    }

    public String getBailMagCode() {
        return bailMagCode;
    }

    public final void setBailMagCode(String bailMagCode) {
        this.bailMagCode = bailMagCode;
    }

    public Integer getRefCourtId() {
        return refCourtId;
    }

    public final void setRefCourtId(Integer refCourtId) {
        this.refCourtId = refCourtId;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public final void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getChargeImportIndicator() {
        return chargeImportIndicator;
    }

    public final void setChargeImportIndicator(String chargeImportIndicator) {
        this.chargeImportIndicator = chargeImportIndicator;
    }

    public String getSeveredInd() {
        return severedInd;
    }

    public final void setSeveredInd(String severedInd) {
        this.severedInd = severedInd;
    }

    public String getIndictResp() {
        return indictResp;
    }

    public final void setIndictResp(String indictResp) {
        this.indictResp = indictResp;
    }

    public String getProsAgencyReference() {
        return prosAgencyReference;
    }

    public final void setProsAgencyReference(String prosAgencyReference) {
        this.prosAgencyReference = prosAgencyReference;
    }

    public Integer getCaseClass() {
        return caseClass;
    }

    public final void setCaseClass(Integer caseClass) {
        this.caseClass = caseClass;
    }

    public String getJudgeReasonForAppeal() {
        return judgeReasonForAppeal;
    }

    public final void setJudgeReasonForAppeal(String judgeReasonForAppeal) {
        this.judgeReasonForAppeal = judgeReasonForAppeal;
    }

    public String getResultsVerified() {
        return resultsVerified;
    }

    public final void setResultsVerified(String resultsVerified) {
        this.resultsVerified = resultsVerified;
    }

    public Integer getLengthTape() {
        return lengthTape;
    }

    public final void setLengthTape(Integer lengthTape) {
        this.lengthTape = lengthTape;
    }

    public Integer getNoPageProsEvidence() {
        return noPageProsEvidence;
    }

    public final void setNoPageProsEvidence(Integer noPageProsEvidence) {
        this.noPageProsEvidence = noPageProsEvidence;
    }

    public Integer getNoProsWitness() {
        return noProsWitness;
    }

    public final void setNoProsWitness(Integer noProsWitness) {
        this.noProsWitness = noProsWitness;
    }

    public Integer getEstPdhTrialLength() {
        return estPdhTrialLength;
    }

    public final void setEstPdhTrialLength(Integer estPdhTrialLength) {
        this.estPdhTrialLength = estPdhTrialLength;
    }

    public String getPoliceOfficerAttending() {
        return policeOfficerAttending;
    }

    public final void setPoliceOfficerAttending(String policeOfficerAttending) {
        this.policeOfficerAttending = policeOfficerAttending;
    }

    public String getCpsCaseWorker() {
        return cpsCaseWorker;
    }

    public final void setCpsCaseWorker(String cpsCaseWorker) {
        this.cpsCaseWorker = cpsCaseWorker;
    }

    public String getExportCharges() {
        return exportCharges;
    }

    public final void setExportCharges(String exportCharges) {
        this.exportCharges = exportCharges;
    }

    public String getIndChangeStatus() {
        return indChangeStatus;
    }

    public final void setIndChangeStatus(String indChangeStatus) {
        this.indChangeStatus = indChangeStatus;
    }

    public String getMagistratesCaseRef() {
        return magistratesCaseRef;
    }

    public final void setMagistratesCaseRef(String magistratesCaseRef) {
        this.magistratesCaseRef = magistratesCaseRef;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public final void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getOffenceGroupUpdate() {
        return offenceGroupUpdate;
    }

    public final void setOffenceGroupUpdate(String offenceGroupUpdate) {
        this.offenceGroupUpdate = offenceGroupUpdate;
    }

    public Integer getCccTransToRefCourtId() {
        return cccTransToRefCourtId;
    }

    public final void setCccTransToRefCourtId(Integer cccTransToRefCourtId) {
        this.cccTransToRefCourtId = cccTransToRefCourtId;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public final void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public Integer getCccTransFromRefCourtId() {
        return cccTransFromRefCourtId;
    }

    public final void setCccTransFromRefCourtId(Integer cccTransFromRefCourtId) {
        this.cccTransFromRefCourtId = cccTransFromRefCourtId;
    }

    public String getRetrial() {
        return retrial;
    }

    public final void setRetrial(String retrial) {
        this.retrial = retrial;
    }

    public String getOriginalCaseNumber() {
        return originalCaseNumber;
    }

    public final void setOriginalCaseNumber(String originalCaseNumber) {
        this.originalCaseNumber = originalCaseNumber;
    }

    public Integer getNoCbProsWitness() {
        return noCbProsWitness;
    }

    public final void setNoCbProsWitness(Integer noCbProsWitness) {
        this.noCbProsWitness = noCbProsWitness;
    }

    public Integer getNoOtherProsWitness() {
        return noOtherProsWitness;
    }

    public final void setNoOtherProsWitness(Integer noOtherProsWitness) {
        this.noOtherProsWitness = noOtherProsWitness;
    }

    public String getVulnerableVictimIndicator() {
        return vulnerableVictimIndicator;
    }

    public final void setVulnerableVictimIndicator(String vulnerableVictimIndicator) {
        this.vulnerableVictimIndicator = vulnerableVictimIndicator;
    }

    public String getPublicDisplayHide() {
        return publicDisplayHide;
    }

    public final void setPublicDisplayHide(String publicDisplayHide) {
        this.publicDisplayHide = publicDisplayHide;
    }

    public String getTransferredCase() {
        return transferredCase;
    }

    public final void setTransferredCase(String transferredCase) {
        this.transferredCase = transferredCase;
    }

    public String getTransferDeferredSentence() {
        return transferDeferredSentence;
    }

    public final void setTransferDeferredSentence(String transferDeferredSentence) {
        this.transferDeferredSentence = transferDeferredSentence;
    }

    public Integer getMonitoringCategoryId() {
        return monitoringCategoryId;
    }

    public final void setMonitoringCategoryId(Integer monitoringCategoryId) {
        this.monitoringCategoryId = monitoringCategoryId;
    }

    public String getEitherWayType() {
        return eitherWayType;
    }

    public final void setEitherWayType(String eitherWayType) {
        this.eitherWayType = eitherWayType;
    }

    public String getTicketRequired() {
        return ticketRequired;
    }

    public final void setTicketRequired(String ticketRequired) {
        this.ticketRequired = ticketRequired;
    }

    public Integer getTicketTypeCode() {
        return ticketTypeCode;
    }

    public final void setTicketTypeCode(Integer ticketTypeCode) {
        this.ticketTypeCode = ticketTypeCode;
    }

    public Integer getCourtIdReceivingSite() {
        return courtIdReceivingSite;
    }

    public final void setCourtIdReceivingSite(Integer courtIdReceivingSite) {
        this.courtIdReceivingSite = courtIdReceivingSite;
    }

    public Integer getNoDefendantsForCase() {
        return noDefendantsForCase;
    }

    public final void setNoDefendantsForCase(Integer noDefendantsForCase) {
        this.noDefendantsForCase = noDefendantsForCase;
    }

    public String getSecureCourt() {
        return secureCourt;
    }

    public final void setSecureCourt(String secureCourt) {
        this.secureCourt = secureCourt;
    }

    public Integer getPoliceForceCode() {
        return policeForceCode;
    }

    public final void setPoliceForceCode(Integer policeForceCode) {
        this.policeForceCode = policeForceCode;
    }

    public Integer getMagcourtHearingtypeRefid() {
        return magcourtHearingtypeRefId;
    }

    public final void setMagcourtHearingtypeRefid(Integer magcourtHearingtypeRefid) {
        this.magcourtHearingtypeRefId = magcourtHearingtypeRefid;
    }

    public String getCaseListed() {
        return caseListed;
    }

    public final void setCaseListed(String caseListed) {
        this.caseListed = caseListed;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public final void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getVideoLinkRequired() {
        return videoLinkRequired;
    }

    public final void setVideoLinkRequired(String videoLinkRequired) {
        this.videoLinkRequired = videoLinkRequired;
    }

    public Integer getCrackedIneffectiveId() {
        return crackedIneffectiveId;
    }

    public final void setCrackedIneffectiveId(Integer crackedIneffectiveId) {
        this.crackedIneffectiveId = crackedIneffectiveId;
    }

    public Integer getDefaultHearingType() {
        return defaultHearingType;
    }

    public final void setDefaultHearingType(Integer defaultHearingType) {
        this.defaultHearingType = defaultHearingType;
    }

    public Integer getCaseGroupNumber() {
        return caseGroupNumber;
    }

    public final void setCaseGroupNumber(Integer caseGroupNumber) {
        this.caseGroupNumber = caseGroupNumber;
    }

    public Integer getPubRunningListId() {
        return pubRunningListId;
    }

    public final void setPubRunningListId(Integer pubRunningListId) {
        this.pubRunningListId = pubRunningListId;
    }

    public Integer getDarRetentionPolicyId() {
        return darRetentionPolicyId;
    }

    public final void setDarRetentionPolicyId(Integer darRetentionPolicyId) {
        this.darRetentionPolicyId = darRetentionPolicyId;
    }

    public LocalDateTime getCrpLastUpdateDate() {
        return crpLastUpdateDate;
    }

    public final void setCrpLastUpdateDate(LocalDateTime crpLastUpdateDate) {
        this.crpLastUpdateDate = crpLastUpdateDate;
    }

    public String getCivilUnrest() {
        return civilUnrest;
    }

    public final void setCivilUnrest(String civilUnrest) {
        this.civilUnrest = civilUnrest;
    }

}
