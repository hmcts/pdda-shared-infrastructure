package uk.gov.hmcts.framework.scheduler;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;



/**
 * <p>
 * Title: Class that handles the configuration and execution of tasks to be executed in a scheduled
 * manner.
 * </p>
 * <p>
 * Description: This class uses the strategy pattern to allow the scheduled execution of tasks
 * implemented in many manners.
 * </p>
 * <p>
 * Properties are passed in by the 'user' of this class defining the required scheduling, execution
 * strategy and it's configuration. The properties defined within Schedulable all have defaults,
 * further properties may be defined by the individual strategies that may or may not have defaults.
 * </p>
 * <p>
 * Maintains an internal instance of java.util.Timer as we need to be able to support longer running
 * tasks that if run on a shared timer may hog the shared Timer's execution thread.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Bob Boothby
 * @see java.util.Timer
 * @see TaskStrategy
 */
public class Schedulable {
    private static final String CLASS_DEFINED_IN = "Class defined in ";
    private static final Long ZERO = 0L;

    // Setup the contained timer as a Daemon.
    private final Timer timer = new Timer(true);

    // The implementation of the task executor.
    private TaskStrategy taskStrategy;

    private static Logger log = LoggerFactory.getLogger(Schedulable.class);

    private InternalTimerTask internalTimerTask;

    /**
     * Property that needs setting for determining whether the task is to run at a fixed rate or
     * not. Fixed rate is best defined by java.util.Timer
     * 
     * @see java.util.Timer
     * @see FIXED_RATE_DEFAULT
     */
    public static final String FIXED_RATE = "fixedrate";

    /**
     * Default value for the fixed rate property.
     * 
     * @see FIXED_RATE
     */
    public static final String FIXED_RATE_DEFAULT = "false";

    private boolean fixedRate;

    /**
     * Property that needs setting for determining the delay in ms before initial execution of the
     * task.
     * 
     * @see DELAY_DEFAULT
     */
    public static final String DELAY = "delay";

    /**
     * Default value for the delay property.
     * 
     * @see DELAY
     */
    public static final String DELAY_DEFAULT = "0";

    private long timerDelay;

    /**
     * Property that needs setting for determining the period in ms between executions of the task.
     * If the period is 0, the task will run once and terminate.
     * 
     * @see PERIOD_DEFAULT
     */
    public static final String PERIOD = "period";

    /**
     * Default value for the period.
     * 
     * @see PERIOD
     */
    public static final String PERIOD_DEFAULT = "0";

    private long timerPeriod;

    /**
     * Property that specifies which strategy class to use in execution of the task.
     * 
     * @see TaskStrategy
     * @see TASK_STRATEGY_DEFAULT
     */
    public static final String TASK_STRATEGY = "strategy";

    /**
     * Default value for the strategy class.
     * 
     * @see TASK_STRATEGY
     */
    public static final String TASK_STRATEGY_DEFAULT = JavaTaskStrategy.class.getName();

    private String name;

    private boolean valid = true;

    private boolean running;

