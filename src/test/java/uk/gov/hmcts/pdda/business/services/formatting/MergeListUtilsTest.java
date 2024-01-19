package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: MergeListUtils Test.
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
class MergeListUtilsTest {

    private static final String EQUAL = "Result is Equal";
    private static final String FALSE = "Result is False";

    private static final String[] DAILYLISTROOTNODES = {"DailyList/CourtLists"};
    private static final String[] FIRMLISTROOTNODES = {"FirmList/CourtLists", "FirmList/ReserveList"};
    private static final String[] WARNEDLISTROOTNODES = {"WarnedList/CourtLists/CourtList"};
    
    private static final String INVALID = "Invalid";

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testGetListRootNodesDailyList() throws XPathExpressionException, IOException {
        DailyListXmlMergeUtils dailyListXmlMergeUtils = new DailyListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(dailyListXmlMergeUtils.getClass());
        assertEquals(DAILYLISTROOTNODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesFirmList() throws XPathExpressionException, IOException {
        FirmListXmlMergeUtils firmListXmlMergeUtils = new FirmListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(firmListXmlMergeUtils.getClass());
        assertEquals(FIRMLISTROOTNODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesWarnedList() throws XPathExpressionException, IOException {
        WarnedListXmlMergeUtils warnedListXmlMergeUtils = new WarnedListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(warnedListXmlMergeUtils.getClass());
        assertEquals(WARNEDLISTROOTNODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesInvalid() throws XPathExpressionException, IOException {
        Assertions.assertThrows(IOException.class, () -> {
            String invalidEntry = "";
            MergeListUtils.getListRootNodes(invalidEntry.getClass());
        });
    }
    
    @Test
    void testIsFirmListRootNodesFalseResult() throws XPathExpressionException, IOException {
        String[] invalidNodes = {INVALID, INVALID};
        assertTrue(!MergeListUtils.isFirmListRootNodes(invalidNodes), FALSE);
    }
    
    @Test
    void testIsWarnedListRootNodesFalseResult() throws XPathExpressionException, IOException {
        String[] invalidNodes = {INVALID, INVALID};
        assertTrue(!MergeListUtils.isWarnedListRootNodes(invalidNodes), FALSE);
    }
    
    @Test
    void testGetlistRootNodeExpression() throws XPathExpressionException {
        String[] testValues = {INVALID, INVALID};
        assertTrue(MergeListUtils.getlistRootNodeExpression(testValues) instanceof XPathExpression[], EQUAL);
    }
}
