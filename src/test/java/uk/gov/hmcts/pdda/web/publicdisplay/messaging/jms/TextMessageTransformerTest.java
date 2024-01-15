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
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayJmsConstants;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.UnexpectedJmsException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TextMessageTransformerTest {

    private static final Integer COURT_ID = Integer.valueOf(81);

    @Mock
    private TextMessage mockTextMessage;

    @Mock
    private ObjectMessage mockObjectMessage;

    @InjectMocks
    private final TextMessageTransformer classUnderTest = new TextMessageTransformer();

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
        Mockito.when(mockTextMessage.getIntProperty(PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME))
            .thenReturn(COURT_ID);

        PublicDisplayEvent result = classUnderTest.transform(mockTextMessage);

        assertNotNull(result, "Result is Null");
    }

    @Test
    void testTransformInvalidParam() {
        Assertions.assertThrows(InvalidMessageException.class, () -> {
            classUnderTest.transform(mockObjectMessage);
        });
    }

    @Test
    void testTransformFailure() {
        Assertions.assertThrows(UnexpectedJmsException.class, () -> {
            Mockito.doThrow(JMSException.class).when(mockTextMessage)
                .getIntProperty(PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME);

            classUnderTest.transform(mockTextMessage);
        });
    }
}
