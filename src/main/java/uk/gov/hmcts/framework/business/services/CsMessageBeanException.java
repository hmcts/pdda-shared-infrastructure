package uk.gov.hmcts.framework.business.services;

import uk.gov.hmcts.framework.exception.CsUnrecoverableException;

public class CsMessageBeanException extends CsUnrecoverableException {
    private static final long serialVersionUID = 1L;

    public CsMessageBeanException(Throwable cause) {
        super(cause);
    }

    public CsMessageBeanException(String msg) {
        super(msg);
    }
}
