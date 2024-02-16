package uk.gov.hmcts.pdda.business.entities.xhbcourtlogentry;

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
class XhbCourtLogEntryRepositoryTest extends AbstractRepositoryTest<XhbCourtLogEntryDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCourtLogEntryRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCourtLogEntryRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCourtLogEntryRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testfindByCaseIdSuccess() {
        boolean result = testfindByCaseId(getDummyDao());
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testfindByCaseIdFailure() {
        boolean result = testfindByCaseId(null);
        assertTrue(result, NOT_TRUE);
    }

    private boolean testfindByCaseId(XhbCourtLogEntryDao dao) {
        List<XhbCourtLogEntryDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbCourtLogEntryDao> result =
            (List<XhbCourtLogEntryDao>) getClassUnderTest().findByCaseId(getDummyDao().getCaseId());
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(0), "Result is not Same");
        } else {
            assertSame(0, result.size(), "Result is not Same");
        }
        return true;
    }

    @Override
    protected XhbCourtLogEntryDao getDummyDao() {
        Integer entryId = getDummyId();
        Integer caseId = Integer.valueOf(-1);
        Integer defendantOnCaseId = Integer.valueOf(-2);
        Integer defendantOnOffenceId = Integer.valueOf(-3);
        Integer scheduledHearingId = Integer.valueOf(-4);
        Integer eventDescId = Integer.valueOf(-5);
        String logEntryXml = "logEntryXml";
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);


        XhbCourtLogEntryDao result = new XhbCourtLogEntryDao();
        result.setEntryId(entryId);
        result.setCaseId(caseId);
        result.setDefendantOnCaseId(defendantOnCaseId);
        result.setDefendantOnOffenceId(defendantOnOffenceId);
        result.setScheduledHearingId(scheduledHearingId);
        result.setEventDescId(eventDescId);
        result.setLogEntryXml(logEntryXml);
        result.setDateTime(dateTime);
        result.setLastUpdateDate(lastUpdateDate);
        result.setCreationDate(creationDate);
        result.setLastUpdatedBy(lastUpdatedBy);
        result.setCreatedBy(createdBy);
        result.setVersion(version);
        entryId = result.getPrimaryKey();
        assertNotNull(entryId, NOTNULL);
        return new XhbCourtLogEntryDao(result);
    }

}
