package uk.gov.hmcts.pdda.business.services.publicdisplay.database.query;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>
 * Title: VipDisplayDocumentQueryTest Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2023
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 */
@ExtendWith(EasyMockExtension.class)
class VipDisplayDocumentQueryTest {

    private static final String TRUE = "Result is not True";

    @Mock
    protected EntityManager mockEntityManager;

    @Mock
    private XhbDisplayRepository mockXhbDisplayRepository;

    @Mock
    private XhbDisplayLocationRepository mockXhbDisplayLocationRepository;

    @Mock
    private XhbDisplayDocumentRepository mockXhbDisplayDocumentRepository;

    @Mock
    private XhbRotationSetDdRepository mockXhbRotationSetDdRepository;

    @TestSubject
    protected VipDisplayDocumentQuery classUnderTest =
        new VipDisplayDocumentQuery(mockEntityManager, mockXhbDisplayRepository, mockXhbDisplayLocationRepository,
            mockXhbDisplayDocumentRepository, mockXhbRotationSetDdRepository);

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
            classUnderTest = new VipDisplayDocumentQuery(mockEntityManager);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoListEmpty() {
        boolean result = testGetDataNoList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoListNoDisplay() {
        List<XhbDisplayLocationDao> xhbDisplayLocationDaoList = new ArrayList<>();
        xhbDisplayLocationDaoList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        boolean result =
            testGetDataNoList(xhbDisplayLocationDaoList, new ArrayList<>(), new ArrayList<>(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoListNoRotationSetDD() {
        List<XhbDisplayLocationDao> xhbDisplayLocationDaoList = new ArrayList<>();
        xhbDisplayLocationDaoList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        List<XhbDisplayDao> xhbDisplayDaoList = new ArrayList<>();
        xhbDisplayDaoList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        boolean result =
            testGetDataNoList(xhbDisplayLocationDaoList, xhbDisplayDaoList, new ArrayList<>(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoListNoDisplayDocument() {
        List<XhbDisplayLocationDao> xhbDisplayLocationDaoList = new ArrayList<>();
        xhbDisplayLocationDaoList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        List<XhbDisplayDao> xhbDisplayDaoList = new ArrayList<>();
        xhbDisplayDaoList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        boolean result =
            testGetDataNoList(xhbDisplayLocationDaoList, xhbDisplayDaoList, new ArrayList<>(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoListSuccess() {
        List<XhbDisplayLocationDao> xhbDisplayLocationDaoList = new ArrayList<>();
        xhbDisplayLocationDaoList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        List<XhbDisplayDao> xhbDisplayDaoList = new ArrayList<>();
        xhbDisplayDaoList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        List<XhbRotationSetDdDao> xhbRotationSetDdDaoList = new ArrayList<>();
        xhbRotationSetDdDaoList.add(DummyPublicDisplayUtil.getXhbRotationSetDdDao());
        boolean result = testGetDataNoList(xhbDisplayLocationDaoList, xhbDisplayDaoList, xhbRotationSetDdDaoList,
            Optional.of(DummyPublicDisplayUtil.getXhbDisplayDocumentDao()));
        assertTrue(result, TRUE);
    }

    private boolean testGetDataNoList(List<XhbDisplayLocationDao> xhbDisplayLocationDaoList,
        List<XhbDisplayDao> xhbDisplayDaoList, List<XhbRotationSetDdDao> xhbRotationSetDdDaoList,
        Optional<XhbDisplayDocumentDao> xhbDisplayDocumentDao) {
        // Setup
        final Integer courtSiteId = Integer.valueOf(81);
        List<AbstractRepository<?>> replayArray = new ArrayList<>();

        // Expects
        boolean abortExpects;
        EasyMock.expect(mockXhbDisplayLocationRepository.findByVipCourtSite(EasyMock.isA(Integer.class)))
            .andReturn(xhbDisplayLocationDaoList);
        replayArray.add(mockXhbDisplayLocationRepository);
        abortExpects = xhbDisplayLocationDaoList.isEmpty();
        if (!abortExpects) {
            EasyMock.expect(mockXhbDisplayRepository.findByDisplayLocationId(EasyMock.isA(Integer.class)))
                .andReturn(xhbDisplayDaoList);
            replayArray.add(mockXhbDisplayRepository);
            abortExpects = xhbDisplayDaoList.isEmpty();
        }
        if (!abortExpects) {
            EasyMock.expect(mockXhbRotationSetDdRepository.findByRotationSetId(EasyMock.isA(Integer.class)))
                .andReturn(xhbRotationSetDdDaoList);
            replayArray.add(mockXhbRotationSetDdRepository);
            abortExpects = xhbRotationSetDdDaoList.isEmpty();
        }
        if (!abortExpects) {
            EasyMock.expect(mockXhbDisplayDocumentRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(xhbDisplayDocumentDao);
            replayArray.add(mockXhbDisplayDocumentRepository);
        }

        // Replays
        for (AbstractRepository<?> repository : replayArray) {
            EasyMock.replay(repository);
        }

        // Run
        classUnderTest.getData(courtSiteId);

        // Checks
        for (AbstractRepository<?> repository : replayArray) {
            EasyMock.verify(repository);
        }
        return true;
    }
}
