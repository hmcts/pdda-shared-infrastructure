package uk.gov.hmcts.pdda.business.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.business.entities.xhbconfiguredpublicnotice.XhbConfiguredPublicNoticeDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbcrlivedisplay.XhbCrLiveDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingDao;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PddaEntityHelperTest {

    private static final String NOT_TRUE = "Result is not True";
    private static final String NULL = "Result is Null";

    @AfterEach
    public void clearCaches() {
        Mockito.clearAllCaches();
    }
    
    @Test
    void testDefaultConstructor() {
        PddaEntityHelper classUnderTest = new PddaEntityHelper();
        assertNotNull(classUnderTest, NULL);
    }
    
    @Test
    void testXcpnFindByPrimaryKey() {
        assertTrue(
            PddaEntityHelper
                .xcpnFindByPrimaryKey(0) instanceof Optional<XhbConfiguredPublicNoticeDao>,
            NOT_TRUE);
    }

    @Test
    void testcpnUpdate() {
        XhbConfiguredPublicNoticeDao xhbConfiguredPublicNoticeDao =
            new XhbConfiguredPublicNoticeDao();
        assertTrue(
            PddaEntityHelper.cpnUpdate(
                xhbConfiguredPublicNoticeDao) instanceof Optional<XhbConfiguredPublicNoticeDao>,
            NOT_TRUE);
    }

    @Test
    void testXcrtFindByPrimaryKey() {
        assertTrue(PddaEntityHelper.xcrtFindByPrimaryKey(0) instanceof Optional<XhbCourtRoomDao>,
            NOT_TRUE);
    }

    @Test
    void testXcldSave() {
        XhbCrLiveDisplayDao xhbCrLiveDisplayDao = new XhbCrLiveDisplayDao();
        assertTrue(
            PddaEntityHelper.xcldSave(xhbCrLiveDisplayDao) instanceof Optional<XhbCrLiveDisplayDao>,
            NOT_TRUE);
    }

    @Test
    void testXshFindByPrimaryKey() {
        assertTrue(
            PddaEntityHelper.xshFindByPrimaryKey(0) instanceof Optional<XhbScheduledHearingDao>,
            NOT_TRUE);
    }
}
