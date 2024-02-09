package uk.gov.hmcts.pdda.business.services.cpp;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.services.cpplist.CppListControllerBean;
import uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3.CppStagingInboundControllerBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AbstractCppInitialProcessingControllerBeanHelpersTest.
 **/
@ExtendWith(EasyMockExtension.class)
class AbstractCppInitialProcessingControllerBeanHelpersTest {

    private static final String NOT_TRUE = "Result is Not True";

    @Mock
    protected EntityManager mockEntityManager;

    @TestSubject
    protected final CppInitialProcessingControllerBean classUnderTest =
        new CppInitialProcessingControllerBean(mockEntityManager);

    @Test
    void testGetCppStagingInboundControllerBean() {
        assertTrue(
            classUnderTest
                .getCppStagingInboundControllerBean() instanceof CppStagingInboundControllerBean,
            NOT_TRUE);
    }

    @Test
    void testGetCppListControllerBean() {
        assertTrue(
            classUnderTest
                .getCppListControllerBean() instanceof CppListControllerBean,
            NOT_TRUE);
    }
}
