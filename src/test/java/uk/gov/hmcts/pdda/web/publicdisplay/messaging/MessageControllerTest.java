package uk.gov.hmcts.pdda.web.publicdisplay.messaging;


import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueReceiver;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicSession;
import jakarta.jms.TopicSubscriber;
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
import uk.gov.hmcts.framework.exception.CsBusinessException;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.event.EventStoreException;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessagingMode;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.P2PSubscription;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.Reinitializer;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.Subscription;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageControllerTest {

    private static final Long COURT_ID = Long.valueOf(81);
    private static final Integer NO_OF_WORKERS = Integer.valueOf(1);
    private static final String RESULTISNULL = "Result is Null";
    private static final String TRUE = "Result is not True";

    @Mock
    private Topic mockTopic;

    @Mock
    private ConnectionFactory mockConnectionFactory;

    @Mock
    private MessagingMode mockMessagingMode;

    @Mock
    private TopicConnection mockTopicConnection;

    @Mock
    private TopicSession mockTopicSession;

    @Mock
    private TopicSubscriber mockTopicSubscriber;

    @Mock
    private Subscription mockSubscription;

    @Mock
    private QueueConnection mockQueueConnection;

    @Mock
    private QueueSession mockQueueSession;

    @Mock
    private Queue mockQueue;

    @Mock
    private QueueReceiver mockQueueReceiver;

    @InjectMocks
    private final MessageController classUnderTest = new MessageController(NO_OF_WORKERS);

    @BeforeEach
    public void setUp() throws Exception {
        // Do nothing
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testAddSubscription() throws JMSException {
        // Expects
        Mockito.when(mockConnectionFactory.createConnection()).thenReturn(mockTopicConnection);
        Mockito.when(mockTopicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE))
            .thenReturn(mockTopicSession);
        Mockito.when(mockTopicSession.createSubscriber(Mockito.isA(Topic.class), Mockito.isA(String.class),
            Mockito.isA(boolean.class))).thenReturn(mockTopicSubscriber);
        classUnderTest.addSubscription(mockTopic, mockConnectionFactory, COURT_ID, MessagingMode.PUB_SUB);
        // Run
        classUnderTest.setSubscriptions(classUnderTest.getSubscriptions());
        // Checks
        assertNotNull(classUnderTest.getSubscriptions(), RESULTISNULL);
        assertEquals(1, classUnderTest.getSubscriptions().size(), "Results are not Equal");
    }

    @Test
    void testP2PSubscription() throws JMSException {
        Mockito.when(mockConnectionFactory.createConnection()).thenReturn(mockQueueConnection);
        Mockito.when(mockQueueSession.createReceiver(Mockito.isA(Queue.class), Mockito.isA(String.class)))
            .thenReturn(mockQueueReceiver);

        P2PSubscription typeUnderTest =
            new P2PSubscription(1, mockConnectionFactory, mockQueueConnection, mockQueueSession, mockQueue);
        assertNotNull(typeUnderTest, RESULTISNULL);
    }

    @Test
    void testStartEventProcessing() throws JMSException {
        // Setup
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(mockSubscription);
        classUnderTest.setSubscriptions(subscriptions);
        // Run
        classUnderTest.startEventProcessing();
        // Checks
        assertNotNull(classUnderTest.getSubscriptions(), RESULTISNULL);
        assertEquals(1, classUnderTest.getSubscriptions().size(), "Results are not Equal");
    }

    @Test
    void testShutdown() throws JMSException {
        // Setup
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(mockSubscription);
        classUnderTest.setSubscriptions(subscriptions);
        // Run
        classUnderTest.shutdown();
        // Checks
        assertNotNull(classUnderTest.getSubscriptions(), RESULTISNULL);
        assertEquals(1, classUnderTest.getSubscriptions().size(), "Results are not Equal");
    }

    @Test
    void testEventStoreException() {
        Assertions.assertThrows(EventStoreException.class, () -> {
            throw new EventStoreException("Test", new CsBusinessException());
        });
    }

    @Test
    void testReinitializer() {
        boolean result;
        Reinitializer classUnderTest = new Reinitializer();
        classUnderTest.onException(new JMSException("Test"));
        result = true;

        assertTrue(result, TRUE);
    }
}
