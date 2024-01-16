package uk.gov.hmcts.framework.services.audittrail;

import uk.gov.hmcts.framework.exception.CsConfigurationException;
import uk.gov.hmcts.framework.services.CsServices;

import java.util.Properties;


/**
 * <p>
 * Title: AuditTrailService.
 * </p>
 * <p>
 * Description: This is the AuditTrailService class
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Logica
 * </p>
 * 
 * @author James Powell
 * @version 1.0
 */

public final class AuditTrailService {
    private static final String AUDIT_PROVIDER_JMS = "JMS";
    private static final String AUDIT_PROVIDER_FILE = "FILE";

    private static final String PROPERTY_AUDIT_PROVIDER = "auditProvider";
    private static final String PROPERTY_MESSAGE_FORMAT = "messageFormatString";
    private static final String FACTORY_CLASS =
        "uk.gov.hmcts.pdda.business.audittrail.AuditTrailEventFactoryImpl";

    private final String messageFormatString;
    private final String auditProviderString;

    private AuditProvider auditProvider;
    private AuditTrailEventFactory auditTrailEventFactory;

    private static AuditTrailService instance = new AuditTrailService();

    /**
     * Private constructor for this singleton class. Gets and sets properties from the Resource
     * Bundle.
     *
     */
    private AuditTrailService() {
        Properties prop = CsServices.getConfigServices().getProperties("audittrail");
        auditProviderString = prop.getProperty(PROPERTY_AUDIT_PROVIDER);
        messageFormatString = prop.getProperty(PROPERTY_MESSAGE_FORMAT);
    }

    /**
     * Return the singleton instance of this class.
     * 
     * @return AuditTrailService
     */
    public static AuditTrailService getInstance() {
        return instance;
    }

    /**
     * Accepts an AuditTrailEvent and sends the formatted message to the appropriate AuditProvider.
     * 
     * @param evt - AuditTrailEvent to create AuditRecrod for
     */
    public void createAuditRecord(AuditTrailEvent evt) {
        AuditTrailMessage mess = new AuditTrailMessage(messageFormatString);
        AuditProvider ap = getAuditProvider();
        ap.sendMessage(mess.getFormattedMessage(evt));
    }

    /**
     * This class takes in an object and returns an appropriate AuditTrailEvent by using the
     * AuditTrailEventFactory.
     * 
     * @param argument Arbitrary object to create AuditTrailEvent for
     * @return AuditTrailEvent
     */
    public AuditTrailEvent getAuditTrailEvent(Object argument) {
        if (auditTrailEventFactory == null) {
            try {
                // Class is in another package to avoid circular reference so load at runtime
                auditTrailEventFactory =
                    (AuditTrailEventFactory) Class.forName(FACTORY_CLASS).newInstance();
            } catch (Exception ex) {
                CsConfigurationException exception = new CsConfigurationException(ex);
                CsServices.getDefaultErrorHandler().handleError(exception, CsServices.class);
                throw exception;
            }
        }
        return auditTrailEventFactory.getAuditTrailEvent(argument);
    }

    /**
     * Gets the auditProvider based on the property in the ResourceBundle.
     * <p/>
     * Currently there are 2 types of AuditProvider:
     * <UL>
     * <li>FileAuditProvider - Log messages directly to file (only used for development)</li>
     * <li>JMSAuditProvider - Send messages to a queue for asynchronous processing</li>
     * </UL>
     * 
     * @return AuditProvider
     */
    private AuditProvider getAuditProvider() {
        if (auditProvider == null) {
            if (AUDIT_PROVIDER_JMS.equals(auditProviderString)) {
                auditProvider = new JmsAuditProvider();
            } else if (AUDIT_PROVIDER_FILE.equals(auditProviderString)) {
                auditProvider = new FileAuditProvider();
            } else {
                // By Default, use FileAuditProvider
                auditProvider = new FileAuditProvider();
            }
        }
        return auditProvider;
    }
}
