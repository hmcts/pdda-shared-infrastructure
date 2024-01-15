package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicSession;
import jakarta.jms.TopicSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayJmsConstants;

/**
 *         Provides a publish/subscribe model subscription.
 * @author meekun
 */
public class PubSubSubscription extends Subscription {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(PubSubSubscription.class);

    /** Topic subscriber. */
    private TopicSubscriber subscriber;

    /** Topic connection factory that is used. */
    private final TopicConnection topicConnection;

    /**
     * Creates a new PubSubSubscription object.
     * 
     * @param courtId Court id for which the subscription is made
     */
    public PubSubSubscription(Topic topic, ConnectionFactory connectionFactory, long courtId)
        throws JMSException {
        super();
        String messageSelector = PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME + "=" + courtId;
        log.debug("Message selector: {}", messageSelector);

        topicConnection = (TopicConnection) connectionFactory.createConnection();
        log.debug("Topic connection created for court: {}", courtId);

        try (TopicSession sess =
            topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE)) {
            log.debug("Topic session created for court: {}", courtId);

            subscriber = sess.createSubscriber(topic, messageSelector, true);
            log.debug("Topic receiver created for court: {}", courtId);

            setCourtId(courtId);
        }
    }

    /**
     * Returns the topic connection.
     * 
     * @return Topic connection
     */
    @Override
    protected Connection getConnection() {
        return topicConnection;
    }

    /**
     * Returns the topic subscriber.
     * 
     * @return Topic subscriber
     */
    @Override
    protected MessageConsumer getConsumer() {
        return subscriber;
    }

}
