package uk.gov.hmcts.pdda.business.services.pdda;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;

/**
 * <p>
 * Title: Config Helper.
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
 * @author Mark Harris
 * @version 1.0
 */
public class PddaConfigHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PddaConfigHelper.class);

    private static final String CONFIG_PDDA_SWITCHER = "PDDA_SWITCHER";
    private static final String CONFIG_PDDA_SWITCHER_DEFAULT = "3";
    protected static final String EMPTY_STRING = "";

    protected static final String LOG_CALLED = " called";
    private ConfigPropMaintainer configPropMaintainer;
    protected String methodName;
    private String pddaSwitcher;
    protected EntityManager entityManager;
    private XhbConfigPropRepository xhbConfigPropRepository;

    protected static class Config {
        static final String SFTP_HOST = "PDDA_BAIS_SFTP_HOSTNAME";
        static final String SFTP_PASSWORD = "PDDA_BAIS_SFTP_PASSWORD";
        static final String SFTP_UPLOAD_LOCATION = "PDDA_BAIS_SFTP_UPLOAD_LOCATION";
        static final String SFTP_USERNAME = "PDDA_BAIS_SFTP_USERNAME";
        static final String CP_SFTP_USERNAME = "PDDA_BAIS_CP_SFTP_USERNAME";
        static final String CP_SFTP_PASSWORD = "PDDA_BAIS_CP_SFTP_PASSWORD";
        static final String CP_SFTP_UPLOAD_LOCATION = "PDDA_BAIS_CP_SFTP_UPLOAD_LOCATION";
    }
    
    protected PddaConfigHelper(EntityManager entityManager) {
        this(entityManager, new XhbConfigPropRepository(entityManager));
    }
    
    // Junit constructor
    protected PddaConfigHelper(EntityManager entityManager, XhbConfigPropRepository xhbConfigPropRepository) {
        this.entityManager = entityManager;
        this.xhbConfigPropRepository = xhbConfigPropRepository;
    }

    /**
     * <p>
     * Description: Get the PDDA Switcher value 1 = Send to PDDA only 2 = Send to PDDA and process
     * in Xhibit 3 = Do not send to PDDA only process in Xhibit (DEFAULT).
     * </p>
     */
    public String getPddaSwitcher() {
        if (pddaSwitcher == null) {
            methodName = "getPDDASwitcher()";
            LOG.debug(methodName + LOG_CALLED);
            String result = getConfigValue(CONFIG_PDDA_SWITCHER);
            LOG.debug("PDDA_SWITCHER =" + result);
            pddaSwitcher = result != null ? result : CONFIG_PDDA_SWITCHER_DEFAULT;
        }
        return pddaSwitcher;
    }

    public String getConfigValue(final String propertyName) {
        methodName = "getConfigValue(" + propertyName + ")";
        LOG.debug(methodName + LOG_CALLED);
        String result = getConfigPropMaintainer().getPropertyValue(propertyName);
        LOG.debug(propertyName + " = " + result);
        return result;
    }
    
    public String getMandatoryConfigValue(final String propertyName) {
        methodName = "getMandatoryConfigValue(" + propertyName + ")";
        LOG.debug(methodName + LOG_CALLED);
        String result = getConfigValue(propertyName);
        if (result == null || EMPTY_STRING.equals(result)) {
            throw new InvalidConfigException();
        }
        return result;
    }

    protected ConfigPropMaintainer getConfigPropMaintainer() {
        if (configPropMaintainer == null) {
            configPropMaintainer = new ConfigPropMaintainer(xhbConfigPropRepository);
        }
        return configPropMaintainer;
    }
    
    public class InvalidConfigException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
