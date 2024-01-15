package com.test;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3.CppStagingInboundControllerBean;
import uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3.CppStagingInboundControllerException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Servlet implementation class HelloServlet.
 */
public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = 4723679809450870621L;
    private static final Logger LOG = LoggerFactory.getLogger(HelloServlet.class);

    @jakarta.ejb.EJB
    @Inject
    private CppStagingInboundControllerBean sessionBean2;

    @Resource(name = "jdbc/PDDA_postgreXADS")
    private DataSource dataSource;

    @Resource(name = "aTestJMSTopic") // Created on the fly as no reference in conf files
    private Topic testTopic;

    @Resource(name = "barTopic") // From activemq.xml
    private Topic testTopic2;

    @Resource(name = "SampleTestTopic") // From tomee.xml
    private Topic testTopic3;

    @jakarta.ejb.EJB
    private HelloMdb mdbean;

    @Resource(name = "MyJMSConnectionFactory_tomee")
    private ConnectionFactory connectionFactory;

    /**
     * doGet.
     * 
     * <p>Tests: 1 servlet deploys on TomEE and is accessible 2 session bean deploys on TomEE and is
     * accessible 3 can use CDI via pojo 4 TomEE datasource an be accessed 5 Access a JMS Topic declared
     * on the fly 6 Access a JMS Topic configured globally in TomEE.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        try (
            // Tests 1-3
            java.io.Writer writer = response.getWriter()) {
            writer.append("<html>");
            writer.append("<body>");
            writer.append("<h2>Hello Servlet!!</h2>");
            LOG.debug("About to call cppstagingcontrollerbean");
            List<XhbCppStagingInboundDao> unprocessedDocs = getUnprocessedDocs();
            writer.append("no of docs:" + unprocessedDocs.size());
            if (!unprocessedDocs.isEmpty()) {
                writer.append("going to set it to processed");
                sessionBean2.updateStatusProcessingSuccess(unprocessedDocs.get(0), "FredBloggs");
                writer.append("Doc updated");
            }

            final Map<String, Object> bindings = new ConcurrentHashMap<>();
            writer.append("<p>About to get a datasource</p>");
            writer.append("<p>About to get context and datasource in servlet</p>");
            Context context = new InitialContext();
            writer.append("<p>Context obtained .... </p>");
            writer.append("<p>Context1=" + context.toString() + "</p>");
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/PDDA_postgreXADS");
            try (java.sql.Connection sqlConnection = ds.getConnection()) {
                testPreparedStatement(sqlConnection);
            }
            writer.append("<p>Context2=" + context.toString() + "</p>");
            addBindings("", bindings, context);

            // Trying with Resource declared above
            writer.append("<p>This datasource is " + dataSource + "</p>");

            writer.append("<p>End of datasource</p>");
            writer.append("</body>");
            writer.append("</html>");

            LOG.debug("JNDI Context:");

            // Test 5
            try (Connection conn = getConnection(writer, "Exception 1")) {
                conn.start();

                // Create a session
                try (Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

                    // Create a MessageProduec from the Session to the Topic (or Queue)
                    try (MessageProducer producer = session.createProducer(testTopic)) {
                        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                        // Create a message
                        TextMessage message = session.createTextMessage("Hello from this on-the-fly topic");

                        // Tell the producer to send the message
                        producer.send(message);

                        writer.append("<h2>Hello - Message sent using in-flight JMS</h2>");
                    }
                }
            }

            // Test 6
            try (Connection conn = getConnection(writer, "\n\nException 2")) {

                // Create a session
                try (Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

                    // Create a MessageProduec from the Session to the Topic (or Queue)
                    try (MessageProducer producer = session.createProducer(testTopic2)) {
                        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                        // Create a message
                        TextMessage message = session.createTextMessage("Hello from this topic2");

                        // Tell the producer to send the message
                        producer.send(message);

                        writer.append(
                            "<h2>Hello - Message sent using internal ActiveMQ with conf in activemq.xml</h2>");
                    }

                    // Create a MessageProducer from the Session to the Topic (or Queue)
                    try (MessageProducer producer = session.createProducer(testTopic3)) {
                        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                        // Create a message
                        TextMessage message = session.createTextMessage("Hello from this global topic3");

                        // Tell the producer to send the message
                        producer.send(message);

                        writer.append("<h2>Hello - Message sent using internal ActiveMQ with conf in tomee.xml</h2>");
                    }
                }
            }
        } catch (JMSException e) {
            LOG.error("JMSException: {}", e.getMessage());
        } catch (NamingException e) {
            LOG.error("NamingException: {}", e.getMessage());
        } catch (IOException e) {
            LOG.error("IOEXception: {}", e.getMessage());
        } catch (SQLException e) {
            LOG.error("SQLException: {}", e.getMessage());
        }
    }

    private Connection getConnection(java.io.Writer writer, String exceptionText) throws JMSException, IOException {
        try (Connection conn = connectionFactory.createConnection()) {
            return conn;
        } catch (JMSException e) {
            writer.append(exceptionText);
            throw e;
        }
    }

    private PreparedStatement testPreparedStatement(java.sql.Connection sqlConnection) throws SQLException {
        try (PreparedStatement stmt = sqlConnection.prepareStatement("select * from xhb_config_prop")) {
            return stmt;
        }
    }

    private List<XhbCppStagingInboundDao> getUnprocessedDocs() {
        try {
            return sessionBean2.getLatestUnprocessedDocument();
        } catch (CppStagingInboundControllerException e) {
            return new ArrayList<>();
        }
    }

    private void addBindings(String path, Map<String, Object> bindings, Context context) {
        try {
            for (NameClassPair pair : Collections.list(context.list(""))) {
                String name = pair.getName();
                String className = pair.getClassName();
                if ("org.apache.naming.resources.FileDirContext$FileResource".equals(className)) {
                    bindings.put(path + name, "<file>");
                } else {
                    addNonClassBindings(path, bindings, context, name);
                }
            }
        } catch (NamingException e) {
            bindings.put(path, "ERROR: list bindings threw an exception: " + e.getMessage());
        }
    }

    private void addNonClassBindings(String path, Map<String, Object> bindings, Context context, String name) {
        try {
            Object value = context.lookup(name);
            if (value instanceof Context) {
                Context nextedContext = (Context) value;
                bindings.put(path + name, "");
                addBindings(path + name + "/", bindings, nextedContext);
            } else {
                bindings.put(path + name, value);
            }
        } catch (NamingException e) {
            // lookup failed
            bindings.put(path + name, "ERROR: " + e.getMessage());
        }
    }
}
