package uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.ruleengine.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.events.CaseCourtRoomEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;

/**
 * <p>
 * Title: Case Being Heard Rule.
 * </p>
 * <p>
 * Description: Checks whether the case associated with event being processed has its display turned
 * on
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Rakesh Lakhani
 * @version 1.0
 */

public class CaseBeingHeardRule implements Rule {
    private static final Logger LOG = LoggerFactory.getLogger(CaseBeingHeardRule.class);

    /**
     * Rule is true if the case associated with the event being processed has its display turned on.
     * 
     * @param event An event that sub classes
     * @return boolean
     */
    @Override
    public boolean isValid(PublicDisplayEvent event) {
        if (event instanceof CaseCourtRoomEvent) {
            return ((CaseCourtRoomEvent) event).isCaseActive();
        } else {
            LOG.error("Rule called with wrong error type:{}", event.getClass());
            return false;
        }
    }
}
