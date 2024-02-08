package com.pdda.hb.jpa;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>
 * Title: EntityManagerUtil Test.
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
class EntityManagerUtilTest {

    private static final String NULL = "Result is Null";

    @Test
    void testDefaultConstructor() {
        EntityManagerUtil classUnderTest = new EntityManagerUtil();
        assertNotNull(classUnderTest, NULL);
    }
}
