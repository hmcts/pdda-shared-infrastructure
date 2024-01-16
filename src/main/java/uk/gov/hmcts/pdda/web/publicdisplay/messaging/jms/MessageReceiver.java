package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.apache.openejb.resource.activemq.jms2.WrappingObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.UnexpectedJmsException;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.event.EventStore;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "PDDATopic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")

})
public class MessageReceiver implements MessageListener {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    /** Event store to which the messages are pushed. */
    private EventStore eventStore;

    private long courtId;

    public MessageReceiver() {
        // Default constructor
    }

    /**
     * Creates the message receiver with the event store.
     * 
     * @param eventStore EventStore
     */
    public MessageReceiver(EventStore eventStore, long courtId) {
        this.eventStore = eventStore;
        this.courtId = courtId;
        log.debug("Message receiver created");
    }

    /**
     * Asynchronous notification.
     * 
     * @param msg JMS Message
     * @throws InvalidMessageException If the JMS message is not an object message or it doesn't
     *         contain a public display event
     * @throws UnexpectedJmsException If an unexpected JMS exception occurs
     */
    @Override
    public void onMessage(Message msg) {
        log.debug("MessageReceiver(" + courtId + ")::onMessage: " + msg);
        if (msg instanceof WrappingObjectMessage) {
            // ignore these as they are duplicated versions of ObjectMessage under activemq
            log.debug("WrappingObjectMessage - ignored");
        } else {
            PublicDisplayEvent event = MessageTransformerFactory.transform(msg);
            log.debug(
                "Message Event: Type=" + event.getEventType() + " CourtId=" + event.getCourtId());

            if (eventStore != null) {
                eventStore.pushEvent(event);
                log.debug("Event pushed to the event queue");
            }
        }
    }

}
