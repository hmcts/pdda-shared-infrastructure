package uk.gov.hmcts.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableJms
public class ActiveMqConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMqConfiguration.class);

    public static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

    /**
     * This represents a JMS server. In this case, this is an embedded JMS server.
     * In production, you always connect to external JMS servers.
     */
    @Bean
    public BrokerService createBrokerService() throws URISyntaxException {
        BrokerService broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI(DEFAULT_BROKER_URL));
        try {
            broker.addConnector(connector);
        } catch (Exception exception) {
            LOG.error("Failed to add connector: ", exception);
        }
        return broker;
    }

}