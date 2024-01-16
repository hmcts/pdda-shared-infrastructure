package uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.impl;

import uk.gov.hmcts.pdda.common.publicdisplay.events.ConfigurationChangeEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.CourtRoomEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.MoveCaseEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.WorkFlow;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.WorkFlowContext;
import uk.gov.hmcts.pdda.web.publicdisplay.workflow.pub.WorkFlowManager;

/**
 * <p>
 * Title: The DefaultWorkFlowManager.
 * </p>
 * 
 * <p>
 * Description: Passes the event to the appropriate workflow
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * 
 * <p>
 * Company: Electronic Data Systems
 * </p>
 * 
 * @author Neil Ellis
 * @version $Revision: 1.3 $
 */
public class DefaultWorkFlowManager extends WorkFlowManager {
    /**
     * Creates a new DefaultWorkFlowManager object.
     * 
     * @param context WorkFlowContext
     */
    public DefaultWorkFlowManager(WorkFlowContext context) {
        super(context);
    }

    /**
     * If it is a configuration event, it is processed by ConfigurationChangeWorkflow; A move case
     * event, is processed by MoveCaseWorkFlow; All other events are processed by
     * DefaultEventWorkFlow.
     * 
     * @param event PublicDisplayEvent
     * 
     * @return WorkFlowArray
     */
    @Override
    protected WorkFlow[] getWorkFlowsForEvent(PublicDisplayEvent event) {
        if (event instanceof ConfigurationChangeEvent) {
            return new WorkFlow[] {
                new ConfigurationChangeWorkFlow(getContext(), (ConfigurationChangeEvent) event)};
        } else if (event instanceof MoveCaseEvent) {
            return new WorkFlow[] {new MoveCaseWorkFlow(getContext(), (MoveCaseEvent) event)};
        } else {
            return new WorkFlow[] {new DefaultEventWorkFlow(getContext(), (CourtRoomEvent) event)};
        }
    }
}
