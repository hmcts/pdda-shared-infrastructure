package uk.gov.hmcts.pdda.business.entities;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdda.business.entities.xhbconfiguredpublicnotice.XhbConfiguredPublicNoticeDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbcrlivedisplay.XhbCrLiveDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing.XhbScheduledHearingDao;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("static-access")
@ExtendWith(EasyMockExtension.class)
class PddaEntityHelperTest {

    private static final String NOT_TRUE = "Result is not True";

    @Test
    void testDefaultConstructor() {
        boolean result = true;
        new PddaEntityHelper();
        assertTrue(result, NOT_TRUE);
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
