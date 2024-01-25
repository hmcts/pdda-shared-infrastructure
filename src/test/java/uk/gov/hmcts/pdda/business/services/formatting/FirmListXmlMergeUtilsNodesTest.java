package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
    void testMergeNode() throws SAXException, IOException, ParserConfigurationException {
        String documentXml = "<cs:CourtList><cs:FirmList><cs:HearingDate>01/01/2024</cs:HearingDate></cs:FirmList></cs:CourtList>";
        String documentXmlToMerge = "<cs:FirmList><cs:ReserveList><cs:HearingDate>01/01/2024</cs:HearingDate></cs:ReserveList></cs:FirmList>";
        boolean result = true;
        
        Document baseDocument = getDummyDoc(documentXml);
        Node node = baseDocument.getDocumentElement();
        Node nodeToMerge = getDummyDoc(documentXmlToMerge).getDocumentElement();
        
        classUnderTest.mergeNode(baseDocument,  node, nodeToMerge);
        assertTrue(result, TRUE);
    }
    
    @Test
    void testMergeNodeSecondNodeReserved() throws SAXException, IOException, ParserConfigurationException {
        String documentXml = "<cs:FirmList></cs:FirmList>";
        String documentXmlToMerge = "<cs:ReserveList></cs:ReserveList>";
        boolean result = true;
        
        Document baseDocument = getDummyDoc(documentXml);
        Node node = baseDocument.getDocumentElement();
        Node nodeToMerge = getDummyDoc(documentXmlToMerge).getDocumentElement();
        
        classUnderTest.mergeNode(baseDocument,  node, nodeToMerge);
        assertTrue(result, TRUE);
    }
    
    @Test
    void testMergeNodeFirstNodeReserved() throws SAXException, IOException, ParserConfigurationException {
        String documentXml = "<cs:ReserveList></cs:ReserveList>";
        String documentXmlToMerge = "<cs:ReserveList></cs:ReserveList>";
        boolean result = true;
        
        Document baseDocument = getDummyDoc(documentXml);
        Node node = baseDocument.getDocumentElement();
        Node nodeToMerge = getDummyDoc(documentXmlToMerge).getDocumentElement();
        
        classUnderTest.mergeNode(baseDocument,  node, nodeToMerge);
        assertTrue(result, TRUE);
    }
    
    private Document getDummyDoc(String xml) throws SAXException, IOException, ParserConfigurationException {
        return DocumentUtils.createInputDocument(xml);
    }
}