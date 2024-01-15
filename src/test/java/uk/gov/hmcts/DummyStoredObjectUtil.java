package uk.gov.hmcts;

import uk.gov.hmcts.pdda.business.services.publicdisplay.data.ejb.PdDataControllerBean;
import uk.gov.hmcts.pdda.common.publicdisplay.types.document.DisplayDocumentType;
import uk.gov.hmcts.pdda.web.publicdisplay.storage.priv.impl.DisplayStoreControllerBean;
import uk.gov.hmcts.pdda.web.publicdisplay.storage.pub.Storeable;
import uk.gov.hmcts.pdda.web.publicdisplay.storage.pub.StoredObject;
import uk.gov.hmcts.pdda.web.publicdisplay.storage.pub.Storer;
import uk.gov.hmcts.pdda.web.publicdisplay.types.document.DisplayDocument;

public final class DummyStoredObjectUtil {

    private DummyStoredObjectUtil() {
        // Do nothing
    }

    public static StoredObject getStoredObject() {
        return new StoredObject("Test content");
    }
    
    public static Storeable getStoreable(PdDataControllerBean mockPdDataControllerBean,
        DisplayStoreControllerBean mockDisplayStoreControllerBean, Storer mockStorer) {
        DisplayDocument doc = new DisplayDocument(DummyDisplayDocumentUtil.getUri(DisplayDocumentType.DAILY_LIST),
            mockPdDataControllerBean, mockDisplayStoreControllerBean, mockStorer);
        doc.setRenderedString("Test Rendered String");
        return doc;
    }

}
