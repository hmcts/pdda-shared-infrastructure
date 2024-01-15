package uk.gov.hmcts.pdda.business.services.pdda;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbpddadlnotifier.XhbPddaDlNotifierDao;
import uk.gov.hmcts.pdda.business.entities.xhbpddadlnotifier.XhbPddaDlNotifierRepository;
import uk.gov.hmcts.pdda.integration.publicdisplay.jms.DailyListNotifier;
import uk.gov.hmcts.pdda.integration.publicdisplay.jms.NotifierAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title: PDDADlNotifierHelper.
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
public class PddaDlNotifierHelper extends PddaConfigHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PddaDlNotifierHelper.class);

    private static final String CONFIG_DL_NOTIFIER_PROVIDER_URL = "DL_NOTIFIER_PROVIDER_URL";
    private static final String CONFIG_DL_NOTIFIER_CONNECTION_FACTORY = "DL_NOTIFIER_CONNECTION_FACTORY";
    private static final String CONFIG_DL_NOTIFIER_DESTINATION = "DL_NOTIFIER_DESTINATION";
    private static final String CONFIG_DL_NOTIFIER_EXECUTION_TIME = "DL_NOTIFIER_EXECUTION_TIME";
   
    
    private static final String YES = "Y";
    private static final String STR_DATEFORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern(STR_DATEFORMAT);
    private static final DateTimeFormatter DATETIMEFORMAT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss");
    private static final DateTimeFormatter EXECUTIONTIMEFORMAT =
        DateTimeFormatter.ofPattern(STR_DATEFORMAT + "HH:mm");

    private XhbPddaDlNotifierRepository pddaDlNotifierRepository;
    private XhbCourtRepository courtRepository;
    private Notifier notifier;

    public PddaDlNotifierHelper(EntityManager entityManager) {
        super(entityManager);
    }

    // Junit constructor
    public PddaDlNotifierHelper(EntityManager entityManager, XhbConfigPropRepository xhbConfigPropRepository) {
        super(entityManager, xhbConfigPropRepository);
    }
    
    public boolean isDailyNotifierRequired() {
        return isTimeToExecute() && isSendToPddaOnly();
    }

    private boolean isTimeToExecute() {
        LocalDateTime executionDateTime = getExecutionTime();
        LocalDateTime timeNow = LocalDateTime.now();
        return executionDateTime != null && timeNow.isAfter(executionDateTime);
    }

    private LocalDateTime getExecutionTime() {
        try {
            String propertyValue = getConfigValue(CONFIG_DL_NOTIFIER_EXECUTION_TIME);
            String stringDate = LocalDateTime.now().format(DATEFORMAT) + propertyValue;
            LocalDateTime date = LocalDateTime.parse(stringDate, EXECUTIONTIMEFORMAT);
            LOG.error(CONFIG_DL_NOTIFIER_EXECUTION_TIME + " = " + propertyValue);
            return date;
        } catch (Exception ex) {
            LOG.error(CONFIG_DL_NOTIFIER_EXECUTION_TIME + " contains invalid time");
            return null;
        }
    }

    /**
     * Send messages to PDDA Only (future way).
     */
    private boolean isSendToPddaOnly() {
        return "1".equals(getPddaSwitcher());
    }

    public void runDailyListNotifier() {
        String providerUrl = getConfigValue(CONFIG_DL_NOTIFIER_PROVIDER_URL);
        String connectionFactoryName = getConfigValue(CONFIG_DL_NOTIFIER_CONNECTION_FACTORY);
        String destinationName = getConfigValue(CONFIG_DL_NOTIFIER_DESTINATION);

        String date = LocalDateTime.now().format(DATETIMEFORMAT);

        // Loop through all the active courts
        List<XhbCourtDao> allCourts = getCourtRepository().findAll();
        if (allCourts != null) {
            for (XhbCourtDao court : allCourts) {
                if (!YES.equals(court.getObsInd())) {
                    runDailyListNotifier(providerUrl, connectionFactoryName, destinationName,
                        court.getCourtId(), date);
                }
            }
        }
    }

    /*
     * Notify the public display for this court that there is a new days list.
     */
    private void runDailyListNotifier(String providerUrl, String connectionFactoryName,
        String destinationName, Integer courtId, String date) {
        methodName = "runDailyListNotifier(" + courtId + ")";
        LOG.debug(methodName + " called");
        LocalDateTime lastRunDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

        // Get the record for the court and date
        XhbPddaDlNotifierDao dao = findByCourtAndLastRunDate(courtId, lastRunDate);

        // Check there isn't already a record written for today
        if (dao.getPddaDlNotifierId() == null) {

            // Set the status as RUNNING
            DlNotifierStatusEnum runnningEnum = DlNotifierStatusEnum.RUNNING;
            setStatus(dao, runnningEnum.getStatus(), null);

            // Fire off the notifier
            DlNotifierStatusEnum successEnum = DlNotifierStatusEnum.SUCCESS;
            String status = successEnum.getStatus();
            String errorMessage = null;
            try {
                getNotifier().setProviderUrl(providerUrl);
                getNotifier().setConnectionFactoryName(connectionFactoryName);
                getNotifier().setDestinationName(destinationName);
                getNotifier().setCourtId(courtId);
                getNotifier().setDate(date);
                getNotifier().run();
            } catch (Exception ex) {
                DlNotifierStatusEnum failureEnum = DlNotifierStatusEnum.FAILURE;
                status = failureEnum.getStatus();
                errorMessage = ex.getMessage();
            } finally {
                // Refetch the record ready for updating
                dao = findByCourtAndLastRunDate(courtId, lastRunDate);
                setStatus(dao, status, errorMessage);
            }
        }
    }

    public void setStatus(XhbPddaDlNotifierDao dao, String status, String errorMessage) {
        dao.setStatus(status);
        dao.setErrorMessage(errorMessage);
        Optional<XhbPddaDlNotifierDao> savedDao = getPddaDlNotifierRepository().update(dao);
        if (!savedDao.isEmpty()) {
            LOG.debug("PddaDlNotifier saved");
        }
    }

    private XhbPddaDlNotifierDao findByCourtAndLastRunDate(Integer courtId,
        LocalDateTime lastRunDate) {
        XhbPddaDlNotifierDao dao;
        List<XhbPddaDlNotifierDao> daoList =
            getPddaDlNotifierRepository().findByCourtAndLastRunDate(courtId, lastRunDate);
        if (daoList.isEmpty()) {
            dao = new XhbPddaDlNotifierDao();
            dao.setCourtId(courtId);
            dao.setLastRunDate(lastRunDate);
        } else {
            dao = daoList.get(0);
        }
        return dao;
    }


    private XhbPddaDlNotifierRepository getPddaDlNotifierRepository() {
        if (pddaDlNotifierRepository == null) {
            pddaDlNotifierRepository = new XhbPddaDlNotifierRepository(entityManager);
        }
        return pddaDlNotifierRepository;
    }

    private XhbCourtRepository getCourtRepository() {
        if (courtRepository == null) {
            courtRepository = new XhbCourtRepository(entityManager);
        }
        return courtRepository;
    }

    private Notifier getNotifier() {
        if (notifier == null) {
            notifier = new Notifier();
        }
        return notifier;
    }

    public class Notifier {
        private String providerUrl;
        private String connectionFactoryName;
        private String destinationName;
        private int courtId;
        private String date;

        public void run() {
            
            NotifierAttributes notifier = new NotifierAttributes();
            
            notifier.setProviderUrl(providerUrl);
            notifier.setConnectionFactoryName(connectionFactoryName);
            notifier.setDestinationName(destinationName);
            notifier.setCourtId(courtId);
            notifier.setDate(date);
            
            DailyListNotifier.main(notifier);
        }

        public void setProviderUrl(String providerUrl) {
            this.providerUrl = providerUrl;
        }

        public void setConnectionFactoryName(String connectionFactoryName) {
            this.connectionFactoryName = connectionFactoryName;
        }

        public void setDestinationName(String destinationName) {
            this.destinationName = destinationName;
        }

        public void setCourtId(int courtId) {
            this.courtId = courtId;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
