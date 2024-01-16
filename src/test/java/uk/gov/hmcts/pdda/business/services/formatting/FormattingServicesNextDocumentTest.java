package uk.gov.hmcts.pdda.business.services.formatting;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.xml.sax.ContentHandler;
import uk.gov.hmcts.DummyFormattingUtil;
import uk.gov.hmcts.DummyServicesUtil;
import uk.gov.hmcts.framework.services.XmlServices;
import uk.gov.hmcts.framework.services.XslServices;
import uk.gov.hmcts.pdda.business.entities.xhbblob.XhbBlobDao;
import uk.gov.hmcts.pdda.business.entities.xhbblob.XhbBlobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformattingmerge.XhbCppFormattingMergeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListRepository;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingDao;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbxmldocument.XhbXmlDocumentDao;
import uk.gov.hmcts.pdda.business.entities.xhbxmldocument.XhbXmlDocumentRepository;
import uk.gov.hmcts.pdda.business.vos.translation.TranslationBundles;
import uk.gov.hmcts.pdda.business.xmlbinding.formatting.FormattingConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FormattingServicesNextDocumentTest.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.TooManyFields"})
class FormattingServicesNextDocumentTest {

    private static final String TRUE = "Result is not True";
    private static final String FORMATTING_LIST_DELAY = "FORMATTING_LIST_DELAY";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private TranslationBundles mockTranslationBundles;

    @Mock
    private FormattingConfig mockFormattingConfig;

    @Mock
    private XslServices mockXslServices;

    @Mock
    private XmlServices mockXmlServices;

    @Mock
    private TransformerFactory mockTransformerFactory;

    @Mock
    private Transformer mockTransformer;

    @Mock
    private ContentHandler mockContentHandler;

    @Mock
    private XhbBlobRepository mockXhbBlobRepository;

    @Mock
    private XhbClobRepository mockXhbClobRepository;

    @Mock
    private XhbCppListRepository mockXhbCppListRepository;

    @Mock
    private XhbCppFormattingRepository mockXhbCppFormattingRepository;

    @Mock
    private XhbFormattingRepository mockXhbFormattingRepository;

    @Mock
    private XhbConfigPropRepository mockXhbConfigPropRepository;

    @Mock
    private XhbCppFormattingMergeRepository mockXhbCppFormattingMergeRepository;

    @Mock
    private XhbXmlDocumentRepository mockXhbXmlDocumentRepository;

    @InjectMocks
    private final FormattingServices classUnderTest = new FormattingServices(mockEntityManager);

    @BeforeEach
    public void setUp() throws Exception {
        // Do nothing
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testNextDocumentId() {
        boolean result = testNextDocument("ND", "FD");
        assertTrue(result, TRUE);
    }

    @Test
    void testNextDocumentIdFormatError() {
        boolean result = testNextDocument("FE", "NF");
        assertTrue(result, TRUE);
    }

    private boolean testNextDocument(String formatStatus, String expectedFormatStatus) {
        // Setup
        XhbClobDao xhbClobDao = DummyFormattingUtil.getXhbClobDao(Long.valueOf(1), FormattingServicesTest.CPP_LIST);
        List<XhbFormattingDao> formattingDaoList = new ArrayList<>();
        XhbFormattingDao formattingDao = DummyFormattingUtil.getXhbFormattingDao();
        formattingDao.setFormatStatus(formatStatus);
        formattingDao.setXmlDocumentClobId(xhbClobDao.getClobId());
        formattingDaoList.add(formattingDao);
        List<XhbXmlDocumentDao> xmlDocumentList = new ArrayList<>();
        XhbXmlDocumentDao xhbXmlDocumentDao = DummyFormattingUtil.getXhbXmlDocumentDao();
        xhbXmlDocumentDao.setXmlDocumentClobId(xhbClobDao.getClobId());
        xmlDocumentList.add(xhbXmlDocumentDao);
        if ("ND".contentEquals(formatStatus)) {
            expectConfigProp(FORMATTING_LIST_DELAY, "3");
            Mockito.when(mockXhbXmlDocumentRepository.findListByClobId(Mockito.isA(Long.class),
                Mockito.isA(LocalDateTime.class))).thenReturn(xmlDocumentList);
        } else if ("FE".contentEquals(formatStatus)) {
            Mockito.when(mockXhbFormattingRepository.findByFormatStatus("ND"))
                .thenReturn(new ArrayList<XhbFormattingDao>());
        }
        Mockito.when(mockXhbFormattingRepository.findByFormatStatus(formatStatus)).thenReturn(formattingDaoList);
        Mockito.when(mockXhbBlobRepository.update(Mockito.isA(XhbBlobDao.class)))
            .thenReturn(Optional.of(DummyFormattingUtil.getXhbBlobDao(new byte[0])));
        Mockito.when(mockXhbFormattingRepository.update(Mockito.isA(XhbFormattingDao.class)))
            .thenReturn(Optional.of(formattingDao));
        // Run
        XhbFormattingDao result = classUnderTest.getNextFormattingDocument();
        assertNotNull(result, "Result is Null");
        assertSame(expectedFormatStatus, result.getFormatStatus(), "Incorrect status");
        return true;
    }

    private void expectConfigProp(String propertyName, String propertyValue) {
        List<XhbConfigPropDao> daoList = new ArrayList<>();
        daoList.add(DummyServicesUtil.getXhbConfigPropDao(propertyName, propertyValue));
        Mockito.when(mockXhbConfigPropRepository.findByPropertyName(propertyName)).thenReturn(daoList);
    }
}
