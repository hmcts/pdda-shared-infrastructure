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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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

    private static final String NOT_INSTANCE = "Result is Not An Instance of";

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final PublicDisplayQueryLogEntry classUnderTest =
        new PublicDisplayQueryLogEntry(mockEntityManager);

    @Test
    void testGetXhbCaseRepository() {
        assertInstanceOf(XhbCaseRepository.class, classUnderTest.getXhbCaseRepository(),
            NOT_INSTANCE);
    }

    @Test
    void testGetXhbCourtSiteRepository() {
        assertInstanceOf(XhbCourtSiteRepository.class, classUnderTest.getXhbCourtSiteRepository(),
            NOT_INSTANCE);
    }

    @Test
    void testGetXhbCourtRoomRepository() {
        assertInstanceOf(XhbCourtRoomRepository.class, classUnderTest.getXhbCourtRoomRepository(),
            NOT_INSTANCE);
    }

}
