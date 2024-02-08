package com.pdda.hb.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static final EntityManagerFactory ENTITYMANAGERFACTORY;
    
    static {
        try {
            ENTITYMANAGERFACTORY = Persistence.createEntityManagerFactory("PDDA");

        } catch (RuntimeException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected EntityManagerUtil() {
        // Protected constructor
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = ENTITYMANAGERFACTORY.createEntityManager();
        entityManager.setFlushMode(FlushModeType.AUTO);
        return entityManager;

    }
}
