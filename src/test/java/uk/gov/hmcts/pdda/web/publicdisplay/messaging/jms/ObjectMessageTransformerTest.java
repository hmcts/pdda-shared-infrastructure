package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.common.publicdisplay.events.MoveCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.UnexpectedJmsException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ObjectMessageTransformerTest {

    @Mock
    private TextMessage mockTextMessage;

    @Mock
    private ObjectMessage mockObjectMessage;

    @InjectMocks
    private final ObjectMessageTransformer classUnderTest = new ObjectMessageTransformer();

    @BeforeEach
    public void setUp() throws Exception {
        // Do nothing
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testTransformSuccess() throws JMSException {
        // Setup
        PublicDisplayEvent publicDisplayEvent = getDummyPublicDisplayEvent();
        // Expects
        Mockito.when(mockObjectMessage.getObject()).thenReturn(publicDisplayEvent);
        // Run
        PublicDisplayEvent result = classUnderTest.transform(mockObjectMessage);
        // Checks
        assertNotNull(result, "Result is Null");
    }

    @Test
    void testTransformInvalidParam() {
        Assertions.assertThrows(InvalidMessageException.class, () -> {
            classUnderTest.transform(mockTextMessage);
        });
    }

    @Test
    void testTransformFailure() {
        Assertions.assertThrows(UnexpectedJmsException.class, () -> {
            Mockito.doThrow(JMSException.class).when(mockObjectMessage).getObject();

            classUnderTest.transform(mockObjectMessage);
        });
    }

    @Test
    void testTransformInvalidTypeFailure() throws JMSException {
        Assertions.assertThrows(InvalidMessageException.class, () -> {
            // Setup
            XhbCourtDao invalidType = new XhbCourtDao();
            // Expects
            Mockito.when(mockObjectMessage.getObject()).thenReturn(invalidType);
            // Run
            classUnderTest.transform(mockObjectMessage);
        });
    }

    private PublicDisplayEvent getDummyPublicDisplayEvent() {
        return getDummyMoveCaseEvent();
    }

    private MoveCaseEvent getDummyMoveCaseEvent() {
        CourtRoomIdentifier from = new CourtRoomIdentifier(Integer.valueOf(-99), null);
        CourtRoomIdentifier to = new CourtRoomIdentifier(Integer.valueOf(-1), null);
        from.setCourtId(from.getCourtId());
        from.setCourtRoomId(from.getCourtRoomId());
        return new MoveCaseEvent(from, to, null);
    }
}
