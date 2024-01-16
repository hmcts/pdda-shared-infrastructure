package uk.gov.hmcts.framework.services.jms;

import uk.gov.hmcts.framework.exception.CsUnrecoverableException;

/**
 * <p>
 * Title: JMSServicesException.
 * </p>
 * <p>
 * Description: Thrown when an error occures in the JMSServices
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Will Fardell
 * @version $Id: JMSServicesException.java,v 1.1 2006/07/11 10:10:14 bzjrnl Exp $
 */
public class JmsServicesException extends CsUnrecoverableException {

    private static final long serialVersionUID = 1L;

    public JmsServicesException(Throwable cause) {
        super(cause);
    }
}
