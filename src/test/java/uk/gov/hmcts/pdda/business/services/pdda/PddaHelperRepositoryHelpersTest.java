package uk.gov.hmcts.pdda.business.services.pdda;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3.CppStagingInboundHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: PddaHelper Test for the Repository Helpers.
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
class PddaHelperRepositoryHelpersTest {

    private static final String NOT_TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final PddaHelper classUnderTest = new PddaHelper(mockEntityManager);

    @Test
    void testGetClobRepository() {
        assertTrue(classUnderTest.getClobRepository() instanceof XhbClobRepository, NOT_TRUE);
    }

    @Test
    void testGetCourtRepository() {
        assertTrue(classUnderTest.getCourtRepository() instanceof XhbCourtRepository, NOT_TRUE);
    }

    @Test
    void testGetPddaMessageHelper() {
        assertTrue(classUnderTest.getPddaMessageHelper() instanceof PddaMessageHelper, NOT_TRUE);
    }

    @Test
    void testGetCppStagingInboundHelper() {
        assertTrue(classUnderTest.getCppStagingInboundHelper() instanceof CppStagingInboundHelper,
            NOT_TRUE);
    }

    @Test
    void testGetSftpHelper() {
        assertTrue(classUnderTest.getSftpHelper() instanceof PddaSftpHelper, NOT_TRUE);
    }
}
