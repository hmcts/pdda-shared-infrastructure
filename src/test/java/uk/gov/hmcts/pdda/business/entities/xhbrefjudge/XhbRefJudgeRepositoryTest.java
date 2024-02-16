package uk.gov.hmcts.pdda.business.entities.xhbrefjudge;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbRefJudgeRepositoryTest extends AbstractRepositoryTest<XhbRefJudgeDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbRefJudgeRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbRefJudgeRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbRefJudgeRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindScheduledAttendeeJudgeSuccess() {
        boolean result = testFindScheduledAttendeeJudge(getDummyDao());
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testFindScheduledAttendeeJudgeFailure() {
        boolean result = testFindScheduledAttendeeJudge(null);
        assertTrue(result, NOT_TRUE);
    }

    private boolean testFindScheduledAttendeeJudge(XhbRefJudgeDao dao) {
        List<XhbRefJudgeDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Integer scheduledHearingId = Integer.valueOf(-1);
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        Optional<XhbRefJudgeDao> result = getClassUnderTest().findScheduledAttendeeJudge(scheduledHearingId);
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(), SAME);
        } else {
            assertSame(Optional.empty(), result, SAME);
        }
        return true;
    }

    @Test
    void testFindScheduledSittingJudgeSuccess() {
        boolean result = testFindScheduledSittingJudge(getDummyDao());
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testFindScheduledSittingJudgeFailure() {
        boolean result = testFindScheduledSittingJudge(null);
        assertTrue(result, NOT_TRUE);
    }

    private boolean testFindScheduledSittingJudge(XhbRefJudgeDao dao) {
        List<XhbRefJudgeDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Integer scheduledHearingId = Integer.valueOf(-1);
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        Optional<XhbRefJudgeDao> result = getClassUnderTest().findScheduledSittingJudge(scheduledHearingId);
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(), SAME);
        } else {
            assertSame(Optional.empty(), result, SAME);
        }
        return true;
    }

    @Override
    protected XhbRefJudgeDao getDummyDao() {
        Integer refJudgeId = getDummyId();
        String judgeType = "judgeType";
        Integer crestJudgeId = Integer.valueOf(-1);
        String title = "title";
        String firstname = "firstname";
        String middleName = "middleName";
        String surname = "surname";
        String fullListTitle1 = "fullListTitle1";
        String fullListTitle2 = "fullListTitle2";
        String fullListTitle3 = "fullListTitle3";
        String statsCode = "statsCode";
        String initials = "initials";
        String honours = "honours";
        String judVers = "judVers";
        String obsInd = "N";
        String sourceTable = "sourceTable";
        Integer courtId = Integer.valueOf(-1);
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        XhbRefJudgeDao result = new XhbRefJudgeDao();
        result.setRefJudgeId(refJudgeId);
        result.setJudgeType(judgeType);
        result.setCrestJudgeId(crestJudgeId);
        result.setTitle(title);
        result.setFirstname(firstname);
        result.setMiddleName(middleName);
        result.setSurname(surname);
        result.setFullListTitle1(fullListTitle1);
        result.setFullListTitle2(fullListTitle2);
        result.setFullListTitle3(fullListTitle3);
        result.setStatsCode(statsCode);
        result.setInitials(initials);
        result.setHonours(honours);
        result.setJudVers(judVers);
        result.setObsInd(obsInd);
        result.setSourceTable(sourceTable);
        result.setCourtId(courtId);
        result.setLastUpdateDate(lastUpdateDate);
        result.setCreationDate(creationDate);
        result.setLastUpdatedBy(lastUpdatedBy);
        result.setCreatedBy(createdBy);
        result.setVersion(version);
        refJudgeId = result.getPrimaryKey();
        assertNotNull(refJudgeId, NOTNULL);
        return new XhbRefJudgeDao(result);
    }

}
