package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.services.publicdisplay.data.ejb.PdDataControllerBean;
import uk.gov.hmcts.pdda.business.services.publicdisplay.data.impl.GenericPublicDisplayDataSource;
import uk.gov.hmcts.pdda.common.publicdisplay.types.document.DisplayDocumentType;
import uk.gov.hmcts.pdda.common.publicdisplay.types.document.DisplayDocumentTypeUtils;
import uk.gov.hmcts.pdda.common.publicdisplay.types.uri.DisplayDocumentUri;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>
 * Title: PDDataControllerBean Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2023
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 */
@ExtendWith(EasyMockExtension.class)
class PdDataControllerBeanTest {

    private static final String TRUE = "Result is not True";
    private final Locale dummyLocale = new Locale("en", "GB");

    @Mock
    private GenericPublicDisplayDataSource mockDataSource;

    @Mock
    private EntityManager mockEntityManager;

    @TestSubject
    private final PdDataControllerBean classUnderTest = new PdDataControllerBean(mockEntityManager, mockDataSource);

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testGetDataSuccess() {
        // Setup
        DisplayDocumentType displayDocumentType = getDummyDisplayDocumentType("DailyList");
        DisplayDocumentUri displayDocumentUri = getDummyDisplayDocumentUri(displayDocumentType);
        // Expects
        mockDataSource.retrieve(mockEntityManager);
        EasyMock.expectLastCall();
        EasyMock.expect(mockDataSource.getData()).andReturn(null);
        // Replays
        EasyMock.replay(mockEntityManager);
        EasyMock.replay(mockDataSource);

        // Run
        boolean result = false;
        try {
            classUnderTest.getData(displayDocumentUri);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        // Check
        EasyMock.verify(mockEntityManager);
        EasyMock.verify(mockDataSource);
        assertTrue(result, TRUE);
    }

    private DisplayDocumentUri getDummyDisplayDocumentUri(DisplayDocumentType displayDocumentType) {
        Integer courtId = Integer.valueOf(81);
        int[] courtRoomIds = {8112, 8113, 8114};
        return new DisplayDocumentUri(dummyLocale, courtId, displayDocumentType, courtRoomIds);
    }

    private DisplayDocumentType getDummyDisplayDocumentType(String descriptionCode) {
        return DisplayDocumentTypeUtils.getDisplayDocumentType(descriptionCode, dummyLocale.getLanguage(),
            dummyLocale.getCountry());
    }
}
