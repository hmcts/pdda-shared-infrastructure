package uk.gov.hmcts.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.framework.exception.CsUnrecoverableException;
import uk.gov.hmcts.framework.services.CsServices;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * <p>
 * Title:ReflectionEqualsHelper.
 * </p>
 * <p>
 * Description: Provides a reflection implementation of the 'equals' method. This helper is intended
 * to be used for equating actual and expected test result objects.
 * </p>
 * 
 * <p>
 * This helper could also be called from the 'equals' method on a CSAbstractValue object. This
 * helper could then be used to equate actual and expected values objects.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author AW Daley
 * @version 1.0
 */
/*
 * Ref Date Author Description
 * 
 * 17-10-2003 AW Daley Initial Version
 */
public class ReflectionEqualsHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionEqualsHelper.class);

    protected ReflectionEqualsHelper() {
        // Protected constructor
    }

    public static boolean equalFields(Object obj, Object otherObj) {
        Boolean res;
        String methodName = "equals";
        // Obtain all the fields of the class to compare.
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            // Attempts to call .equals method on each field.
            LOG.debug(methodName + "Number of fields to equate: " + fields.length);

            for (Field field : fields) {
                Method meth = getMethod(field, otherObj);

                Object res1 = getObject(meth, obj);
                Object res2 = getObject(meth, otherObj);

                // If both objects are null then they are equal
                if (res1 == null && res2 == null) {
                    continue;

                    // If one object is null and the other not then
                    // they are not equal
                } else if (res1 == null) {
                    return false;

                } else if (res2 == null) {
                    return false;
                }
                // Invoke comparison method on fields
                res = getResult(res1, res2);

                // Abort comparison if the values of a field do not equal.
                if (!res.booleanValue()) {
                    LOG.debug(methodName + "Objects are NOT equal");

                    return false;
                }

            }

            // If gets here then all fields of the object are equal.
            LOG.debug(methodName + "Objects are equal");

            return true;
        } catch (Exception ex) {
            CsServices.getDefaultErrorHandler().handleError(ex, ReflectionEqualsHelper.class,
                ex.toString());
            throw new CsUnrecoverableException(ex);
        }
    }

    private static Object getObject(Method method, Object object)
        throws IllegalAccessException, InvocationTargetException {
        if (method == null) {
            return null;
        }
        return method.invoke(object, (Object[]) null);
    }

    private static Method getMethod(Field field, Object otherObj) {
        String methodName = "equals";
        String fieldName = null;
        try {
            // Get Method to read property...
            fieldName = field.getName();

            LOG.debug(methodName + "Equating field: " + fieldName);

            PropertyDescriptor pd = getPropertyDescriptor(fieldName, otherObj.getClass());

            return pd.getReadMethod();
            // Thrown if property does not have getter/setter methods
            // a private attribute
        } catch (IntrospectionException ie) {
            // Process next field
            LOG.debug(methodName + "Field " + fieldName + " does not have getters/setters");
            return null;
        }
    }

    private static Boolean getResult(Object res1, Object res2)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String methodName = "equals";
        Method comparisonMethod = ReflectionHelper.getMethodFromClassHierarchy(res1.getClass(),
            methodName, getClassArray());

        if (comparisonMethod == null) {
            throw new NoSuchMethodException(methodName);
        }
        // Invoke comparison method on fields
        Object[] args = {res2};
        return (Boolean) comparisonMethod.invoke(res1, args);
    }

    private static Class[] getClassArray() {
        return new Class[] {Object.class};
    }

    private static PropertyDescriptor getPropertyDescriptor(String fieldName, Object otherObj)
        throws IntrospectionException {
        return new PropertyDescriptor(fieldName, otherObj.getClass());
    }

}
