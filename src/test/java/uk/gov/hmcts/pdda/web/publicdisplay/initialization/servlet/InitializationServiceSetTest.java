package uk.gov.hmcts.pdda.web.publicdisplay.initialization.servlet;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Topic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.web.publicdisplay.configuration.DisplayConfigurationReader;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessagingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InitializationServiceSetTest {

    private static final String EQUALS = "Results are not Equal";
    private static final String TRUE = "Result is not True";
    private static final Long DELAY = Long.valueOf(1);
    private static final Integer NO_OF_WORKERS = Integer.valueOf(1);
    private static final Long RETRY = Long.valueOf(1);

    @Mock
    private ConnectionFactory mockConnectionFactory;

    @Mock
    private Topic mockTopic;

    @InjectMocks
    private final InitializationService classUnderTest = InitializationService.getInstance();

    @BeforeAll
    public static void setUp() throws Exception {
        Mockito.mockStatic(DisplayConfigurationReader.class);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        Mockito.clearAllCaches();
    }

    @Test
    void testSetNumSubscriptionWorkers() {
        boolean result = false;
        try {
            classUnderTest.setNumSubscriptionWorkers(NO_OF_WORKERS);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testSetNumInitializationWorkers() {
        boolean result = false;
        try {
            classUnderTest.setNumInitializationWorkers(NO_OF_WORKERS);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testSetInitializationDelay() {
        boolean result = false;
        try {
            classUnderTest.setInitializationDelay(DELAY);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testSetRetryPeriod() {
        boolean result = false;
        try {
            classUnderTest.setRetryPeriod(RETRY);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testSetMessagingMode() {
        boolean result = false;
        try {
            classUnderTest.setMessagingMode(MessagingMode.P2P);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testSetConnectionFactory() {
        classUnderTest.setConnectionFactory(mockConnectionFactory);
        assertEquals(mockConnectionFactory, classUnderTest.getConnectionFactory(), EQUALS);
    }

    @Test
    void testSetTopic() {
        classUnderTest.setTopic(mockTopic);
        assertEquals(mockTopic, classUnderTest.getTopic(), EQUALS);
    }
}
