package com.pdda.hb.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * EntityManagerUtilTest.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EntityManagerUtilTest {

    private static final String NULL = "Result is Null";

    private EntityManagerUtil classUnderTest;

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.clearAllCaches();
    }
    
//    @Test
//    void testDefaultConstructorException() {
//        Assertions.assertThrows(ExceptionInInitializerError.class, () -> {
//            //EntityManagerUtil classUnderTest;
//            Mockito.mockStatic(Persistence.class);
//            Mockito.when(Persistence.createEntityManagerFactory(Mockito.isA(String.class)))
//                .thenThrow(RuntimeException.class);
//            classUnderTest = new EntityManagerUtil();
//        });
//        Mockito.reset(Persistence.class);
//    }
    
    @Test
    void testDefaultConstructor() {
        // Run
        classUnderTest = new EntityManagerUtil();
        // Checks
        assertNotNull(classUnderTest, NULL);
    }
}
