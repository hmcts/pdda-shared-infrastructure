package com.test;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundRepository;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Session Bean implementation class HelloBean.
 */
@Stateless
@Service
@Transactional
@LocalBean
public class HelloBean {

    private static final Logger LOG = LoggerFactory.getLogger(HelloBean.class);

    @Autowired
    private XhbCppStagingInboundRepository xhbCppStagingInboundRepo;

    public List<XhbCppStagingInboundDao> findAll() {
        LOG.debug("In HelloBean - 2");
        return xhbCppStagingInboundRepo.findAll();
    }

    public List<XhbCppStagingInboundDao> findAll2() {
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PDDA")) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                XhbCppStagingInboundRepository xcsi2Repo = new XhbCppStagingInboundRepository(entityManager);

                // Find all
                LOG.debug("Method 2 - finding all");
                return xcsi2Repo.findAll();
            }
        }
    }


    public String from() {
        LOG.debug("In HelloBean - 1");
        List<XhbCppStagingInboundDao> records = findAll2();
        LOG.debug("Records = {}", records.size());
        return "EJB";
    }


    /**
     * Create a new record in DB.
     */
    public String createNewRecord() {
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PDDA")) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

                try {
                    XhbCppStagingInboundDao cp = getExample1XcsiToSave();
                    entityManager.getTransaction().begin();
                    cp = entityManager.merge(cp);
                    entityManager.getTransaction().commit();
                    LOG.debug(cp != null ? "CP exists" : "CP is null");
                } catch (Exception e) {
                    entityManager.getTransaction().rollback();
                }

                return "New record added via EJB";
            }
        }
    }


    /**** Example Data. ***/

    public static XhbCppStagingInboundDao getExample1XcsiToSave() {
        XhbCppStagingInboundDao ex = new XhbCppStagingInboundDao();
        ex.setDocumentName("Using Deployed TomEE");
        ex.setDocumentType("PL");
        ex.setCourtCode("433");
        ex.setClobId(Long.valueOf(1));
        ex.setTimeLoaded(LocalDateTime.now());
        ex.setValidationStatus("VF");
        ex.setCreationDate(LocalDateTime.now());
        ex.setLastUpdateDate(LocalDateTime.now());
        ex.setCreatedBy("ScottAt");
        ex.setLastUpdatedBy("ScottAt");
        return ex;
    }

}
