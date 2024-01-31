package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.CourtRoomNotFoundException;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.DisplayNotFoundException;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.RotationSetNotFoundCheckedException;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayNotifier;
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

    private static final String NOT_TRUE = "Result is Not True";
    private static final String NULL = "Result is Null";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbDisplayRepository mockXhbDisplayRepository;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbRotationSetsRepository mockXhbRotationSetsRepository;

    @Mock
    private XhbCourtRoomRepository xhbCourtRoomRepository;

    @Mock
    private PublicDisplayNotifier mockPublicDisplayNotifier;

    @TestSubject
    private final DisplayConfigurationHelper classUnderTest = new DisplayConfigurationHelper();

    @Test
    void testDefaultConstructor() {
        boolean result = false;
        try {
            new DisplayConfigurationHelper();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testGetDisplayConfiguration() {
        // Setup
        XhbDisplayDao displayDao = DummyPublicDisplayUtil.getXhbDisplayDao();
        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(displayDao));
        EasyMock.replay(mockXhbDisplayRepository);
        // Run
        DisplayConfiguration result = classUnderTest.getDisplayConfiguration(0,
            mockXhbDisplayRepository, mockXhbCourtRepository);
        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        assertNotNull(result, NULL);
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

        XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();
        xhbDisplayDao.setXhbCourtRooms(roomList);

        XhbCourtDao xhbCourtDao = DummyCourtUtil.getXhbCourtDao(80, "TestCourt");
        List<XhbCourtSiteDao> xhbCourtSites = new ArrayList<>();
        xhbCourtSites.add(DummyCourtUtil.getXhbCourtSiteDao());
        xhbCourtSites.add(DummyCourtUtil.getXhbCourtSiteDao());
        xhbCourtDao.setXhbCourtSites(xhbCourtSites);

        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbDisplayDao));
        EasyMock.expect(mockXhbCourtRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbCourtDao));

        EasyMock.replay(mockXhbDisplayRepository);
        EasyMock.replay(mockXhbCourtRepository);

        // Run
        DisplayConfiguration result = classUnderTest.getDisplayConfiguration(0,
            mockXhbDisplayRepository, mockXhbCourtRepository);

        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        EasyMock.verify(mockXhbCourtRepository);
        assertNotNull(result, NULL);
    }

    @Test
    void testUpdateDisplayConfiguration() {
        // Setup
        XhbRotationSetsDao xhbRotationSetsDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
        XhbCourtRoomDao[] roomArray = {DummyCourtUtil.getXhbCourtRoomDao()};
        XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();

        DisplayConfiguration displayConfiguration =
            new DisplayConfiguration(xhbDisplayDao, xhbRotationSetsDao, roomArray);
        displayConfiguration.setRotationSetDao(xhbRotationSetsDao);
        displayConfiguration.setCourtRoomDaosWithCourtRoomChanged(roomArray);

        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbDisplayDao));
        // Going down the isCourtRoomsChanged() route
        EasyMock.expect(mockXhbDisplayRepository.update(xhbDisplayDao))
            .andReturn(Optional.of(xhbDisplayDao));
        EasyMock.expect(xhbCourtRoomRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(DummyCourtUtil.getXhbCourtRoomDao()));
        // Going down the isRotationSetChanged() route
        EasyMock.expect(mockXhbRotationSetsRepository.findById(EasyMock.isA(Long.class)))
            .andReturn(Optional.of(xhbRotationSetsDao));

        EasyMock.replay(mockXhbDisplayRepository);
        EasyMock.replay(mockXhbRotationSetsRepository);
        EasyMock.replay(xhbCourtRoomRepository);

        final boolean result = true;
        
        // Run
        classUnderTest.updateDisplayConfiguration(displayConfiguration, mockPublicDisplayNotifier,
            mockXhbDisplayRepository, mockXhbRotationSetsRepository, xhbCourtRoomRepository);

        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        EasyMock.verify(mockXhbRotationSetsRepository);
        EasyMock.verify(xhbCourtRoomRepository);
        assertTrue(result, NOT_TRUE);
    }
    
    @Test
    void testUpdateDisplayConfigurationRotationSetRoute() {
        // Setup
        XhbRotationSetsDao xhbRotationSetsDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
        XhbCourtRoomDao[] roomArray = {DummyCourtUtil.getXhbCourtRoomDao()};
        XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();

        DisplayConfiguration displayConfiguration =
            new DisplayConfiguration(xhbDisplayDao, xhbRotationSetsDao, roomArray);
        displayConfiguration.setRotationSetDao(xhbRotationSetsDao);

        EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbDisplayDao));
        // Going down the isRotationSetChanged() route
        EasyMock.expect(mockXhbRotationSetsRepository.findById(EasyMock.isA(Long.class)))
            .andReturn(Optional.of(xhbRotationSetsDao));

        EasyMock.replay(mockXhbDisplayRepository);
        EasyMock.replay(mockXhbRotationSetsRepository);
        
        final boolean result = true;

        // Run
        classUnderTest.updateDisplayConfiguration(displayConfiguration, mockPublicDisplayNotifier,
            mockXhbDisplayRepository, mockXhbRotationSetsRepository, xhbCourtRoomRepository);

        // Checks
        EasyMock.verify(mockXhbDisplayRepository);
        EasyMock.verify(mockXhbRotationSetsRepository);
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testUpdateDisplayConfigurationEmptyDisplay() {
        Assertions.assertThrows(DisplayNotFoundException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetsDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            XhbCourtRoomDao[] roomArray =
                {DummyCourtUtil.getXhbCourtRoomDao(), DummyCourtUtil.getXhbCourtRoomDao()};
            XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();

            DisplayConfiguration displayConfiguration =
                new DisplayConfiguration(xhbDisplayDao, xhbRotationSetsDao, roomArray);

            EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(Optional.empty());

            EasyMock.replay(mockXhbDisplayRepository);

            // Run
            classUnderTest.updateDisplayConfiguration(displayConfiguration,
                mockPublicDisplayNotifier, mockEntityManager);
        });
    }

    @Test
    void testUpdateDisplayConfigurationEmptyRotationSet() {
        Assertions.assertThrows(RotationSetNotFoundCheckedException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetsDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            XhbCourtRoomDao[] roomArray = {DummyCourtUtil.getXhbCourtRoomDao()};
            XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();

            DisplayConfiguration displayConfiguration =
                new DisplayConfiguration(xhbDisplayDao, xhbRotationSetsDao, roomArray);
            displayConfiguration.setRotationSetDao(xhbRotationSetsDao);

            EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(Optional.of(xhbDisplayDao));

            // Going down the isRotationSetChanged() route
            EasyMock.expect(mockXhbRotationSetsRepository.findById(EasyMock.isA(Long.class)))
                .andReturn(Optional.empty());

            EasyMock.replay(mockXhbDisplayRepository);
            EasyMock.replay(mockXhbRotationSetsRepository);

            // Run
            classUnderTest.updateDisplayConfiguration(displayConfiguration,
                mockPublicDisplayNotifier, mockXhbDisplayRepository, mockXhbRotationSetsRepository,
                xhbCourtRoomRepository);
        });
    }

    @Test
    void testUpdateDisplayConfigurationEmptyCourtRoom() {
        Assertions.assertThrows(CourtRoomNotFoundException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetsDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            XhbCourtRoomDao[] roomArray = {DummyCourtUtil.getXhbCourtRoomDao()};
            XhbDisplayDao xhbDisplayDao = DummyPublicDisplayUtil.getXhbDisplayDao();

            DisplayConfiguration displayConfiguration =
                new DisplayConfiguration(xhbDisplayDao, xhbRotationSetsDao, roomArray);
            displayConfiguration.setCourtRoomDaosWithCourtRoomChanged(roomArray);

            EasyMock.expect(mockXhbDisplayRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(Optional.of(xhbDisplayDao));
            // Going down the isCourtRoomsChanged() route
            EasyMock.expect(mockXhbDisplayRepository.update(xhbDisplayDao))
                .andReturn(Optional.of(xhbDisplayDao));
            EasyMock.expect(xhbCourtRoomRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(Optional.empty());
            // Going down the isRotationSetChanged() route
            EasyMock.replay(mockXhbDisplayRepository);
            EasyMock.replay(xhbCourtRoomRepository);

            // Run
            classUnderTest.updateDisplayConfiguration(displayConfiguration,
                mockPublicDisplayNotifier, mockXhbDisplayRepository, mockXhbRotationSetsRepository,
                xhbCourtRoomRepository);
        });
    }
}
