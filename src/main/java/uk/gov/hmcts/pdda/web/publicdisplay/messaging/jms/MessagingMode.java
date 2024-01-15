package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

/**
 * To change the template for this generated type comment go to Window - Preferences - Java - Code
 * Generation - Code and Comments.
 * 
 * @author pznwc5
 */
public final class MessagingMode {

    /** P2P messaging mode. */
    public static final MessagingMode P2P = new MessagingMode();

    /** Publish/subscribe messaging mode. */
    public static final MessagingMode PUB_SUB = new MessagingMode();

    /**
     * Private constructor.
     */
    private MessagingMode() {
        // private constructor
    }
    
    public boolean isPeer2Peer() {
        return this == P2P;
    }
    
    public boolean isPublishSubscription() {
        return this == PUB_SUB;
    }

}
