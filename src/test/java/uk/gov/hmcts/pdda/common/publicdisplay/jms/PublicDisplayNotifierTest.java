package uk.gov.hmcts.pdda.common.publicdisplay.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicPublisher;
import jakarta.jms.TopicSession;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.common.publicdisplay.events.MoveCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.web.publicdisplay.initialization.servlet.InitializationService;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PublicDisplayNotifierTest {

    @Mock
    private TopicConnection mockTopicConnection;

    @Mock
    private TopicSession mockTopicSession;

    @Mock
    private ActiveMQObjectMessage mockActiveMqObjectMessage;

    @Mock
    private Topic mockTopic;

    @Mock
    private TopicPublisher mockTopicPublisher;

    @Mock
    private InitializationService mockInitializationService;

    @InjectMocks
    private final PublicDisplayNotifier classUnderTest = new PublicDisplayNotifier();

    @BeforeEach
    public void setup() throws Exception {
        Mockito.mockStatic(InitializationService.class);
    }

    @AfterEach
    public void teardown() throws Exception {
        Mockito.clearAllCaches();
    }

    @Test
    void testSendMessage() throws JMSException {
        // Setup
        PublicDisplayEvent publicDisplayEvent = getDummyPublicDisplayEvent();
        // Expects
        Mockito.when(mockTopicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE))
            .thenReturn(mockTopicSession);
        Mockito.when(mockTopicSession.createObjectMessage(Mockito.isA(Serializable.class)))
            .thenReturn(mockActiveMqObjectMessage);
        Mockito.when(mockActiveMqObjectMessage.getObject()).thenReturn(publicDisplayEvent);
        Mockito.when(InitializationService.getInstance()).thenReturn(mockInitializationService);
        Mockito.when(InitializationService.getInstance().getTopic()).thenReturn(mockTopic);
        Mockito.when(mockTopicSession.createPublisher(mockTopic)).thenReturn(mockTopicPublisher);
        // Run
        classUnderTest.sendMessage(publicDisplayEvent);
        assertNotNull(publicDisplayEvent, "Result is Null");
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
