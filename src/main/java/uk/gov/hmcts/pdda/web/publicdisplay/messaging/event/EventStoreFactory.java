package uk.gov.hmcts.pdda.web.publicdisplay.messaging.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.framework.services.CsServices;



/**
 * Factory that returns a pre-configured event type.
 * 
 * @author meekun
 */
public class EventStoreFactory {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(EventStoreFactory.class);

    /** The name of the property that contains the event store type. */
    private static final String EVENT_STORE_TYPE = "public.display.event.store.type";

    protected EventStoreFactory() {
        // Protected constructor
    }

    /**
     * Creates the event store.
     * 
     * @return Event store
     */
    public static EventStore getEventStore() {

        String eventStoreClass = CsServices.getConfigServices().getProperty(EVENT_STORE_TYPE);
        EventStore eventStore;

        if (eventStoreClass == null) {
            // Create the default event store
            eventStore = new DefaultEventStore();
        } else {
            try {
                // Dynamically instantiate the event store
                eventStore = (EventStore) Class.forName(eventStoreClass).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.error(e.getMessage(), e);
                throw new EventStoreException(eventStoreClass, e);
            }
        }

        log.debug("Event store type: " + eventStore.getClass());
        return eventStore;
    }

}
