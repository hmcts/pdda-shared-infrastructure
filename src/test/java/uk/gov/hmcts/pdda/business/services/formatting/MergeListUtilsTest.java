package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private static final String EQUAL = "Result is Not Equal";
    private static final String FALSE = "Result is Not False";

    private static final String[] DAILY_LIST_ROOT_NODES = {"DailyList/CourtLists"};
    private static final String[] FIRM_LIST_ROOT_NODES = {"FirmList/CourtLists", "FirmList/ReserveList"};
    private static final String[] WARNED_LIST_ROOT_NODES = {"WarnedList/CourtLists/CourtList"};
    
    private static final String INVALID = "Invalid";

    @Test
    void testGetListRootNodesDailyList() throws XPathExpressionException, IOException {
        DailyListXmlMergeUtils dailyListXmlMergeUtils = new DailyListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(dailyListXmlMergeUtils.getClass());
        assertEquals(DAILY_LIST_ROOT_NODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesFirmList() throws XPathExpressionException, IOException {
        FirmListXmlMergeUtils firmListXmlMergeUtils = new FirmListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(firmListXmlMergeUtils.getClass());
        assertEquals(FIRM_LIST_ROOT_NODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesWarnedList() throws XPathExpressionException, IOException {
        WarnedListXmlMergeUtils warnedListXmlMergeUtils = new WarnedListXmlMergeUtils();
        String[] result = MergeListUtils.getListRootNodes(warnedListXmlMergeUtils.getClass());
        assertEquals(WARNED_LIST_ROOT_NODES[0], result[0], EQUAL);
    }
    
    @Test
    void testGetListRootNodesInvalid() {
        Assertions.assertThrows(IOException.class, () -> {
            String invalidEntry = "";
            MergeListUtils.getListRootNodes(invalidEntry.getClass());
        });
    }
    
    @Test
    void testIsFirmListRootNodesFalseResult() {
        String[] invalidNodes = {INVALID, INVALID};
        assertFalse(MergeListUtils.isFirmListRootNodes(invalidNodes), FALSE);
    }
    
    @Test
    void testIsWarnedListRootNodesFalseResult() {
        String[] invalidNodes = {INVALID, INVALID};
        assertFalse(MergeListUtils.isWarnedListRootNodes(invalidNodes), FALSE);
    }
    
    @Test
    void testGetlistRootNodeExpression() throws XPathExpressionException {
        String[] testValues = {INVALID, INVALID};
        assertTrue(MergeListUtils.getlistRootNodeExpression(testValues) instanceof XPathExpression[], EQUAL);
        assertEquals(2, MergeListUtils.getlistRootNodeExpression(testValues).length, EQUAL);
    }
}
