package uk.gov.hmcts.pdda.integration.publicdisplay.jms;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * DailyListNotifier.
 * 
 * <p>Used to send an empty text message to the public display with two message properties 'courtId'
 * and 'date'.
 * 
 * @author bzjrnl
 */
public final class DailyListNotifier {

    private static final String COURT_ID_MESSAGE_PROPERTY = "courtId";
    private static final String DATE_MESSAGE_PROPERTY = "date";
    private static final Integer MAX_PARAMS = 5;
    private static final Logger LOG = LoggerFactory.getLogger(DailyListNotifier.class);

    private DailyListNotifier() {
        // private constructor
    }

    /**
     * Send a public display notification message.
     * 
     * @param args the command line arguments to use
     */
    public static void main(String[] args) {
        if (MAX_PARAMS.equals(args.length)) {
            NotifierAttributes notifier = new NotifierAttributes();
            notifier.setProviderUrl(args[0]);
            notifier.setConnectionFactoryName(args[1]);
            notifier.setDestinationName(args[2]);
            notifier.setCourtId(Integer.parseInt(args[3]));
            notifier.setDate(args[4]);

            main(notifier);
        } else {
            LOG.debug("Usage: java " + DailyListNotifier.class.getName()
                + "<Midtier URL> <Connection Factory Name> "
                + "<Destination Name> <Court Id> <Date>");
            System.exit(1);
        }
    }

    /**
     * Send a public display notification message.
     * 
     * @param notifier NotifierAttributes

     * @throws NamingException Exception
     * @throws JMSException Exception
     */
    public static void main(NotifierAttributes notifier) {
        LOG.warn("Info: Notifing public display.");
        LOG.warn("Info:     providerUrl: " + notifier.getProviderUrl());
        LOG.warn("Info:     connectionFactoryName: " + notifier.getConnectionFactoryName());
        LOG.warn("Info:     destinationName: " + notifier.getDestinationName());
        LOG.warn("Info:     courtId: " + notifier.getCourtId());
        LOG.warn("Info:     date: " + notifier.getDate());
        try {
            Properties properties = createEnvironment(notifier.getProviderUrl());
            Context ctx = new InitialContext(properties);
            try {
                main(ctx, notifier.getConnectionFactoryName(), notifier.getDestinationName(),
                    notifier.getCourtId(), notifier.getDate());
                LOG.info("Info: Sending message succeded.");
            } finally {
                close(ctx);
            }
        } catch (NamingException | JMSException e) {
            LOG.error("Error: Sending message failed.", e);
        }
    }

    public static void main(Context ctx, String connectionFactoryName, String destinationName,
        int courtId, String date) throws NamingException, JMSException {
        Destination destination = (Destination) ctx.lookup(destinationName);
        ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup(connectionFactoryName);
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            main(destination, connection, courtId, date);
        }
    }

    private static void main(Destination destination, Connection connection, int courtId,
        String date) throws JMSException {
        try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            main(destination, session, courtId, date);
        }
    }

    private static void main(Destination destination, Session session, int courtId, String date)
        throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setStringProperty(DATE_MESSAGE_PROPERTY, date);
        message.setIntProperty(COURT_ID_MESSAGE_PROPERTY, courtId);
        try (MessageProducer producer = session.createProducer(destination)) {
            producer.send(message);
        }
        LOG.debug("Info: Notifying public display.");
        LOG.debug("Info: courtId: " + courtId);
        LOG.debug("Info: Date: " + date);
    }

    /**
     * Creates the initial context.
     * 
     * @param url Provider URL
     * @return Initial context
     */
    public static Properties createEnvironment(String url) {
        Properties env = new Properties();
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        return env;
    }

    /**
     * Close the context quietly.
     */
    private static void close(Context context) {
        try {
            if (context != null) {
                context.close();
                LOG.debug("Context closed.");
            }
        } catch (NamingException e) {
            LOG.error("Warning: An error occured closing the context.", e);
        }
    }
}
