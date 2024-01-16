package uk.gov.hmcts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Hello world.
 *
 */
public class PddaApp {
    private static final Logger LOG = LoggerFactory.getLogger(PddaApp.class);

    protected PddaApp() {
        // protected constructor
    }

    public static void main(String[] args) {
        LOG.debug("Hello World!");
    }
}
