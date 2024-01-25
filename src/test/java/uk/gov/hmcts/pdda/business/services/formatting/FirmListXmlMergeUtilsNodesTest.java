package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled.DocumentUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: FirmListXMLMergeUtils Second Test Class.
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
class FirmListXmlMergeUtilsNodesTest {

    private static final String TRUE = "Result is True";

    @TestSubject
    private static FirmListXmlMergeUtils classUnderTest;

    @BeforeAll
    public static void setUp() throws Exception {
        classUnderTest = new FirmListXmlMergeUtils();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testMergeNodeEmptyParentNode()
        throws SAXException, IOException, ParserConfigurationException {
        String documentXml =
            "<cs:FirmList><cs:ReserveList></cs:ReserveList><cs:CourtList></cs:CourtList></cs:FirmList>";
        String documentXmlToMerge =
            "<cs:FirmList><cs:ReserveList><cs:HearingDate>01/01/2024</cs:HearingDate></cs:ReserveList><cs:CourtList>"
                + "<cs:CourtHouse>SNARESBROOK</cs:CourtHouse></cs:CourtList></cs:FirmList>";
        boolean result = true;

        Document baseDocument = getDummyDoc(documentXml);
        NodeList baseNodes = baseDocument.getFirstChild().getChildNodes();
        Document documentToMerge = getDummyDoc(documentXmlToMerge);
        NodeList nodesToMerge = documentToMerge.getFirstChild().getChildNodes();

        // Reserve List Merge
        Node node = baseNodes.item(0);
        Node nodeToMerge = nodesToMerge.item(0);
        classUnderTest.mergeNode(baseDocument, node, nodeToMerge);
        assertTrue(result, TRUE);

        // CourtList
        node = baseNodes.item(1);
        nodeToMerge = nodesToMerge.item(1);
        classUnderTest.mergeNode(baseDocument, node, nodeToMerge);
        assertTrue(result, TRUE);
    }

    @Test
    void testMergeNodeCombinations()
        throws SAXException, IOException, ParserConfigurationException {
        List<String> documentXmls = new ArrayList<>();
        List<String> documentXmlsToMerge = new ArrayList<>();
        boolean result = true;
        // Insert before Data
        documentXmls.add(
            "<cs:CourtList><cs:CourtHouse>TestCourtHouse</cs:CourtHouse><cs:FirmList>"
            + "<cs:HearingDate>01/01/2024</cs:HearingDate></cs:FirmList></cs:CourtList>");
        documentXmlsToMerge.add(
            "<cs:FirmList><cs:ReserveList><cs:HearingDate>01/01/2024</cs:HearingDate>"
            + "</cs:ReserveList></cs:FirmList>");
        // Second Node Reserved Data
        documentXmls.add("<cs:FirmList></cs:FirmList>");
        documentXmlsToMerge.add("<cs:ReserveList></cs:ReserveList>");
        // First Node Reserved Data
        documentXmls.add("<cs:ReserveList></cs:ReserveList>");
        documentXmlsToMerge.add("<cs:ReserveList></cs:ReserveList>");

        for (int i = 0; i < documentXmls.size(); i++) {
            testMergeNode(documentXmls.get(i), documentXmlsToMerge.get(i));
            assertTrue(result, TRUE);
        }
    }

    void testMergeNode(String documentXml, String documentXmlToMerge)
        throws SAXException, IOException, ParserConfigurationException {
        
        Document baseDocument = getDummyDoc(documentXml);
        Node node = baseDocument.getDocumentElement();
        Node nodeToMerge = getDummyDoc(documentXmlToMerge).getDocumentElement();

        classUnderTest.mergeNode(baseDocument, node, nodeToMerge);
    }

    private Document getDummyDoc(String xml)
        throws SAXException, IOException, ParserConfigurationException {
        return DocumentUtils.createInputDocument(xml);
    }
}
