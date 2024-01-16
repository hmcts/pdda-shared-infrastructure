package uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyServicesUtil;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.services.validation.ValidationResult;
import uk.gov.hmcts.pdda.business.services.validation.ValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CppStagingInboundControllerBeanIsValidTest.
 */
@ExtendWith(EasyMockExtension.class)
class CppStagingInboundControllerBeanIsValidTest {

    private static final String FALSE = "Result is not False";
    private static final String TRUE = "Result is not True";
    private static final String NOTNULL = "Result is Null";
    private static final String NULL = "Result is not Null";
    private static final String EMPTY_STRING = "";
    private static final String TESTING = "Testing";

    @Mock
    private static EntityManager mockEntityManager;

    @Mock
    private CppStagingInboundHelper mockCppStagingInboundHelper;

    @Mock
    private XhbConfigPropRepository mockXhbConfigPropRepository;

    @Mock
    private XhbConfigPropDao mockXhbConfigPropDao;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbClobRepository mockXhbClobRepository;

    @Mock
    private ValidationResult mockValidationResult;

    @Mock
    private ValidationService mockValidationService;

    @TestSubject
    private final CppStagingInboundControllerBean classUnderTest =
        new CppStagingInboundControllerBean(mockEntityManager, mockXhbConfigPropRepository, mockCppStagingInboundHelper,
            mockXhbCourtRepository, mockXhbClobRepository, mockValidationService);

    @BeforeEach
    public void setUp() throws Exception {
        // Do nothing
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testIsValidDocumentNameInvalidNames() {
        // Setup
        boolean resultValue;
        String[] invalidDocumentNames =
            {"PublicDisplay_457_20230515154330.txt", "PublicDisplay_457_20230515154330_TEST.xml",
                "InvalidName_457_20230515154330.xml", "PublicDisplay_399_20230515154330.xml",
                "PublicDisplay_500_20230515154330.xml", "PublicDisplay_£££_20230515154330.xml"};
        for (String invalidDocumentName : invalidDocumentNames) {
            // Run
            resultValue = DocumentValidationUtils.isValidDocumentName(invalidDocumentName);
            // Checks
            assertFalse(resultValue, FALSE);
        }
    }

    @Test
    void testIsValidDocumentNameValidName() {
        // Setup
        String documentName = "PublicDisplay_457_20230515154330.xml";
        // Run
        boolean resultValue = DocumentValidationUtils.isValidDocumentName(documentName);
        // Checks
        assertTrue(resultValue, TRUE);
    }

    @Test
    void testIsValidDocumentTimeInvalidTimes() {
        // Setup
        boolean resultValue;
        String documentName = EMPTY_STRING;
        String[] invalidDocTimes = {"20230515154330000000", "30000515154330", "20231615154330", "20230554154330",
            "20230515454330", "20230515156530", "20230515154367", "££££££££££££££"};
        // Run
        for (String invalidDocTime : invalidDocTimes) {
            resultValue = DocumentValidationUtils.isValidDocumentTime(invalidDocTime, documentName);
            // Checks
            assertFalse(resultValue, FALSE);
        }
    }

    @Test
    void testIsValidDocumentTimeValidTime() {
        // Setup
        boolean resultValue;
        String validDocTime = "20230515154330";
        String documentName = EMPTY_STRING;
        // Run
        resultValue = DocumentValidationUtils.isValidDocumentTime(validDocTime, documentName);
        // Checks
        assertTrue(resultValue, TRUE);
    }

    @Test
    void testIsValidDocumentType() {
        // Run
        boolean methodResult = DocumentValidationUtils.isValidDocumentType("PD");
        // Checks
        assertTrue(methodResult, TRUE);
    }
    
    @Test
    void testFindConfigEntryByPropertyName() {
        // Setup
        List<XhbConfigPropDao> properties = new ArrayList<>();
        properties.add(DummyServicesUtil.getXhbConfigPropDao(TESTING, EMPTY_STRING));
        EasyMock.expect(mockXhbConfigPropRepository.findByPropertyName("scheduledtasks.pdda")).andReturn(properties);
        EasyMock.replay(mockXhbConfigPropRepository);
        // Run
        String returnString = classUnderTest.findConfigEntryByPropertyName(TESTING);
        // Checks
        EasyMock.verify(mockXhbConfigPropRepository);
        assertNotNull(returnString, NOTNULL);
    }

    @Test
    void testFindConfigEntryByPropertyNameNullArray() {
        // Setup
        List<XhbConfigPropDao> properties = new ArrayList<>();
        EasyMock.expect(mockXhbConfigPropRepository.findByPropertyName("scheduledtasks.pdda")).andReturn(properties);
        EasyMock.replay(mockXhbConfigPropRepository);
        // Run
        String returnString = classUnderTest.findConfigEntryByPropertyName(TESTING);
        // Checks
        EasyMock.verify(mockXhbConfigPropRepository);
        assertNull(returnString, NULL);
    }
    
    @Test
    void testGetClobXmlAsString() {
        // Setup
        Long clobId = (long) 421_000;
        XhbClobDao clobObj = new XhbClobDao();
        clobObj.setClobData("Demo Data");
        EasyMock.expect(mockXhbClobRepository.findById(clobId)).andReturn(Optional.of(clobObj));
        EasyMock.replay(mockXhbClobRepository);
        // Run
        String clobReturnData = classUnderTest.getClobXmlAsString(clobId);
        // Checks
        EasyMock.verify(mockXhbClobRepository);
        assertNotNull(clobReturnData, NOTNULL);
    }

    @Test
    void testGetClobXmlAsStringFail() {
        // Setup
        Long clobId = (long) 421_000;
        EasyMock.expect(mockXhbClobRepository.findById(clobId)).andReturn(Optional.empty());
        EasyMock.replay(mockXhbClobRepository);
        // Run
        String clobReturnData = classUnderTest.getClobXmlAsString(clobId);
        // Checks
        EasyMock.verify(mockXhbClobRepository);
        assertNull(clobReturnData, NULL);
    }
}
