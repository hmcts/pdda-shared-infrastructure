package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import uk.gov.hmcts.framework.services.CsServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static final String TRUE = "Result is True";
    private static final String FALSE = "Result is False";
    
    private static final String NODENAME = "NodeName";
    private static final String COURT1 = "Court 1";
    
    private static final String TEST_NODE_BLANK_XML = "<courtroomname></courtroomname>";

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testCompareCourtHouseNodes() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareCourtHouseNodes("at TestValue", "at TestValue"), EQUAL);
        assertEquals(EQUAL_TO, CompareXmlNodes.compareCourtHouseNodes("TestValue", "TestValue"),
            EQUAL);
    }

    @Test
    void testCompareNodesIwp() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, COURT1, COURT1, true, false), EQUAL);
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Court Test", "Court Test", true, false),
            EQUAL);
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Crown Court 1", "Crown Court 1", true, false),
            EQUAL);
        assertEquals(EQUAL_TO, CompareXmlNodes.compareNodes(NODENAME, "Crown Court Test",
            "Crown Court Test", true, false), EQUAL);
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes(NODENAME, "Test", "Test", true, false), EQUAL);
    }

    @Test
    void testCompareNodes() {
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes("cs:CourtHouseName", COURT1, COURT1, false, true),
            EQUAL);
        assertEquals(EQUAL_TO,
            CompareXmlNodes.compareNodes("cs:CourtHouseName", COURT1, COURT1, false, false),
            EQUAL);
    }
    
    @Test
    void testCompareToEmpty() {
        assertTrue(CompareXmlNodes.compareToEmpty(NODENAME, ""), TRUE);
        assertFalse(CompareXmlNodes.compareToEmpty(NODENAME, NODENAME), FALSE);
    }
    
    @Test
    void testIsBlankTextNode() {
        assertFalse(CompareXmlNodes.isBlankTextNode(getDummyNode(TEST_NODE_BLANK_XML)), FALSE);
    }
    
    private Node getDummyNode(String xml) {
        Document doc = CsServices.getXmlServices().createDocFromString(xml);
        return doc.getDocumentElement();
    }
}
