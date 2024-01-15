package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsRepository;
import uk.gov.hmcts.pdda.common.publicdisplay.util.StringUtilities;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.CourtSitePdComplexValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DisplayLocationDataHelperTest {

    private static final String TRUE = "Result is not True";
    private static final String NOTNULL = "Result is Null";

    @Mock
    private ResourceBundle mockResourceBundle;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbCourtSiteRepository mockXhbCourtSiteRepository;

    @Mock
    private XhbRotationSetsRepository mockXhbRotationSetsRepository;

    @Mock
    private XhbDisplayRepository mockXhbDisplayRepository;

    @Mock
    private Query mockQuery;

    @InjectMocks
    private final DisplayLocationDataHelper classUnderTest = new DisplayLocationDataHelper();

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
            new DisplayLocationDataHelper();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDisplaysForCourtActual() {
        // Setup
        Integer courtId = Integer.valueOf(-1);
        List<XhbCourtSiteDao> xhbCourtSiteDaoList = new ArrayList<>();
        xhbCourtSiteDaoList.add(DummyCourtUtil.getXhbCourtSiteDao());
        // Expects
        Mockito.when(mockXhbCourtSiteRepository.findByCourtId(Mockito.isA(Integer.class)))
            .thenReturn(xhbCourtSiteDaoList);
        // Run
        boolean result = false;
        try {
            DisplayLocationDataHelper.getDisplaysForCourt(courtId, mockXhbCourtSiteRepository);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetRotationSetsDetailForCourtActual() {
        // Setup
        Integer courtId = Integer.valueOf(-1);
        List<XhbRotationSetsDao> xhbRotationSetsDaoList = new ArrayList<>();
        xhbRotationSetsDaoList.add(DummyPublicDisplayUtil.getXhbRotationSetsDao());
        List<XhbDisplayDao> xhbDisplayDaoList = new ArrayList<>();
        xhbDisplayDaoList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        // Expects
        Mockito.when(mockXhbRotationSetsRepository.findByCourtId(Mockito.isA(Integer.class)))
            .thenReturn(xhbRotationSetsDaoList);
        Mockito.when(mockXhbDisplayRepository.findByRotationSetId(Mockito.isA(Integer.class)))
            .thenReturn(xhbDisplayDaoList);
        Mockito.when(mockResourceBundle.getString(Mockito.isA(String.class))).thenReturn("TranslatedText");
        // Run
        boolean result = false;
        try {
            DisplayLocationDataHelper.getRotationSetsDetailForCourt(courtId, mockResourceBundle,
                mockXhbRotationSetsRepository, mockXhbDisplayRepository);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDisplaysForCourtWrapper() {
        Integer courtId = Integer.valueOf(81);
        Mockito.when(mockEntityManager.createNamedQuery(Mockito.isA(String.class))).thenReturn(mockQuery);
        CourtSitePdComplexValue[] results = DisplayLocationDataHelper.getDisplaysForCourt(courtId, mockEntityManager);
        assertNotNull(results, NOTNULL);
    }

    @Test
    void testGetRotationSetsDetailForCourtWrapper() {
        Integer courtId = Integer.valueOf(81);
        Mockito.when(mockEntityManager.createNamedQuery(Mockito.isA(String.class))).thenReturn(mockQuery);
        boolean result = false;
        try {
            DisplayLocationDataHelper.getRotationSetsDetailForCourt(courtId, Locale.UK, mockEntityManager);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testToSentenceCase() {
        boolean result = false;
        try {
            StringUtilities.toSentenceCase("test This");
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }
}
