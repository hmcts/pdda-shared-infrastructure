
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
import uk.gov.hmcts.framework.business.exceptions.CourtNotFoundException;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.setup.ejb.PdSetupControllerBean;
import uk.gov.hmcts.pdda.common.publicdisplay.setup.drilldown.CourtDrillDown;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>
 * Title: PDSetupControllerBean Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2022
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Chris Vincent
 */
@ExtendWith(EasyMockExtension.class)
class PdSetupControllerBeanTest {

    private static final String EQUALS = "Results are not Equal";
    private static final String TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbCourtSiteRepository mockXhbCourtSiteRepository;

    @Mock
    private XhbDisplayLocationRepository mockXhbDisplayLocationRepository;

    @Mock
    private XhbDisplayRepository mockXhbDisplayRepository;

    @TestSubject
    private final PdSetupControllerBean classUnderTest = new PdSetupControllerBean(mockEntityManager);

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
            new PdSetupControllerBean();
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    @SuppressWarnings("unused")
    void testGetDrillDownForCourt() {
        // Setup
        Integer courtId = 94;
        XhbCourtDao courtDao = DummyCourtUtil.getXhbCourtDao(courtId, "Test Court");
        List<XhbCourtSiteDao> courtSiteList = (ArrayList<XhbCourtSiteDao>) getDummyCourtSiteList();

        EasyMock.expect(mockXhbCourtRepository.findById(courtId)).andReturn(Optional.of(courtDao));
        EasyMock.expect(mockXhbCourtSiteRepository.findByCrestCourtIdValue(EasyMock.isA(String.class)))
            .andReturn(courtSiteList);

        List<XhbDisplayLocationDao> displayLocationList;
        List<XhbDisplayDao> displaysList;

        for (XhbCourtSiteDao xhbCourtSite : courtSiteList) {
            displayLocationList = (ArrayList<XhbDisplayLocationDao>) getDummyDisplayLocationList();
            EasyMock.expect(mockXhbDisplayLocationRepository.findByCourtSite(EasyMock.isA(Integer.class)))
                .andReturn(displayLocationList);

            for (XhbDisplayLocationDao displayLocation : displayLocationList) {
                displaysList = (ArrayList<XhbDisplayDao>) getDummyDisplayList();
                EasyMock.expect(mockXhbDisplayRepository.findByDisplayLocationId(EasyMock.isA(Integer.class)))
                    .andReturn(displaysList);
            }
        }

        replayMocks();

        // Run
        CourtDrillDown result = classUnderTest.getDrillDownForCourt(courtId);

        // Checks
        verifyMocks();
        assertEquals(2, result.getValues().size(), EQUALS);
    }

    @Test
    void testGetDrillDownForCourtFailure() {
        // Setup
        Integer courtId = 94;

        Assertions.assertThrows(CourtNotFoundException.class, () -> {
            EasyMock.expect(mockXhbCourtRepository.findById(courtId)).andReturn(Optional.empty());
            EasyMock.replay(mockXhbCourtRepository);
            // Run
            classUnderTest.getDrillDownForCourt(courtId);
        });
    }

    @Test
    void testGetAllCourts() {
        // Setup
        List<XhbCourtDao> dummyCourtList = new ArrayList<>();
        dummyCourtList.add(DummyCourtUtil.getXhbCourtDao(1, "TestCourt1"));
        dummyCourtList.add(DummyCourtUtil.getXhbCourtDao(3, "TestCourt3"));
        dummyCourtList.add(DummyCourtUtil.getXhbCourtDao(5, "TestCourt5"));
        EasyMock.expect(mockXhbCourtRepository.findAll()).andReturn(dummyCourtList);
        replayMocks();

        // Run
        XhbCourtDao[] result = classUnderTest.getAllCourts();

        // Checks
        verifyMocks();
        assertArrayEquals(dummyCourtList.toArray(), result, EQUALS);
    }

    /**
     * Replay the mocked objects.
     */
    private void replayMocks() {
        EasyMock.replay(mockXhbCourtRepository);
        EasyMock.replay(mockXhbCourtSiteRepository);
        EasyMock.replay(mockXhbDisplayLocationRepository);
        EasyMock.replay(mockXhbDisplayRepository);
    }

    /**
     * Verify the mocked objects.
     */
    private void verifyMocks() {
        EasyMock.verify(mockXhbCourtRepository);
        EasyMock.verify(mockXhbCourtSiteRepository);
    }

    private List<XhbCourtSiteDao> getDummyCourtSiteList() {
        List<XhbCourtSiteDao> siteList = new ArrayList<>();
        siteList.add(DummyCourtUtil.getXhbCourtSiteDao());
        siteList.add(DummyCourtUtil.getXhbCourtSiteDao());
        return siteList;
    }

    private List<XhbDisplayLocationDao> getDummyDisplayLocationList() {
        List<XhbDisplayLocationDao> xdlList = new ArrayList<>();
        xdlList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        xdlList.add(DummyPublicDisplayUtil.getXhbDisplayLocationDao());
        return xdlList;
    }

    private List<XhbDisplayDao> getDummyDisplayList() {
        List<XhbDisplayDao> xdList = new ArrayList<>();
        xdList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        xdList.add(DummyPublicDisplayUtil.getXhbDisplayDao());
        return xdList;
    }
}
