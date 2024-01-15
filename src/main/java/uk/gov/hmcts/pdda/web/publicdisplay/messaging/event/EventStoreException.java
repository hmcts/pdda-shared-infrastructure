package uk.gov.hmcts.pdda.web.publicdisplay.messaging.event;

import uk.gov.hmcts.pdda.common.publicdisplay.exceptions.PublicDisplayRuntimeException;

/**
 * EventStoreException.
 * 
 * @author pznwc5 The exception is thrown when we can't create the event store
 */
public class EventStoreException extends PublicDisplayRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * EventStoreException.
     * 
     * @param eventStoreClass Event store class
     * @param th Root exception
     */
    public EventStoreException(String eventStoreClass, Throwable th) {
        super("Invalid event store type: " + eventStoreClass, th);
    }
}
