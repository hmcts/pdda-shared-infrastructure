package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled.DocumentUtils;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: WarnedListUtils Test.
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
class WarnedListUtilsTest {

    private static final String NOT_TRUE = "Result is Not True";

    private static final String BASE_NODES =
        "<cs:CourtList><cs:WithFixedDate><cs:FixedDate>2</cs:FixedDate></cs:WithFixedDate></cs:CourtList>";

    private static final String CHILD_NODES =
        "<cs:CourtList><cs:FixedDate>1</cs:FixedDate></cs:CourtList>";

    @Test
    void testIsWarnedListChildNodeValid()
        throws SAXException, IOException, ParserConfigurationException {
        // Setup
        Node node = getDummyDoc(BASE_NODES).getFirstChild().getChildNodes().item(0);
        Node nodeToMerge = getDummyDoc(CHILD_NODES).getFirstChild().getChildNodes().item(0);
        // Run & Check
        assertTrue(WarnedListUtils.isWarnedListChildNodeValid(node, nodeToMerge, true, false, "IWP",
            false, true), NOT_TRUE);
    }

    private Document getDummyDoc(String xml)
        throws SAXException, IOException, ParserConfigurationException {
        return DocumentUtils.createInputDocument(xml);
    }
}
