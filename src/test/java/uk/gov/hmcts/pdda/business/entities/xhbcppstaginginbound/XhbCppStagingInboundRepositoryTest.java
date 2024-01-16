package uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyPdNotifierUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbCppStagingInboundRepositoryTest extends AbstractRepositoryTest<XhbCppStagingInboundDao> {

    private static final Integer BYVALIDATIONANDPROCESSINGSTATUS = 1;
    private static final Integer BYSTATUS = 2;
    private static final Integer BYVALIDATIONSTATUS = 3;
    private static final Integer BYNEXTDOCUMENT = 4;
    private static final Integer BYNEXTDOCUMENTTEST = 5;
    private static final Integer UNRESPONDED = 6;

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCppStagingInboundRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCppStagingInboundRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCppStagingInboundRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindNextDocumentByValidationAndProcessingStatus() {
        boolean result = testFind(getDummyDao(), BYVALIDATIONANDPROCESSINGSTATUS);
        assertTrue(result, TRUE);
        result = testFind(null, BYVALIDATIONANDPROCESSINGSTATUS);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNextDocumentByProcessingStatus() {
        boolean result = testFind(getDummyDao(), BYSTATUS);
        assertTrue(result, TRUE);
        result = testFind(null, BYSTATUS);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNextDocument() {
        boolean result = testFind(getDummyDao(), BYNEXTDOCUMENT);
        assertTrue(result, TRUE);
        result = testFind(null, BYNEXTDOCUMENT);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNextDocumentByValidationStatus() {
        boolean result = testFind(getDummyDao(), BYVALIDATIONSTATUS);
        assertTrue(result, TRUE);
        result = testFind(null, BYVALIDATIONSTATUS);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNextDocumentTestSuccess() {
        boolean result = testFind(getDummyDao(), BYNEXTDOCUMENTTEST);
        assertTrue(result, TRUE);
        result = testFind(null, BYNEXTDOCUMENTTEST);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindUnrespondedCppMessages() {
        boolean result = testFind(getDummyDao(), UNRESPONDED);
        assertTrue(result, TRUE);
        result = testFind(null, UNRESPONDED);
        assertTrue(result, TRUE);
    }

    private boolean testFind(XhbCppStagingInboundDao dao, Integer whichTest) {
        List<XhbCppStagingInboundDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        List<XhbCppStagingInboundDao> result = null;
        if (BYVALIDATIONANDPROCESSINGSTATUS.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest()
                .findNextDocumentByValidationAndProcessingStatus(getDummyDao().getTimeLoaded(),
                    getDummyDao().getValidationStatus(), getDummyDao().getProcessingStatus());
        } else if (BYSTATUS.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest()
                .findNextDocumentByProcessingStatus(getDummyDao().getTimeLoaded(), getDummyDao().getProcessingStatus());
        } else if (BYVALIDATIONSTATUS.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest()
                .findNextDocumentByValidationStatus(getDummyDao().getTimeLoaded(), getDummyDao().getValidationStatus());
        } else if (BYNEXTDOCUMENT.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest()
                .findNextDocument(getDummyDao().getValidationStatus(), getDummyDao().getProcessingStatus());
        } else if (BYNEXTDOCUMENTTEST.equals(whichTest)) {
            Mockito.when(getEntityManager().createQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest()
                .findNextDocumentTest(getDummyDao().getTimeLoaded(), getDummyDao().getValidationStatus());
        } else if (UNRESPONDED.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCppStagingInboundDao>) getClassUnderTest().findUnrespondedCppMessages();
        }
        assertNotNull(result, NOTNULL);
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Override
    protected XhbCppStagingInboundDao getDummyDao() {
        return DummyPdNotifierUtil.getXhbCppStagingInboundDao();

    }

}
