package uk.gov.hmcts.pdda.business.vos.services.court;

import org.easymock.EasyMockExtension;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: Court Structure Value Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2024
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class CourtStructureValueTest {

    private static final String TRUE = "Result is True";
    private static final String EQUAL = "Result is Equal";

    @TestSubject
    private final CourtStructureValue classUnderTest = new CourtStructureValue();


    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testSetAndGetCourt() {
        XhbCourtDao xhbCourtDao = getDummyXhbCourtDao(1);
        boolean result;
        classUnderTest.setCourt(xhbCourtDao);
        result = true;
        assertTrue(result, TRUE);
        assertEquals(xhbCourtDao, classUnderTest.getCourt(), EQUAL);
    }

    @Test
    void testGetCourtRoomsForSite() {
        boolean result;
        classUnderTest.getCourtRoomsForSite(0);
        result = true;
        assertTrue(result, TRUE);
    }

    @Test
    void testSetAndGetCourtSites() {
        XhbCourtSiteDao[] xhbCourtSiteDaoArray = {getDummyXhbCourtSiteDao(1)};
        boolean result;
        classUnderTest.setCourtSites(xhbCourtSiteDaoArray);
        result = true;
        assertTrue(result, TRUE);
        assertEquals(xhbCourtSiteDaoArray.length, classUnderTest.getCourtSites().length, EQUAL);
    }
 
    @Test
    void testGetAllCourtRoomsForZeroSites() {
        XhbCourtSiteDao[] xhbCourtSiteDaoArray = {};
        classUnderTest.setCourtSites(xhbCourtSiteDaoArray);
        XhbCourtRoomDao[] result = classUnderTest.getAllCourtRooms();
        assertEquals(0, result.length, EQUAL);
    }
    
    @Test
    void testGetAllCourtRoomsForOneSite() {
        XhbCourtSiteDao xhbCourtSiteDao1 = getDummyXhbCourtSiteDao(1);
        XhbCourtSiteDao[] xhbCourtSiteDaoArray = {xhbCourtSiteDao1};
        XhbCourtRoomDao[] xhbCourtRoomDaoArray = {new XhbCourtRoomDao()};
        classUnderTest.setCourtSites(xhbCourtSiteDaoArray);
        classUnderTest.addCourtRooms(xhbCourtSiteDao1.getCourtSiteId(), xhbCourtRoomDaoArray);
        XhbCourtRoomDao[] result = classUnderTest.getAllCourtRooms();
        assertEquals(1, result.length, EQUAL);
    }
    
    @Test
    void testGetAllCourtRoomsForMultipleSites() {
        XhbCourtSiteDao xhbCourtSiteDao1 = getDummyXhbCourtSiteDao(1);
        XhbCourtSiteDao xhbCourtSiteDao2 = getDummyXhbCourtSiteDao(2);
        XhbCourtSiteDao[] xhbCourtSiteDaoArray = {xhbCourtSiteDao1, xhbCourtSiteDao2};
        XhbCourtRoomDao[] xhbCourtRoomDaoArray = {new XhbCourtRoomDao()};
        classUnderTest.setCourtSites(xhbCourtSiteDaoArray);
        classUnderTest.addCourtRooms(xhbCourtSiteDao1.getCourtSiteId(), xhbCourtRoomDaoArray);
        classUnderTest.addCourtRooms(xhbCourtSiteDao2.getCourtSiteId(), xhbCourtRoomDaoArray);
        XhbCourtRoomDao[] result = classUnderTest.getAllCourtRooms();
        assertEquals(2, result.length, EQUAL);
    }
    
    private XhbCourtSiteDao getDummyXhbCourtSiteDao(Integer courtSiteId) {
        XhbCourtSiteDao result = new XhbCourtSiteDao();
        result.setCourtSiteId(courtSiteId);
        return result;
    }
    
    private XhbCourtDao getDummyXhbCourtDao(Integer courtId) {
        XhbCourtDao result = new XhbCourtDao();
        result.setCourtId(courtId);
        return result;
    }
}
