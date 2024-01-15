package com.test;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.MessageController;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessagingMode;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.Subscription;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Servlet implementation class HelloServlet.
 */
public class HelloServlet2 extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloServlet2.class);
    private static final long serialVersionUID = 4723679809450870621L;

    @Resource(name = "PDDATestTopic") // From tomee.xml
    private Topic testTopic;

    @EJB
    private HelloMdb mdbean;

    @Resource
    private ConnectionFactory connectionFactory;

    /**
     * doGet.
     * 
     *      <p>Tests: 1 servlet deploys on TomEE and is accessible 2 session bean deploys on TomEE and
     *      is accessible 3 can use CDI via pojo 4 TomEE datasource an be accessed 5 Access a JMS
     *      Topic declared on the fly 6 Access a JMS Topic configured globally in TomEE.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        String text =
            request.getParameter("text") != null ? request.getParameter("text") : "Hello World";

        try (
            Writer writer = response.getWriter()) {
            writer.append("<html>");
            writer.append("<body>");
            writer.append("<h2>Hello Servlet!!</h2>");

            // Test
            try (
                TopicConnection conn = getTopicConnection()) {

                // Create a session
                try (TopicSession session = getTopicSession(conn)) {

                    MessageController messageController = new MessageController(2);
                    messageController.addSubscription(testTopic, connectionFactory, 94,
                        MessagingMode.PUB_SUB);
                    messageController.startEventProcessing();
    
                    // Create a MessageProducer from the Session to the Topic (or Queue)
                    try (MessageProducer producer = getMessageProducer(session)) {
                        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        
                        // Create a message
                        TextMessage message = session.createTextMessage(text + " from this global topic");
        
                        // Tell the producer to send the message
                        producer.send(message);
        
                        writer.append(
                            "<h2>Hello - Message sent using internal ActiveMQ with conf in tomee.xml</h2>");
        
                        for (Subscription sub : messageController.getSubscriptions()) {
                            if (sub != null) {
                                writer.append("<h2>Message Received: ");
                                writer.append(((TextMessage) sub.receive(1000)).getText());
                                writer.append("</h2>");
                            }
                        }
                    }
                }

            } catch (Exception e) {
                writer.append("<p><b>Exception: ");
                writer.append(e.getClass().getName());
                writer.append("</b><p>");
                e.printStackTrace((PrintWriter) writer);
            }

            /**
             * try (final TopicConnection conn =
             * (TopicConnection)connectionFactory.createConnection()){
             * 
             * // Create a session TopicSession session = conn.createTopicSession(false,
             * Session.AUTO_ACKNOWLEDGE);
             * 
             * TopicSubscriber consumer = session.createSubscriber(testTopic);
             * consumer.setMessageListener(mdbean); conn.start();
             * 
             * // Create a MessageProducer from the Session to the Topic (or Queue) MessageProducer
             * producer = session.createProducer(testTopic);
             * producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
             * 
             * // Create a message TextMessage message = session.createTextMessage(text + " from
             * this global topic3");
             * 
             * // Tell the producer to send the message producer.send(message);
             * 
             * writer.append("
             * <h2>Hello - Message sent using internal ActiveMQ with conf in tomee.xml</h2>");
             * 
             * writer.append("
             * <h2>Message Received: "); writer.append(((TextMessage)
             * consumer.receive(1000)).getText()); writer.append("</h2>");
             * 
             * } catch (Exception e) { writer.append("
             * <p>
             * <b>Exception: "); writer.append(e.getClass().getName()); writer.append("</b>
             * <p>
             * "); e.printStackTrace((PrintWriter)writer); } finally { if (session != null ) { try {
             * session.close(); } catch (JMSException e) { e.printStackTrace(); } } if (producer !=
             * null ) { try { producer.close(); } catch (JMSException e) { e.printStackTrace(); } }
             * }
             **/

            try {
                writer.append("</body>");
                writer.append("</html>");
            } catch (IOException e) {
                LOG.error("Error appending: {}", e.getMessage());
            }
        } catch (IOException e) {
            LOG.error("Error: {}", e.getMessage());
        }
    }

    private TopicConnection getTopicConnection() throws JMSException {
        try (TopicConnection conn = (TopicConnection) connectionFactory.createConnection()) {
            return conn;
        }
    }

    private TopicSession getTopicSession(TopicConnection conn) throws JMSException {
        try (TopicSession session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE)) {
            return session;
        }
    }

    private MessageProducer getMessageProducer(TopicSession session) throws JMSException {
        try (MessageProducer producer = session.createProducer(testTopic)) {
            return producer;
        }
    }

}
