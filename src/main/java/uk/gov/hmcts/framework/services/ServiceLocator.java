package uk.gov.hmcts.framework.services;

import jakarta.ejb.EJBHome;
import jakarta.ejb.EJBLocalHome;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnectionFactory;
import jakarta.transaction.UserTransaction;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * <p>
 * Title: LocatorServices.
 * </p>
 * <p>
 * Description: Provides a base implementation of services which locate EJBs from the naming
 * conventions for binding these objects into the JNDI tree. A number of additonal services are alss
 * provided by the EJBServices interface. It is expected that this interface will primarily be used
 * by the BusinessDelegate to locate sessin beans
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Pete Raymond
 * @version 1.0
 */
public interface ServiceLocator {

    /**
     * getInitialContext.
     * 
     * @returns the InitialContext.
     */
    InitialContext getInitialContext();

    /**
     * getDataSource.
     * 
     * @returns the DataSource
     * @throws CsServicesException Exception
     */
    DataSource getDataSource();

    /**
     * getDataSource.
     * 
     * @returns the DataSource
     * @throws CsServicesException Exception
     */
    DataSource getDataSource(String name);

    /**
     * Gets an EJBLocalHome home object for the home associated with the class So for the Defendant
     * Entity Bean the DefendantRemote interface is passed in and the jhe JNDI name DefendantRemote
     * will be used for the lookkup. If DefendantController.class is passed in the JNDI name
     * DefendantContoller will be used for the lookup. The JNDI lookup will be performed only from
     * the configured subcontext for this framework application.
     * 
     * @param homeClass the Class to perform the lookup with.
     * @returns the EJBLocalHome for the found ejb
     * @throws a CSResourceUnavailableException if the ejb cannot be located
     */
    EJBLocalHome getLocalHome(Class<?> homeClass);

    /**
     * Gets an EJBHome home object for the home associated with the class So for the Defendant
     * Entity Bean the DefendantRemote interface is passed in and the jhe JNDI name DefendantRemote
     * will be used for the lookkup. If DefendantController.class is passed in the JNDI name
     * DefendantContoller will be used for the lookup. The JNDI lookup will be performed only from
     * the configured subcontext for this framework application.
     * 
     * @param homeClass the Class to perform the lookup with.
     * @returns the EJBHome for the found ejb
     * @throws a CSResourceUnavailableException if the ejb cannot be located
     */
    EJBHome getRemoteHome(Class<?> homeClass);

    /**
     * Uses the initial context to look up for the QueueConnectionFactory specified.
     * QueueConnectionFactory is used to create a QueueConnection, e.g. JMSQueueAppender, which is
     * necessary in order to obtain a QueueSession or to create a message, a QueueSender, or a
     * QueueReciever.
     * 
     * @param bindingName the String to perform the lookup with
     * @return queueConnectionFactory the QueueConnectionFactory
     * @throws CSResourceUnavailableException Exception
     */
    QueueConnectionFactory getQueueConnectionFactory(String bindingName);

    /**
     * Uses the initial context to look up for the Queue specified. Queue is used to create a
     * QueueSender when a QueueConnection is already established.
     * 
     * @param bindingName the String to perform the lookup with
     * @return queue the Queue
     * @throws CSResourceUnavailableException Exception
     */
    Queue getQueue(String bindingName);

    /**
     * Uses the initial context to look up for the TopicConnectionFactory specified.
     * 
     * @param bindingName the String to perform the lookup with
     * @return topicConnectionFactory the TopicConnectionFactory
     * @throws CSResourceUnavailableException Exception
     */
    TopicConnectionFactory getTopicConnectionFactory(String bindingName);

    /**
     * Uses the initial context to look up for the Topoc specified.
     * 
     * @param bindingName the String to perform the lookup with
     * @return topic the Topic
     * @throws CSResourceUnavailableException Exception
     */
    Topic getTopic(String bindingName);

    /**
     * Uses the initial context to look up for the TopicConnectionFactory specified.
     * 
     * @param cfBindingName the String to perform the lookup with
     * @return topicConnectionFactory the TopicConnectionFactory
     * @throws CSResourceUnavailableException Exception
     */
    ConnectionFactory getConnectionFactory(String cfBindingName);

    /**
     * Uses the initial context to look up for the Topoc specified.
     * 
     * @param bindingName the String to perform the lookup with
     * @return topic the Topic
     * @throws CSResourceUnavailableException Exception
     */
    Destination getDestination(String bindingName);

    /**
     * Gets a user transaction.
     * 
     * @return User Transaction
     */
    UserTransaction getUserTx();

}
