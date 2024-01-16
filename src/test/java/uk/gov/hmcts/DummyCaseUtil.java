package uk.gov.hmcts;

import uk.gov.hmcts.pdda.business.entities.xhbcase.XhbCaseDao;
import uk.gov.hmcts.pdda.business.entities.xhbcasereference.XhbCaseReferenceDao;
import uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase.XhbDefendantOnCaseDao;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class DummyCaseUtil {
    
    private static final String NOTNULL = "Result is Null";
    private static final String TEST1 = "Test1";
    private static final String TEST2 = "Test2";

    private DummyCaseUtil() {
        // Do nothing
    }
    
    public static XhbCaseDao getXhbCaseDao() {
        XhbCaseDao result = new XhbCaseDao();
        result.setCaseId(DummyServicesUtil.getDummyId());
        result.setCaseNumber(Integer.valueOf(20_230_505));
        result.setCaseType("T");
        result.setMagConvictionDate(LocalDateTime.now());
        result.setCaseSubType("caseSubType");
        result.setCaseDescription("caseDescription");
        result.setLinkedCaseId(Integer.valueOf(20_230_506));
        result.setBailMagCode("bailMagCode");
        result.setRefCourtId(Integer.valueOf(81));
        result.setCourtId(Integer.valueOf(81));
        result.setChargeImportIndicator("chargeImportIndicator");
        result.setSeveredInd("severedInd");
        result.setIndictResp("indictResp");
        result.setDateIndRec(LocalDateTime.now());
        result.setProsAgencyReference("prosAgencyReference");
        result.setCaseClass(Integer.valueOf(-1));
        result.setJudgeReasonForAppeal("judgeReasonForAppeal");
        result.setResultsVerified("resultsVerified");
        result.setLengthTape(Integer.valueOf(-1));
        result.setNoPageProsEvidence(Integer.valueOf(-1));
        result.setNoProsWitness(Integer.valueOf(-1));
        result.setEstPdhTrialLength(Integer.valueOf(-1));
        result.setIndictmentInfo1("indictmentInfo1");
        result.setIndictmentInfo2("indictmentInfo2");
        result.setIndictmentInfo3("indictmentInfo3");
        result.setIndictmentInfo4("indictmentInfo4");
        result.setIndictmentInfo5("indictmentInfo5");
        result.setIndictmentInfo6("indictmentInfo6");
        result.setPoliceOfficerAttending("policeOfficerAttending");
        result.setCpsCaseWorker("cpsCaseWorker");
        result.setExportCharges("exportCharges");
        result.setIndChangeStatus("indChangeStatus");
        result.setMagistratesCaseRef("magistratesCaseRef");
        result.setClassCode(Integer.valueOf(-1));
        result.setOffenceGroupUpdate("offenceGroupUpdate");
        result.setCccTransToRefCourtId(Integer.valueOf(-1));
        result.setReceiptType("receiptType");
        result.setCccTransFromRefCourtId(Integer.valueOf(-1));
        result.setDateTransFrom(LocalDateTime.now());
        result.setRetrial("retrial");
        result.setOriginalCaseNumber("originalCaseNumber");
        result.setLcSentDate(LocalDateTime.now());
        result.setNoCbProsWitness(Integer.valueOf(-1));
        result.setNoOtherProsWitness(Integer.valueOf(-1));
        result.setVulnerableVictimIndicator("vulnerableVictimIndicator");
        result.setPublicDisplayHide("publicDisplayHide");
        result.setTransferredCase("transferredCase");
        result.setTransferDeferredSentence("transferDeferredSentence");
        result.setMonitoringCategoryId(Integer.valueOf(-1));
        setXhbCaseDaoAdditional(result);
        return new XhbCaseDao(result);        
    }
    
    private static void setXhbCaseDaoAdditional(XhbCaseDao result) {
        result.setAppealLodgedDate(LocalDateTime.now());
        result.setReceivedDate(LocalDateTime.now());
        result.setEitherWayType("eitherWayType");
        result.setTicketRequired("ticketRequired");
        result.setTicketTypeCode(Integer.valueOf(-1));
        result.setCourtIdReceivingSite(Integer.valueOf(-1));
        result.setCommittalDate(LocalDateTime.now());
        result.setSentForTrialDate(LocalDateTime.now());
        result.setNoDefendantsForCase(Integer.valueOf(-1));
        result.setSecureCourt("secureCourt");
        result.setPreliminaryDateOfHearing(LocalDateTime.now());
        result.setOriginalJps1("originalJps1");
        result.setOriginalJps2("originalJps2");
        result.setOriginalJps3("originalJps3");
        result.setOriginalJps4("originalJps4");
        result.setPoliceForceCode(Integer.valueOf(-1));
        result.setMagcourtHearingtypeRefid(Integer.valueOf(-1));
        result.setCaseListed("caseListed");
        result.setOrigBodyDecisionDate(LocalDateTime.now());
        result.setCaseStatus("caseStatus");
        result.setVideoLinkRequired("videoLinkRequired");
        result.setCrackedIneffectiveId(Integer.valueOf(-1));
        result.setDefaultHearingType(Integer.valueOf(-1));
        result.setSection28Name1("section28Name1");
        result.setSection28Name2("section28Name2");
        result.setSection28Phone1("section28Phone1");
        result.setSection28Phone2("section28Phone2");
        result.setCaseGroupNumber(Integer.valueOf(-1));
        result.setPubRunningListId(Integer.valueOf(-1));
        result.setDateCtlReminderPrinted(LocalDateTime.now());
        result.setDateTransTo(LocalDateTime.now());
        result.setDateTransRecordedTo(LocalDateTime.now());
        result.setS28Eligible("s28Eligible");
        result.setS28OrderMade("s28OrderMade");
        result.setTelevisedApplicationMade("televisedApplicationMade");
        result.setTelevisedAppMadeDate(LocalDateTime.now());
        result.setTelevisedAppGranted("televisedAppGranted");
        result.setTelevisedAppRefusedFreetext("televisedAppRefusedFreetext");
        result.setTelevisedRemarksFilmed("televisedRemarksFilmed");
        result.setDarRetentionPolicyId(Integer.valueOf(-1));
        result.setCrpLastUpdateDate(LocalDateTime.now());
        result.setCivilUnrest("civilUnrest");
        result.setLastUpdateDate(LocalDateTime.now());
        result.setCreationDate(LocalDateTime.now().minusMinutes(1));
        result.setLastUpdatedBy(TEST2);
        result.setCreatedBy(TEST1);
        result.setVersion(Integer.valueOf(3));
        Integer caseId = result.getPrimaryKey();
        result.setCaseTitle(result.getCaseTitle());
        assertNotNull(caseId, NOTNULL);
    }
    
    public static XhbCaseReferenceDao getXhbCaseReferenceDao() {
        Integer caseReferenceId = DummyServicesUtil.getDummyId();
        Integer reportingRestrictions = Integer.valueOf(-1);
        Integer caseId = DummyServicesUtil.getDummyId();
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);

        XhbCaseReferenceDao result = new XhbCaseReferenceDao();
        assertNotNull(result, NOTNULL);
        result = new XhbCaseReferenceDao(caseReferenceId, reportingRestrictions, caseId, lastUpdateDate, creationDate,
            lastUpdatedBy, createdBy, version);
        assertNotNull(result, NOTNULL);
        caseReferenceId = result.getPrimaryKey();
        assertNotNull(caseReferenceId, NOTNULL);
        return new XhbCaseReferenceDao(result);
    }
    
    public static XhbDefendantOnCaseDao getXhbDefendantOnCaseDao() {
        XhbDefendantOnCaseDao result = new XhbDefendantOnCaseDao();
        result.setDefendantOnCaseId(Integer.valueOf(-1));
        result.setNoOfTics(Integer.valueOf(-1));
        result.setFinalDrivingLicenceStatus(Integer.valueOf(-1));
        result.setPtiurn("ptiurn");
        result.setIsJuvenile("juvenile");
        result.setIsMasked("masked");
        result.setMaskedName("maskedName");
        result.setCaseId(Integer.valueOf(-1));
        result.setDefendantId(Integer.valueOf(-1));
        result.setResultsVerified("resultsVerified");
        result.setDefendantNumber(Integer.valueOf(-1));
        result.setDateOfCommittal(LocalDateTime.now());
        result.setPncId("pncId");
        result.setCollectMagistrateCourtId(Integer.valueOf(-1));
        result.setCurrentBcStatus("currentBcStatus");
        result.setAsn("asn");
        result.setBenchWarrantExecDate(LocalDateTime.now());
        result.setCommBcStatus("commBcStatus");
        result.setBcStatusBwExecuted("bcStatusBwExecuted");
        result.setSpecialCirFound("specialCirFound");
        result.setDateExported(LocalDateTime.now());
        result.setCustodial("custodial");
        setXhbDefendantOnCaseDaoAdditional(result);
        return new XhbDefendantOnCaseDao(result);
    }
    
    private static void setXhbDefendantOnCaseDaoAdditional(XhbDefendantOnCaseDao result) {
        result.setSuspended("suspended");
        result.setSeriousDrugOffence("seriousDrugOffence");
        result.setRecommendedDeportation("recommendedDeportation");
        result.setNationality("nationality");
        result.setFirstFixedTrial(LocalDateTime.now());
        result.setFirstHearingType("firstHearingType");
        result.setPublicDisplayHide("publicDisplayHide");
        result.setAmendedDateExported(LocalDateTime.now());
        result.setAmendedReason("amendedReason");
        result.setHateInd("hateInd");
        result.setHateType("hateType");
        result.setHateSentInd("hateSentInd");
        result.setDrivingDisqSuspendedDate(LocalDateTime.now());
        result.setMagCourtFirstHearingDate(LocalDateTime.now());
        result.setMagCourtFinalHearingDate(LocalDateTime.now());
        result.setCustodyTimeLimit(LocalDateTime.now());
        result.setFormNgSentDate(LocalDateTime.now());
        result.setCacdAppealResultDate(LocalDateTime.now());
        result.setSection28Name1("section28Name1");
        result.setSection28Name2("section28Name2");
        result.setSection28Phone1("section28Phone1");
        result.setSection28Phone2("section28Phone2");
        result.setDateRcptNoticeAppeal(LocalDateTime.now());
        result.setCacdAppealResult(LocalDateTime.now());
        result.setCoaStatus("coaStatus");
        result.setCtlApplies("ctlApplies");
        result.setDarRetentionPolicyId(Integer.valueOf(-1));
        result.setObsInd("N");
        result.setLastUpdateDate(LocalDateTime.now());
        result.setCreationDate(LocalDateTime.now().minusMinutes(1));
        result.setLastUpdatedBy(TEST2);
        result.setCreatedBy(TEST1);
        result.setVersion(Integer.valueOf(3));
        Integer defendantOnCaseId = result.getPrimaryKey();
        assertNotNull(defendantOnCaseId, NOTNULL);
    }
}
