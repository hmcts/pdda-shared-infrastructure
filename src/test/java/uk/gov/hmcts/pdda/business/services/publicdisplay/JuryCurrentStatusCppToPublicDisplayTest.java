package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyFormattingUtil;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingDao;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.services.cppformatting.CppFormattingHelper;
import uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.cpptoxhibit.JuryCurrentStatusCppToPublicDisplay;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(EasyMockExtension.class)
class JuryCurrentStatusCppToPublicDisplayTest {

    private static final String EQUALS = "Results are not Equal";
    private static final String TRUE = "Result is not True";
    private static final Integer COURT_ID = 94;
    private static final String COURT_NAME = "Test Court";
    private static final Date LIST_DATE = new Date();
    private static final int[] ROOM_ARRAY = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbCppFormattingRepository mockXhbCppFormattingRepository;

    @Mock
    private XhbClobRepository mockXhbClobRepository;

    @Mock
    private CppFormattingHelper mockCppFormattingHelper;

    @TestSubject
    private final JuryCurrentStatusCppToPublicDisplay classUnderTest = new JuryCurrentStatusCppToPublicDisplay(
        LIST_DATE, COURT_ID, ROOM_ARRAY, mockXhbCourtRepository, mockXhbClobRepository, mockCppFormattingHelper);

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testDefaultConstructor() {
        boolean result = false;
        try {
            new JuryCurrentStatusCppToPublicDisplay(LIST_DATE, COURT_ID, ROOM_ARRAY);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetCppData() {
        // Setup
        XhbCourtDao courtDao = DummyCourtUtil.getXhbCourtDao(COURT_ID, COURT_NAME);
        ArrayList<XhbCppFormattingDao> xcfList = new ArrayList<>();
        XhbCppFormattingDao xhbCppFormattingDao = DummyFormattingUtil.getXhbCppFormattingDao();
        xcfList.add(xhbCppFormattingDao);
        XhbClobDao clobDao = DummyFormattingUtil.getXhbClobDao(xcfList.get(0).getXmlDocumentClobId(),
            AllCourtStatusCppToPublicDisplayTest.CPP_XML);

        EasyMock.expect(mockXhbCourtRepository.findById(EasyMock.isA(Integer.class))).andReturn(Optional.of(courtDao));

        EasyMock.expect(mockXhbCppFormattingRepository.getLatestDocumentByCourtIdAndType(EasyMock.isA(Integer.class),
            EasyMock.isA(String.class), EasyMock.isA(LocalDateTime.class))).andReturn(xcfList.get(0));

        EasyMock.expect(mockXhbClobRepository.findById(EasyMock.isA(Long.class))).andReturn(Optional.of(clobDao));

        EasyMock.expect(mockCppFormattingHelper.getLatestPublicDisplayDocument(EasyMock.isA(Integer.class),
            EasyMock.isA(EntityManager.class))).andReturn(xhbCppFormattingDao);

        EasyMock.replay(mockXhbCourtRepository);
        EasyMock.replay(mockXhbCppFormattingRepository);
        EasyMock.replay(mockXhbClobRepository);
        EasyMock.replay(mockCppFormattingHelper);

        /**
         * Run Method Note that due to running this with PowerMockRunner, the call to
         * AbstractXMLUtils.getDocBuilder() and subsequent call of DocumentBuilder.parse caused issues. The
         * only workaround was to use PowerMockIgnore which has a side effect of not being able to create a
         * valid XML Document that we can use or Mock.
         */
        classUnderTest.getCppData(mockEntityManager);

        // Checks
        assertArrayEquals(ROOM_ARRAY, classUnderTest.getCourtRoomIds(), EQUALS);
        assertEquals(COURT_NAME, classUnderTest.getCourtName(), EQUALS);
        assertEquals(COURT_ID, Integer.valueOf(classUnderTest.getCourtId()), EQUALS);
        assertEquals(LIST_DATE, classUnderTest.getDate(), EQUALS);
    }
}
