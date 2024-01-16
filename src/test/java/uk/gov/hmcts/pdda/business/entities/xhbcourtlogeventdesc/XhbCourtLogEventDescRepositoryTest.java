package uk.gov.hmcts.pdda.business.entities.xhbcourtlogeventdesc;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("PMD.TestClassWithoutTestCases")
class XhbCourtLogEventDescRepositoryTest extends AbstractRepositoryTest<XhbCourtLogEventDescDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCourtLogEventDescRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCourtLogEventDescRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCourtLogEventDescRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Override
    protected XhbCourtLogEventDescDao getDummyDao() {
        Integer eventDescId = getDummyId();
        Integer flaggedEvent = Integer.valueOf(-1);
        Integer editable = Integer.valueOf(-2);
        Integer sendToMercator = Integer.valueOf(-3);
        Integer updateLinkedCases = Integer.valueOf(-4);
        Integer publishToSubscribers = Integer.valueOf(-5);
        Integer clearPublicDisplays = Integer.valueOf(-6);
        Integer electronicInform = Integer.valueOf(-1);
        Integer publicDisplay = Integer.valueOf(-1);
        String linkedCaseText = "linkedCaseText";
        String eventDescription = "eventDescription";
        Integer eventType = Integer.valueOf(-1);
        Integer publicNotice = Integer.valueOf(-1);
        String shortDescription = "shortDescription";
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);

        XhbCourtLogEventDescDao result = new XhbCourtLogEventDescDao();
        result.setEventDescId(eventDescId);
        result.setFlaggedEvent(flaggedEvent);
        result.setEditable(editable);
        result.setSendToMercator(sendToMercator);
        result.setUpdateLinkedCases(updateLinkedCases);
        result.setPublishToSubscribers(publishToSubscribers);
        result.setClearPublicDisplays(clearPublicDisplays);
        result.setElectronicInform(electronicInform);
        result.setPublicDisplay(publicDisplay);
        result.setLinkedCaseText(linkedCaseText);
        result.setEventDescription(eventDescription);
        result.setEventType(eventType);
        result.setPublicNotice(publicNotice);
        result.setShortDescription(shortDescription);
        result.setLastUpdateDate(lastUpdateDate);
        result.setCreationDate(creationDate);
        result.setLastUpdatedBy(lastUpdatedBy);
        result.setCreatedBy(createdBy);
        result.setVersion(version);
        eventDescId = result.getPrimaryKey();
        assertNotNull(eventDescId, NOTNULL);
        return new XhbCourtLogEventDescDao(result);
    }

}
