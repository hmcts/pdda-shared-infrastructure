package uk.gov.hmcts.pdda.business.entities.xhbschedhearingdefendant;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbSchedHearingDefendantRepositoryTest extends AbstractRepositoryTest<XhbSchedHearingDefendantDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbSchedHearingDefendantRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbSchedHearingDefendantRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbSchedHearingDefendantRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindByScheduledHearingIdSuccess() {
        boolean result = testFindByScheduledHearingId(getDummyDao());
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testFindByScheduledHearingIdFailure() {
        boolean result = testFindByScheduledHearingId(null);
        assertTrue(result, NOT_TRUE);
    }

    private boolean testFindByScheduledHearingId(XhbSchedHearingDefendantDao dao) {
        List<XhbSchedHearingDefendantDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbSchedHearingDefendantDao> result = (List<XhbSchedHearingDefendantDao>) getClassUnderTest()
            .findByScheduledHearingId(getDummyDao().getScheduledHearingId());
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(0), "Result is not Same");
        } else {
            assertSame(0, result.size(), "Result is not Same");
        }
        return true;
    }

    @Override
    protected XhbSchedHearingDefendantDao getDummyDao() {
        Integer schedHearingDefendantId = Integer.valueOf(-1);
        Integer scheduledHearingId = Integer.valueOf(-1);
        Integer defendantOnCaseId = Integer.valueOf(-1);
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        XhbSchedHearingDefendantDao result = new XhbSchedHearingDefendantDao(schedHearingDefendantId,
            scheduledHearingId, defendantOnCaseId, lastUpdateDate, creationDate, lastUpdatedBy, createdBy, version);
        schedHearingDefendantId = result.getPrimaryKey();
        assertNotNull(schedHearingDefendantId, NOTNULL);
        return new XhbSchedHearingDefendantDao(result);
    }

}