    /**
     * This constructor that reads the basic scheduling properties before passing the properties to
     * the TaskStrategy implementation for initialisation.
     * 
     * @see TaskStrategy.init
     */
    protected Schedulable(String name, Properties props) {
        init(name, props);

        String strategyClassname = props.getProperty(TASK_STRATEGY, TASK_STRATEGY_DEFAULT);
        try {
            Class<?> strategyClass = Class.forName(strategyClassname);
            Constructor<?> strategyConstructor = strategyClass.getConstructor();

            // Check that it is an instance of TaskStrategy.
            if (TaskStrategy.class.isAssignableFrom(strategyClass)) {
                taskStrategy = (TaskStrategy) strategyConstructor.newInstance();
                taskStrategy.init(props, this);
            } else {
                valid = false;
                log.error("class defined in " + name + "." + TASK_STRATEGY
                    + " is not an instance of TaskStrategy.");
            }
        } catch (ClassNotFoundException cnfe) {
            valid = false;
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY + " property does not exist.",
                cnfe);
        } catch (InstantiationException ie) {
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY
                + " property could not be instantiated.", ie);
        } catch (IllegalAccessException iae) {
            log.error(
                CLASS_DEFINED_IN + name + "." + TASK_STRATEGY + " property could not be accessed.",
                iae);
        } catch (NoSuchMethodException e) {
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY
                + " does not have a constructor with no params.", e);
        } catch (SecurityException e) {
            log.error("Incorrect security access to constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        } catch (IllegalArgumentException e) {
            log.error("Incorrect argument into constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        } catch (InvocationTargetException e) {
            log.error("Unable to invoke constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        }
    }

    /**
     * This constructor that reads the basic scheduling properties before passing the properties to
     * the TaskStrategy implementation for initialisation. This variation includes the EntityManager
     * which can be used to construct the TaskStrategy
     * 
     * @see TaskStrategy.init
     */
    protected Schedulable(String name, Properties props, EntityManager em) {
        init(name, props);

        String strategyClassname = props.getProperty(TASK_STRATEGY, TASK_STRATEGY_DEFAULT);
        try {
            Class<?> strategyClass = Class.forName(strategyClassname);
            Constructor<?> strategyConstructor = strategyClass.getConstructor(EntityManager.class);

            // Check that it is an instance of TaskStrategy.
            if (TaskStrategy.class.isAssignableFrom(strategyClass)) {
                taskStrategy = (TaskStrategy) strategyConstructor.newInstance(em);
                taskStrategy.init(props, this);
            } else {
                valid = false;
                log.error("class defined in " + name + "." + TASK_STRATEGY
                    + " is not an instance of TaskStrategy.");
            }
        } catch (ClassNotFoundException cnfe) {
            valid = false;
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY + " property does not exist.",
                cnfe);
        } catch (InstantiationException ie) {
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY
                + " property could not be instantiated.", ie);
        } catch (IllegalAccessException iae) {
            log.error(
                CLASS_DEFINED_IN + name + "." + TASK_STRATEGY + " property could not be accessed.",
                iae);
        } catch (NoSuchMethodException e) {
            log.error(CLASS_DEFINED_IN + name + "." + TASK_STRATEGY
                + " does not have a constructor with no params.", e);
        } catch (SecurityException e) {
            log.error("Incorrect security access to constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        } catch (IllegalArgumentException e) {
            log.error("Incorrect argument into constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        } catch (InvocationTargetException e) {
            log.error("Unable to invoke constructor of Class defined in " + name + "."
                + TASK_STRATEGY + ".", e);
        }
    }

    private void init(String name, Properties props) {
        this.name = name;
        props.put("taskName", name);
        // No exception to be caught here as anything other than a
        // case insensitive 'true' is interpreted as false.
        fixedRate = Boolean.parseBoolean(props.getProperty(FIXED_RATE, FIXED_RATE_DEFAULT));

        try {
            timerDelay = Long.parseLong(props.getProperty(DELAY, DELAY_DEFAULT));
        } catch (NumberFormatException nfe) {
            valid = false;
            log.error("Failure parsing " + name + "." + DELAY + " Property.", nfe);
            timerDelay = Long.parseLong(DELAY_DEFAULT);
        }

        try {
            timerPeriod = Long.parseLong(props.getProperty(PERIOD, PERIOD_DEFAULT));
        } catch (NumberFormatException nfe) {
            valid = false;
            log.error("Failure parsing " + name + "." + PERIOD + " Property.", nfe);
            timerDelay = Long.parseLong(PERIOD_DEFAULT);
        }
    }

    /**
     * isValid - This method returns whether the task schedule configuration is valid and ready for
     * scheduling.
     * 
     * @return boolean Whether the task can be scheduled.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Method supplied for implementations of TaskStrategy to be able to set their validity. Will
     * not allow validity to be set to true if already false.
     * 
     * @param valid boolean value indicating how to attempt to set validity
     */
    public final void setValid(boolean valid) {
        // If it is not valid after the Schedulable constructor, then
        // it will never be valid.
        this.valid = this.valid && valid;
    }

    /**
     * getName.
     * 
     * @return the name of this particular Schedulable.
     */
    public String getName() {
        return name;
    }

    /**
     * isRunning.
     * 
     * @return for repeating tasks, whether they are currently running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Call to start this schedulable.
     * 
     * @return true if successful.
     */
    public boolean start() {
        synchronized (this) {
            if (running || !valid) {
                return false;
            }
    
            // if scheduled to run ongoing.
            if (timerPeriod > ZERO) {
                running = true;
            }
            internalTimerTask = new InternalTimerTask(taskStrategy);
    
            if (fixedRate) {
                timer.scheduleAtFixedRate(internalTimerTask, timerDelay, timerPeriod);
            } else if (timerPeriod == ZERO) {
                timer.schedule(internalTimerTask, timerDelay);
            } else {
                timer.schedule(internalTimerTask, timerDelay, timerPeriod);
            }
            return true;
        }
    }

    /**
     * Call to stop this schedulable.
     */
    public void stop() {
        synchronized (this) {
            if (running) {
                internalTimerTask.cancel();
            }
            taskStrategy.cleanup();
        }
    }

    /**
     * Private internal TimerTask descendant designed to pick up and execute the instance of
     * schedulable. NEVER to be exposed to the outside world.
     */
    private class InternalTimerTask extends TimerTask {
        private final TaskStrategy taskStrategy;

        /**
         * Constructor, taking which Task Strategy it is to execute.
         * 
         * @param taskStrategy the TaskStrategy to execute.
         */
        private InternalTimerTask(TaskStrategy taskStrategy) {
            super();
            this.taskStrategy = taskStrategy;
        }

        /**
         * This run method catches ALL exceptions thrown by the underlying implementation. They will
         * be logged.
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                taskStrategy.executeTask();
            } catch (RuntimeException t) {
                log.error("An exception was thrown during the execution of the task", t);
            } finally {
                long duration = System.currentTimeMillis() - startTime;
                if (log.isDebugEnabled()) {
                    log.debug("Executing scheduled task {} took {}ms.", name, duration);
                }
            }
        }
    }
}
