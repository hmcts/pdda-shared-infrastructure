package uk.gov.hmcts.pdda.common.publicdisplay.types.uri;

/**
 * <p/>
 * Title: Identifiable.
 * </p>
 * <p/>
 * <p/>
 * Description:
 * </p>
 * <p/>
 * <p/>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p/>
 * <p/>
 * Company: Electronic Data Systems
 * </p>
 * 
 * @author Neil Ellis
 * @version $Revision: 1.4 $
 */
public interface Identifiable {
    /**
     * Return the AbstractURI that identifies the Identifiable.
     * 
     * @return the AbstractURI that identifies the Identifiable.
     * 
     * @post return != null
     */
    AbstractUri getUri();
}
