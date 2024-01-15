package uk.gov.hmcts;

import uk.gov.hmcts.pdda.business.services.publicdisplay.data.ejb.PdDataControllerBean;
import uk.gov.hmcts.pdda.common.publicdisplay.data.Data;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.PublicDisplayValue;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class DummyDataUtil {

    private static final String NOTNULL = "Result is Null";
    
    private DummyDataUtil() {
        // Do nothing
    }

    public static PdDataControllerBean getDataDelegate() {
        return new PdDataControllerBean();
    }

    public static Data getData() {
        return new Data();
    }
    

    public static Data getDataWithTable(Collection<PublicDisplayValue> table) {
        Data data = new Data();
        data.setTable(new ArrayList<PublicDisplayValue>());
        data.clear();
        data.setCourtName("Test Court Name");
        data.setCourtRoomName("Test Court Room Name");
        data.setCourtSiteShortName("Test Court Site Name");
        data.setTable(table);
        assertNotNull(data.getTable(), NOTNULL);
        assertNotNull(data.toString(), NOTNULL);
        return data;
    }

}
