package uk.gov.hmcts.pdda.business.services.formatting;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.framework.services.XslServices;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformatting.XhbCppFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcppformattingmerge.XhbCppFormattingMergeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListRepository;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingRepository;
import uk.gov.hmcts.pdda.business.entities.xhbxmldocument.XhbXmlDocumentRepository;
import uk.gov.hmcts.pdda.business.xmlbinding.formatting.FormattingConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: AbstractFormattingRepositories Test.
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
class AbstractFormattingRepositoriesTest {

    private static final String NOT_TRUE = "Result is Not True";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final AbstractFormattingRepositories classUnderTest =
        new AbstractFormattingRepositories(mockEntityManager);

    @Test
    void testGetXhbClobRepository() {
        assertTrue(classUnderTest.getXhbClobRepository() instanceof XhbClobRepository, NOT_TRUE);
    }

    @Test
    void testGetXhbCppListRepository() {
        assertTrue(classUnderTest.getXhbCppListRepository() instanceof XhbCppListRepository,
            NOT_TRUE);
    }

    @Test
    void testgetXhbCppFormattingRepository() {
        assertTrue(
            classUnderTest.getXhbCppFormattingRepository() instanceof XhbCppFormattingRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbFormattingRepository() {
        assertTrue(classUnderTest.getXhbFormattingRepository() instanceof XhbFormattingRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbXmlDocumentRepository() {
        assertTrue(classUnderTest.getXhbXmlDocumentRepository() instanceof XhbXmlDocumentRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbConfigPropRepository() {
        assertTrue(classUnderTest.getXhbConfigPropRepository() instanceof XhbConfigPropRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbCppFormattingMergeRepository() {
        assertTrue(
            classUnderTest
                .getXhbCppFormattingMergeRepository() instanceof XhbCppFormattingMergeRepository,
            NOT_TRUE);
    }

    @Test
    void testGetFormattingConfig() {
        assertTrue(classUnderTest.getFormattingConfig() instanceof FormattingConfig, NOT_TRUE);
    }

    @Test
    void testGetXslServices() {
        assertTrue(classUnderTest.getXslServices() instanceof XslServices, NOT_TRUE);
    }
}
