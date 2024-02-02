package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.query;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbcasereference.XhbCaseReferenceRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdefendant.XhbDefendantRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase.XhbDefendantOnCaseRepository;
import uk.gov.hmcts.pdda.business.entities.xhbhearing.XhbHearingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrefhearingtype.XhbRefHearingTypeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrefjudge.XhbRefJudgeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbschedhearingdefendant.XhbSchedHearingDefendantRepository;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbsitting.XhbSittingRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: PublicDisplayQueryRepo Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2024
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class PublicDisplayQueryRepoTest {

    private static final String NOT_TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final AllCaseStatusQuery classUnderTest = new AllCaseStatusQuery(mockEntityManager);

    @Test
    void testGetXhbDefendantRepository() {
        assertTrue(classUnderTest.getXhbDefendantRepository() instanceof XhbDefendantRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbRefHearingTypeRepository() {
        assertTrue(
            classUnderTest.getXhbRefHearingTypeRepository() instanceof XhbRefHearingTypeRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbHearingRepository() {
        assertTrue(classUnderTest.getXhbHearingRepository() instanceof XhbHearingRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbRefJudgeRepository() {
        assertTrue(classUnderTest.getXhbRefJudgeRepository() instanceof XhbRefJudgeRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbSittingRepository() {
        assertTrue(classUnderTest.getXhbSittingRepository() instanceof XhbSittingRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbScheduledHearingRepository() {
        assertTrue(
            classUnderTest
                .getXhbScheduledHearingRepository() instanceof XhbScheduledHearingRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbSchedHearingDefendantRepository() {
        assertTrue(classUnderTest
            .getXhbSchedHearingDefendantRepository() instanceof XhbSchedHearingDefendantRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbCaseReferenceRepository() {
        assertTrue(
            classUnderTest.getXhbCaseReferenceRepository() instanceof XhbCaseReferenceRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbDefendantOnCaseRepository() {
        assertTrue(
            classUnderTest
                .getXhbDefendantOnCaseRepository() instanceof XhbDefendantOnCaseRepository,
            NOT_TRUE);
    }


}
