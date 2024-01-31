package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * <p>
 * Title: FirmListUtils Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2024
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class FirmListUtilsTest {

    private static final String NOT_EQUAL = "Result is Not Equal";
    private static final String NOT_NULL = "Result is Not Null";
    
    private static final String KEY_1 = "Key1";
    private static final String KEY_2 = "Key2";
    
    @Test
    void testGetNodeUniqueKeySameKey() {
        assertEquals(KEY_1, FirmListUtils.getNodeUniqueKey(KEY_1, KEY_1), NOT_EQUAL);
    }
    
    @Test
    void testGetNodeUniqueKey() {
        assertEquals(KEY_2, FirmListUtils.getNodeUniqueKey(KEY_1, KEY_2), NOT_EQUAL);
    }
    
    @Test
    void testGetNodeUniqueKeyNullKey() {
        assertNull(FirmListUtils.getNodeUniqueKey(null, null), NOT_NULL);
    }
}
