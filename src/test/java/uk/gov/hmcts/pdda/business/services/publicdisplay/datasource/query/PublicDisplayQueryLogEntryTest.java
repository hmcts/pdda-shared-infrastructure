package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.query;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbcase.XhbCaseRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourtsite.XhbCourtSiteRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: PublicDisplayQueryLogEntry Test.
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
class PublicDisplayQueryLogEntryTest {

    private static final String NOT_TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final PublicDisplayQueryLogEntry classUnderTest =
        new PublicDisplayQueryLogEntry(mockEntityManager);

    @Test
    void testGetXhbCaseRepository() {
        assertTrue(classUnderTest.getXhbCaseRepository() instanceof XhbCaseRepository, NOT_TRUE);
    }

    @Test
    void testGetXhbCourtSiteRepository() {
        assertTrue(classUnderTest.getXhbCourtSiteRepository() instanceof XhbCourtSiteRepository,
            NOT_TRUE);
    }

    @Test
    void testGetXhbCourtRoomRepository() {
        assertTrue(classUnderTest.getXhbCourtRoomRepository() instanceof XhbCourtRoomRepository,
            NOT_TRUE);
    }

}
