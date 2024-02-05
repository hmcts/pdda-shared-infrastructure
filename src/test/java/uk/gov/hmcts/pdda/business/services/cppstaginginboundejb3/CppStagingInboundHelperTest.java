package uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyPdNotifierUtil;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;

/**
 * CppStagingInboundHelperTest.
 */

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CppStagingInboundHelperTest {

    private static final String NULL = "Result is Null";
    
    @Mock
    private static EntityManager mockEntityManager;

    @Mock
    private XhbConfigPropRepository mockXhbConfigPropRepository;
    
    @Mock
    private Query mockQuery;
    
    @InjectMocks
    private final CppStagingInboundHelper classUnderTest =
        new CppStagingInboundHelper(mockEntityManager);

    @Test
    void testFindNextDocumentByStatus() {
        // Setup
        List<XhbCppStagingInboundDao> xhbCppStagingInboundDaos = new ArrayList<>();
        xhbCppStagingInboundDaos.add(DummyPdNotifierUtil.getXhbCppStagingInboundDao());
        
        Mockito.when(mockEntityManager.createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(xhbCppStagingInboundDaos);

        // Run
        List<XhbCppStagingInboundDao> result =
            classUnderTest.findNextDocumentByStatus("TEST", "TEST");

        // Checks
        assertNotNull(result, NULL);
    }
}
