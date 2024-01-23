package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.query;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import uk.gov.hmcts.framework.services.CsServices;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>
 * Title: EventXmlNodeHelper Test.
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
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class EventXmlNodeHelperTest {

    private static final String NOT_NULL = "Result is Not Null";
    
    private static final String TEST_XML = "<testNode><testChildNode>courtroomname</testChildNode></testNode>";

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testBuildEventNode() {
        assertNotNull(EventXmlNodeHelper.buildEventNode(TEST_XML), NOT_NULL);
    }
    
    @Test
    void testBuildEventNodeWithNodeParam() {
        assertNotNull(EventXmlNodeHelper.buildEventNode(getDummyNode()), NOT_NULL);
    }
    
    private Node getDummyNode() {
        Document doc = CsServices.getXmlServices().createDocFromString(TEST_XML);
        return doc.getDocumentElement();
    }
}
