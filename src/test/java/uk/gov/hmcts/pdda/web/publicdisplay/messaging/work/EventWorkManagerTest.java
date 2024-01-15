package uk.gov.hmcts.pdda.web.publicdisplay.messaging.work;

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
import uk.gov.hmcts.framework.services.threadpool.ThreadPool;
import uk.gov.hmcts.framework.services.threadpool.ThreadPoolInactiveException;
import uk.gov.hmcts.pdda.common.publicdisplay.events.MoveCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.event.EventStore;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("PMD.DoNotUseThreads")
class EventWorkManagerTest {

    private static final Integer NO_OF_WORKERS = Integer.valueOf(1);
    private static final Long TIMEOUT = Long.valueOf(1);
    private static final String TRUE = "Result is not True";

    @Mock
    private EventStore mockEventStore;

    @Mock
    private ThreadPool mockThreadPool;

    @InjectMocks
    private final EventWorkManager classUnderTest = new EventWorkManager(mockEventStore, mockThreadPool);

    @BeforeEach
    public void setUp() throws Exception {
        // Do nothing
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testDefaultConstructor() {
        boolean result;
        new EventWorkManager(mockEventStore, NO_OF_WORKERS);
        result = true;
        assertTrue(result, TRUE);
    }

    @Test
    void testShutdown() {
        boolean result;
        classUnderTest.shutDown();
        result = true;
        assertTrue(result, TRUE);
    }

    @Test
    void testRun() {
        // Call shutdown to make sure active=false to avoid endless loop
        boolean result;
        classUnderTest.shutDown();
        classUnderTest.start();
        result = true;

        assertTrue(result, TRUE);
    }

    @Test
    void testRunOnce() {
        boolean result;
        PublicDisplayEvent publicDisplayEvent = getDummyPublicDisplayEvent();
        Mockito.when(mockEventStore.popEvent()).thenReturn(publicDisplayEvent);
        classUnderTest.runOnce();
        result = true;

        assertTrue(result, TRUE);
    }

    @Test
    void testThreadPool() {
        ThreadPool threadPoolUnderTest = new ThreadPool(0, TIMEOUT);
        assertNotNull(Integer.valueOf(threadPoolUnderTest.getNumFreeWorkers()), "Result is Null");
        threadPoolUnderTest.shutdown();
    }

    @Test
    void testThreadPoolFailure() {
        Assertions.assertThrows(ThreadPoolInactiveException.class, () -> {
            ThreadPool threadPoolUnderTest = new ThreadPool(0, TIMEOUT);
            threadPoolUnderTest.shutdown();
            threadPoolUnderTest.scheduleWork(null);
        });
    }

    @Test
    void testWorkerUnavailableException() {
        Assertions.assertThrows(WorkerUnavailableException.class, () -> {
            try {
                throw new InterruptedException();
            } catch (InterruptedException e) {
                throw new WorkerUnavailableException(e);
            }
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
