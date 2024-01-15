package uk.gov.hmcts.pdda.web.publicdisplay.messaging.work;

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
import uk.gov.hmcts.pdda.web.publicdisplay.error.ErrorGatherer;
import uk.gov.hmcts.pdda.web.publicdisplay.error.ProcessingError;
import uk.gov.hmcts.pdda.web.publicdisplay.initialization.ProcessingInstance;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.WorkFlowContext;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.WorkFlowManager;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.impl.DefaultWorkFlowManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EventWorkTest {

    private static final String EQUALS = "Results are not Equal";
    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is not True";

    @Mock
    private WorkFlowContext mockWorkFlowContext;

    @Mock
    private DefaultWorkFlowManager mockDefaultWorkFlowManager;

    @Mock
    private ErrorGatherer mockErrorGatherer;

    @Mock
    private Throwable mockThrowable;

    @Mock
    private ProcessingInstance mockProcessingInstance;

    @InjectMocks
    private final EventWork classUnderTest = new EventWork(getDummyPublicDisplayEvent());

    @BeforeEach
    public void setUp() throws Exception {
        Mockito.mockStatic(WorkFlowContext.class);
        Mockito.mockStatic(WorkFlowManager.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.clearAllCaches();
    }

    @Test
    void testRunSuccess() {
        Mockito.when(WorkFlowContext.newInstance()).thenReturn(mockWorkFlowContext);
        Mockito.when(WorkFlowManager.getInstance(mockWorkFlowContext)).thenReturn(mockDefaultWorkFlowManager);
        boolean result = false;
        try {
            classUnderTest.run();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testRunFailure() {
        Mockito.when(WorkFlowContext.newInstance()).thenReturn(mockWorkFlowContext);
        boolean result = false;
        try {
            classUnderTest.run();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testProcessingError() {
        ProcessingError processingErrorUnderTest = getDummyProcessingError();
        assertNotNull(processingErrorUnderTest.getEvent(), NOTNULL);
        assertNotNull(processingErrorUnderTest.getProcessingInstance(), NOTNULL);
        assertNotNull(processingErrorUnderTest.getException(), NOTNULL);
        assertNotNull(processingErrorUnderTest.getTime(), NOTNULL);
    }

    @Test
    void testErrorGatherer() {
        // Mockito.when(ErrorGatherer.getInstance()).thenReturn(mockErrorGatherer);
        ErrorGatherer errorGathererUnderTest = ErrorGatherer.getInstance();
        errorGathererUnderTest.setSize(1);
        errorGathererUnderTest.addError(getDummyProcessingError());
        assertEquals(1, errorGathererUnderTest.getErrors().length, EQUALS);
        errorGathererUnderTest.addError(getDummyProcessingError());
        assertEquals(1, errorGathererUnderTest.getErrors().length, EQUALS);
        errorGathererUnderTest.flush();
        assertEquals(0, errorGathererUnderTest.getErrors().length, EQUALS);
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

    private ProcessingError getDummyProcessingError() {
        return new ProcessingError(getDummyPublicDisplayEvent(), mockThrowable, mockProcessingInstance);
    }
}
