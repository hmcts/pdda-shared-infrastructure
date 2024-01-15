package uk.gov.hmcts.pdda.integration.publicdisplay.jms;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * <p>
 * Title: DailyListNotifierTest Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2022
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DailyListNotifierTest {

    private static final String DATE = "date";

    private static final String TRUE = "Result is not True";

    @Mock
    private static Context mockContext;

    @Mock
    private static Destination mockDestination;

    @Mock
    private static ConnectionFactory mockConnectionFactory;

    @Mock
    private static Connection mockConnection;

    @Mock
    private static Session mockSession;

    @Mock
    private static Properties mockProperties;

    @Mock
    private static InitialContext mockInitialContext;

    @Mock
    private static TextMessage mockTextMessage;

    @Mock
    private static MessageProducer mockMessageProducer;

    @InjectMocks
    private DailyListNotifier classUnderTest;

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @SuppressWarnings("static-access")
    @Test
    void testMain() throws Exception {
        Integer courtId = 80;
        String providerUrl = "providerUrl";
        String destinationName = "destinationName";
        String connectionFactory = "connectionFactory";
        List<String> argsList = new ArrayList<>();
        argsList.add(providerUrl);
        argsList.add(connectionFactory);
        argsList.add(destinationName);
        argsList.add(courtId.toString());
        argsList.add(DATE);

        String[] args = argsList.toArray(new String[0]);
        Mockito.mock(Properties.class);

        boolean result = false;
        try {
            classUnderTest.main(args);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }

        assertTrue(result, TRUE);
    }

    @Test
    void testSecondMain() throws NamingException, JMSException {

        final String destinationName = "destinationName";
        final String connectionFactory = "connectionFactory";
        final Integer courtId = 80;
        final String date = DATE;

        mockInitialContext = Mockito.mock(InitialContext.class);
        mockConnectionFactory = Mockito.mock(ConnectionFactory.class);
        mockSession = Mockito.mock(Session.class);
        mockTextMessage = Mockito.mock(TextMessage.class);
        mockDestination = Mockito.mock(Destination.class);
        mockMessageProducer = Mockito.mock(MessageProducer.class);
        mockConnection = Mockito.mock(Connection.class);

        Mockito.when(mockInitialContext.lookup(destinationName)).thenReturn(mockDestination);
        Mockito.when(mockInitialContext.lookup(connectionFactory)).thenReturn(mockConnectionFactory);
        Mockito.when(mockConnectionFactory.createConnection()).thenReturn(mockConnection);
        mockConnection.start();
        Mockito.atLeastOnce();

        Mockito.when(mockConnection.createSession(false, 1)).thenReturn(mockSession);
        Mockito.when(mockSession.createTextMessage()).thenReturn(mockTextMessage);
        mockTextMessage.setStringProperty(DATE, DATE);
        mockTextMessage.setIntProperty("courtId", 80);
        Mockito.when(mockSession.createProducer(mockDestination)).thenReturn(mockMessageProducer);
        mockMessageProducer.send(mockTextMessage);
        Mockito.atLeastOnce();

        mockConnection.close();
        Mockito.atLeastOnce();
        mockSession.close();
        Mockito.atLeastOnce();

        boolean result = false;
        try {
            classUnderTest.main(mockInitialContext, connectionFactory, destinationName, courtId, date);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }

        assertTrue(result, TRUE);
    }
}
