package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.common.publicdisplay.events.MoveCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayJmsConstants;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageTransformerFactoryTest {

    private static final Integer COURT_ID = Integer.valueOf(81);

    @Mock
    private TextMessage mockTextMessage;

    @Mock
    private ObjectMessage mockObjectMessage;

    @Mock
    private Message mockMessage;

    @BeforeAll
    public static void setUp() throws Exception {
        Mockito.mockStatic(TextMessageTransformer.class);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        Mockito.clearAllCaches();
    }

    @Test
    void testTransformTextMessage() throws JMSException {
        // Expect
        Mockito.when(mockTextMessage.getIntProperty(PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME))
            .thenReturn(COURT_ID);
        // Run
        PublicDisplayEvent result = MessageTransformerFactory.transform(mockTextMessage);
        // Checks
        assertNotNull(result, "Result is Null");
    }

    @Test
    void testTransformObjectMessage() throws JMSException {
        // Setup
        PublicDisplayEvent publicDisplayEvent = getDummyPublicDisplayEvent();
        // Expect
        Mockito.when(mockObjectMessage.getObject()).thenReturn(publicDisplayEvent);
        // Run
        PublicDisplayEvent result = MessageTransformerFactory.transform(mockObjectMessage);
        // Checks
        assertNotNull(result, "Result is Null");
    }

    @Test
    void testTransformFailure() throws JMSException {
        Assertions.assertThrows(InvalidMessageException.class, () -> {
            MessageTransformerFactory.transform(mockMessage);
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
