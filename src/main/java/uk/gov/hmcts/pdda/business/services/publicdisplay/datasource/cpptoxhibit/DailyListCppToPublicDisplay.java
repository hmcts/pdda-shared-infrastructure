package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.cpptoxhibit;

import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.services.cppformatting.CppFormattingHelper;

import java.util.Date;

public class DailyListCppToPublicDisplay extends JuryCurrentStatusCppToPublicDisplay {

    public DailyListCppToPublicDisplay(Date date, int courtId, int... courtRoomIds) {
        super(date, courtId, courtRoomIds);
    }

    // Use only in unit test
    public DailyListCppToPublicDisplay(Date date, int courtId, int[] courtRoomIds,
        XhbCourtRepository xhbCourtRepository, XhbClobRepository xhbClobRepository,
        CppFormattingHelper cppFormattingHelper) {
        super(date, courtId, courtRoomIds, xhbCourtRepository, xhbClobRepository,
            cppFormattingHelper);
    }

}
