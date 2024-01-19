package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * Title: CompareXmlNodes Test.
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
class CompareXmlNodesTest {

    private static final int EQUAL_TO = 0;

    private static final String EQUAL = "Result is Equal";
    
    private static final String NODENAME = "NodeName";
    private static final String COURT1 = "Court 1";

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testCompareCourtHouseNodesReplacePrefix() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareCourtHouseNodes("at TestValue", "at TestValue"), EQUAL);
    }

    @Test
    void testCompareCourtHouseNodesNoReplace() {
        assertEquals(EQUAL_TO, CompareXmlNodes.compareCourtHouseNodes("TestValue", "TestValue"),
            EQUAL);
    }

    @Test
    void testCompareNodesIwpCourtNumeric() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, COURT1, COURT1, true, false), EQUAL);
    }

    @Test
    void testCompareNodesIwpCourtNonNumeric() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Court Test", "Court Test", true, false),
            EQUAL);
    }

    @Test
    void testCompareNodesIwpCrownCourt() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Crown Court 1", "Crown Court 1", true, false),
            EQUAL);
    }

    @Test
    void testCompareNodesIwpCrownCourtNonNumeric() {
        assertEquals(EQUAL_TO, CompareXmlNodes.compareNodes(NODENAME, "Crown Court Test",
            "Crown Court Test", true, false), EQUAL);
    }

    @Test
    void testCompareNodesIwpNoCourt() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Test", "Test", true, false), EQUAL);
    }

    @Test
    void testCompareNodesWarnedList() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes("cs:CourtHouseName", COURT1, COURT1, false, true),
            EQUAL);
    }

    @Test
    void testCompareNodesFalseOutcome() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes("cs:CourtHouseName", COURT1, COURT1, false, false),
            EQUAL);
    }

}
