package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: WarnedListXMLMergeUtils Test.
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
class WarnedListXmlMergeUtilsTest {

    private static final String EQUALS = "Results are not Equal";
    private static final String TRUE = "Result is not True";

    @TestSubject
    private static WarnedListXmlMergeUtils classUnderTest;

    @BeforeAll
    public static void setUp() throws Exception {
        classUnderTest = new WarnedListXmlMergeUtils();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testGetRootNodes() {
        // Setup
        final String[] expectedArray =
            {"WarnedList/CourtLists/CourtList/WithFixedDate", "WarnedList/CourtLists/CourtList/WithoutFixedDate"};

        // Run
        final String[] stringArr = classUnderTest.getRootNodes();

        // Checks
        assertArrayEquals(expectedArray, stringArr, EQUALS);
    }

    @Test
    void testGetNodePositionArray() {
        // Setup
        final String[] expectedArray = {"cs:FixedDate", "cs:CaseNumber"};

        // Run
        final String[] stringArr = classUnderTest.getNodePositionArray();

        // Checks
        assertArrayEquals(expectedArray, stringArr, EQUALS);
    }

    @Test
    void testGetNodeMatchArray() {
        // Setup
        final String[] expectedArray = {"cs:CourtHouseCode", "cs:CourtHouseName"};

        // Run
        final String[] stringArr = classUnderTest.getNodeMatchArray();

        // Checks
        assertArrayEquals(expectedArray, stringArr, EQUALS);
    }

    @Test
    void testGetMergeType() {
        // Setup
        final String expectedString = "LISTS";

        // Run
        final String actualString = classUnderTest.getMergeType();

        // Checks
        assertEquals(expectedString, actualString, EQUALS);
    }

    @Test
    void testWarnedTagSuccess() {
        assertTrue(WarnedTag.CASE_NUMBER == WarnedTag.fromString("cs:CaseNumber"), TRUE);
    }

    @Test
    void testWarnedTagFailure() {
        assertFalse(WarnedTag.CASE_NUMBER == WarnedTag.fromString("XXX"), TRUE);
    }
}
