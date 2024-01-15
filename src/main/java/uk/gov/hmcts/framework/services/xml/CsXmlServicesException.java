package uk.gov.hmcts.framework.services.xml;

import uk.gov.hmcts.framework.exception.CsUnrecoverableException;

/**
 * <p>
 * Title: CSXMLServicesException.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Faisal Shoukat
 * @version 1.0
 */

public class CsXmlServicesException extends CsUnrecoverableException {

    private static final long serialVersionUID = 1L;

    public CsXmlServicesException() {
        super();
    }

    /**
     * Extended constructor.
     * 
     * @param errorMessage String
     */
    public CsXmlServicesException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Constructor to store original exception with.
     * 
     * @param cause Throwable
     */
    public CsXmlServicesException(Throwable cause) {
        super(cause);
    }

    /**
     * Extended constructor.
     * 
     * @param errorMsg String
     * @param cause Throwable
     */
    public CsXmlServicesException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
