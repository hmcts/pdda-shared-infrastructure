package uk.gov.hmcts.pdda.business;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListRepository;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: AbstractControllerBean Test.
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
class AbstractControllerBeanTest {

    private static final String NOT_TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final AbstractControllerBean classUnderTest =
        new AbstractControllerBean(mockEntityManager);

    @Test
    void testGetXhbClobRepository() {
        assertTrue(classUnderTest.getXhbClobRepository() instanceof XhbClobRepository, NOT_TRUE);
    }

    @Test
    void testGetXhbConfigPropRepository() {
        assertTrue(classUnderTest.getXhbConfigPropRepository() instanceof XhbConfigPropRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbCppListRepository() {
        assertTrue(classUnderTest.getXhbCppListRepository() instanceof XhbCppListRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbFormattingRepository() {
        assertTrue(classUnderTest.getXhbFormattingRepository() instanceof XhbFormattingRepository,
            NOT_TRUE);
    }
}
