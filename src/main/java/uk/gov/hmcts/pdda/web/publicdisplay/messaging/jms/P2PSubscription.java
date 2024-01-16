package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.annotation.Resource;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueReceiver;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayJmsConstants;

/**
 * The class provides a P2P subscription.
 * 
 * @author meekun
 */
public class P2PSubscription extends Subscription {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(P2PSubscription.class);

    /** Receiver used by the subscription. */
    private QueueReceiver receiver;

    @Resource
    private ConnectionFactory connectionFactory;

    /** Topic connection factory that is used. */
    private QueueConnection queueConnection;

    private QueueSession queueSession;

    @Resource(name = "PDDAQueue")
    private Queue queue;

    /**
     * Creates a new P2PSubscription object.
     * 
     * @param courtId Court Id for which the subscription is made
     */
    public P2PSubscription(long courtId) throws JMSException {
        super();
        init(courtId);
    }

    /**
     * JUnit constructor.
     */
    public P2PSubscription(long courtId, ConnectionFactory connectionFactory,
        QueueConnection queueConnection, QueueSession queueSession, Queue queue)
        throws JMSException {
        super();
        this.connectionFactory = connectionFactory;
        this.queueConnection = queueConnection;
        this.queueSession = queueSession;
        this.queue = queue;
        init(courtId);
    }

    private void init(long courtId) throws JMSException {
        String messageSelector = PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME + "=" + courtId;
        log.debug("Message selector: {}", messageSelector);

        queueConnection = (QueueConnection) connectionFactory.createConnection();
        log.debug("Queue connection created for court: {}", courtId);

        try (QueueSession sess = getQueueSession()) {
            log.debug("Queue session created for court: {}", courtId);
    
            receiver = sess.createReceiver(queue, messageSelector);
            log.debug("Queue receiver created for court: {}", courtId);
    
            setCourtId(courtId);
        }
    }

    /**
     * Returns the queue connection.
     * 
     * @return Queue connection
     */
    @Override
    protected Connection getConnection() {
        return queueConnection;
    }

    /**
     * Returns the queue receiver.
     * 
     * @return Queue receiver
     */
    @Override
    protected MessageConsumer getConsumer() {
        return receiver;
    }

    private QueueSession getQueueSession() throws JMSException {
        if (queueSession == null) {
            return queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        return queueSession;
    }
}
