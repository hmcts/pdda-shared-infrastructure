package uk.gov.hmcts.pdda.common.publicdisplay.events;

import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CaseChangeInformation;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.CourtRoomIdentifier;
import uk.gov.hmcts.pdda.common.publicdisplay.events.types.EventType;

/**
 * <p>
 * Title: Activate Case Event.
 * </p>
 * 
 * <p>
 * Description: This event is generated when the public display for a case in a court room is either
 * turned on or off.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * 
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Rakesh Lakhani
 * @version $Id: ActivateCaseEvent.java,v 1.3 2006/06/05 12:28:23 bzjrnl Exp $
 */
public class ActivateCaseEvent extends CaseCourtRoomEvent {

    static final long serialVersionUID = -9004892731823291611L;

    /**
     * Specify the case that has been added and to which court room.
     * 
     * @param courtRoomIdentifier The court the change occured in
     * @param caseChangeInformation The case effected
     */
    public ActivateCaseEvent(CourtRoomIdentifier courtRoomIdentifier,
        CaseChangeInformation caseChangeInformation) {
        super(courtRoomIdentifier, caseChangeInformation);
    }

    @Override
    public EventType getEventType() {
        return EventType.getEventType(EventType.ACTIVATE_CASE_EVENT);
    }
}
