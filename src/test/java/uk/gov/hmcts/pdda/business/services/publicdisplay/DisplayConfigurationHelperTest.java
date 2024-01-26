package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.DisplayNotFoundException;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.DisplayConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("static-access")
@ExtendWith(EasyMockExtension.class)
class DisplayConfigurationHelperTest {

    private static final String TRUE = "Result is not True";
    private static final String NOT_NULL = "Result is Not Null";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbDisplayRepository mockXhbDisplayRepository;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @TestSubject
    private final DisplayConfigurationHelper classUnderTest = new DisplayConfigurationHelper();

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
            new DisplayConfigurationHelper();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDisplayConfiguration() {
        // Setup
        XhbDisplayDao displayDao = DummyPublicDisplayUtil.getXhbDisplayDao();
        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(displayDao));
        EasyMock.replay(mockXhbDisplayRepository);
        // Run
        DisplayConfiguration result =
            classUnderTest.getDisplayConfiguration(0, mockEntityManager, mockXhbDisplayRepository);
        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        assertNotNull(result, NOT_NULL);
    }

    @Test
    void testGetDisplayConfigurationNull() {
        Assertions.assertThrows(DisplayNotFoundException.class, () -> {
            classUnderTest.getDisplayConfiguration(0, mockEntityManager);
        });
    }

    @Test
    void testGetDisplayConfigurationMultiSite() {
        // Setup
        List<XhbCourtRoomDao> roomList = new ArrayList<>();
        XhbCourtRoomDao xhbCourtRoomDao = DummyCourtUtil.getXhbCourtRoomDao();
        xhbCourtRoomDao.setXhbCourtSite(DummyCourtUtil.getXhbCourtSiteDao());
        roomList.add(xhbCourtRoomDao);
        
        XhbDisplayDao displayDao = DummyPublicDisplayUtil.getXhbDisplayDao();
        displayDao.setXhbCourtRooms(roomList);
        
        // XhbCourtDao xhbCourtDao = DummyCourtUtil.getXhbCourtDao(80, "TestCourt");
        // List<XhbCourtSiteDao> xhbCourtSites = new ArrayList<>();
        // xhbCourtSites.add(DummyCourtUtil.getXhbCourtSiteDao());
        // xhbCourtSites.add(DummyCourtUtil.getXhbCourtSiteDao());
        // xhbCourtDao.setXhbCourtSites(xhbCourtSites);

        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(displayDao));
        
        EasyMock.replay(mockXhbDisplayRepository);

        // Run
        DisplayConfiguration result =
            classUnderTest.getDisplayConfiguration(0, mockEntityManager, mockXhbDisplayRepository);

        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        assertNotNull(result, NOT_NULL);
    }
}
