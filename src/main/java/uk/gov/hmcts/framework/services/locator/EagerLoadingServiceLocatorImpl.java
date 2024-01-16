package uk.gov.hmcts.framework.services.locator;

import uk.gov.hmcts.framework.exception.CsUnrecoverableException;
import uk.gov.hmcts.framework.security.SubjectManager;

import java.security.PrivilegedActionException;
import java.util.HashMap;
import java.util.Map;

/**
 * EagerLoadingServiceLocatorImpl.
 * 
 * @author Meeraj
 * @title EagerServiceLocatorImpl
 */
public class EagerLoadingServiceLocatorImpl extends ServiceLocatorImpl {

    private Map<Object, Object> jndiCache;

    /**
     * Eager loads the service locator cache.
     */
    @SuppressWarnings("unchecked")
    protected EagerLoadingServiceLocatorImpl() {
        super();
        try {

            LookupHelper lookupHelper = new LookupHelper(getEnv());
            jndiCache = (HashMap<Object, Object>) SubjectManager.getInstance().runAs(lookupHelper);
        } catch (PrivilegedActionException ex) {
            throw handleException(ex);
        }
    }

    /**
     * Utility method to lookup objects.
     * 
     * @param jndiName String
     * @return Object
     */
    @Override
    protected Object lookup(String jndiName) {
        if (!jndiCache.containsKey(jndiName)) {
            throw new CsUnrecoverableException("Object not found, " + jndiName);
        }
        return jndiCache.get(jndiName);
    }

}
