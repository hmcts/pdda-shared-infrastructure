package com.pdda.hb.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ExampleTestJpa {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleTestJpa.class);
    private final EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public static void main(String[] args) {

        // Method 1 - calling Entity direct
        ExampleTestJpa example = new ExampleTestJpa();
        LOG.debug("Before insertion 1 ");
        example.listXcsi();
        LOG.debug("After Successful modification ");
        example.updateXcsi(5, getExample1XcsiToUpdate());
        example.listXcsi();
        LOG.debug("After Successful deletion ");
        example.deleteXcsi(5);
        example.listXcsi();


        // Method 2 - using entity factory - preferred approach!!
        LOG.debug("Method 2 - begin");
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PDDA")) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                XhbCppStagingInboundRepository xcsi2Repo = new XhbCppStagingInboundRepository(entityManager);

                // Find by Id - not exists
                LOG.debug("Method 3 - findid not exists");
                Optional<XhbCppStagingInboundDao> foundRecord = xcsi2Repo.findById(2);
                if (foundRecord.isEmpty()) {
                    LOG.debug("3 - No records found here");
                }

                // Find by Id - exists
                LOG.debug("Method 4 - findid - exists");
                foundRecord = xcsi2Repo.findById(7);
                if (foundRecord.isEmpty()) {
                    LOG.debug("4 - No records found here");
                }

                // Find all
                LOG.debug("Method 5 - finding all");
                List<XhbCppStagingInboundDao> records = xcsi2Repo.findAll();
                if (records.isEmpty()) {
                    LOG.debug("5 - No records found here");
                }

                // Find by named query - not exists
                LOG.debug("Method 6 - find named query - not exists");
                records = xcsi2Repo.findNextDocumentByValidationStatus(LocalDateTime.of(2021, 12, 20, 12, 30), "VS");
                if (records.isEmpty()) {
                    LOG.debug("6 - No records found here");
                }

                // Find by named query - exists
                LOG.debug("Method 7 - find named query - exists");
                records = xcsi2Repo.findNextDocumentByValidationStatus(LocalDateTime.of(2021, 12, 20, 12, 30), "NP");
                if (records.isEmpty()) {
                    LOG.debug("7 - No records found here");
                }

                // Save a new record
                LOG.debug("Method 8 - saving a new record");
                xcsi2Repo.save(getExample1XcsiToSave());

                // Update a new record
                LOG.debug("Method 9 - updating an existing record");
                foundRecord = xcsi2Repo.findById(21);
                XhbCppStagingInboundDao updatedXcsi = foundRecord.stream().findFirst().orElse(null);
                if (updatedXcsi != null) {
                    xcsi2Repo.update(getUpdatedXcsi(updatedXcsi));
                }

                // Delete a record
                LOG.debug("Method 10 - deleting a record");
                foundRecord = xcsi2Repo.findById(44);
                xcsi2Repo.delete(foundRecord);
                LOG.debug("Finished!");

                // Close the entity manager and associated factory
                entityManager.close();
                entityManagerFactory.close();

                // Method 3 -
                LOG.debug("Method 3 - begin");
            }
        }
    }

    public XhbCppStagingInboundDao saveXcsi(XhbCppStagingInboundDao xcsi) {
        XhbCppStagingInboundDao cp = new XhbCppStagingInboundDao();
        try {
            entityManager.getTransaction().begin();
            cp.setDocumentName(xcsi.getDocumentName());
            cp.setCourtCode(xcsi.getCourtCode());
            cp.setDocumentType(xcsi.getDocumentType());
            cp.setTimeLoaded(xcsi.getTimeLoaded());
            cp.setClobId(Long.valueOf(1));
            cp.setValidationStatus(xcsi.getValidationStatus());
            cp.setCreationDate(xcsi.getCreationDate());
            cp.setLastUpdateDate(xcsi.getLastUpdateDate());
            cp.setCreatedBy(xcsi.getCreatedBy());
            cp.setLastUpdatedBy(xcsi.getLastUpdatedBy());
            cp = entityManager.merge(cp);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return cp;
    }

    /**
     * Note: getTransaction methods commented out for use with JTA.
     */
    public void listXcsi() {
        try {
            @SuppressWarnings("unchecked")
            List<XhbCppStagingInboundDao> xhbCppStagingInbounds =
                entityManager.createQuery("from XhbCppStagingInbound").getResultList();
            LOG.debug("Listing docs...");
            for (XhbCppStagingInboundDao cp : xhbCppStagingInbounds) {
                LOG.debug(cp.getDocumentName());
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void updateXcsi(Integer xhbCppStagingInboundId, XhbCppStagingInboundDao xcsi) {
        try {
            entityManager.getTransaction().begin();
            XhbCppStagingInboundDao currXcsi =
                entityManager.find(XhbCppStagingInboundDao.class, xhbCppStagingInboundId);
            currXcsi.setDocumentName(xcsi.getDocumentName());
            currXcsi.setDocumentType(xcsi.getDocumentType());
            currXcsi.setCourtCode(xcsi.getCourtCode());
            currXcsi.setTimeLoaded(xcsi.getTimeLoaded());
            currXcsi.setClobId(Long.valueOf(1));
            currXcsi.setValidationStatus(xcsi.getValidationStatus());
            currXcsi.setCreationDate(xcsi.getCreationDate());
            currXcsi.setLastUpdateDate(xcsi.getLastUpdateDate());
            currXcsi.setCreatedBy(xcsi.getCreatedBy());
            currXcsi.setLastUpdatedBy(xcsi.getLastUpdatedBy());

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void deleteXcsi(Integer xhbCppStagingInboundId) {
        try {
            entityManager.getTransaction().begin();
            XhbCppStagingInboundDao xcsi = entityManager.find(XhbCppStagingInboundDao.class, xhbCppStagingInboundId);
            entityManager.remove(xcsi);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }


    /**** Example Data. ***/

    public static XhbCppStagingInboundDao getExample1XcsiToSave() {
        return getNewXhbCppStagingInboundDao("Some random doc", "453", "NP", "Scott", "Scott");
    }

    public static XhbCppStagingInboundDao getExample2XcsiToSave() {
        return getNewXhbCppStagingInboundDao("Doc no 2 please", "410", "NP", "Sheena", "Sheena");
    }

    public static XhbCppStagingInboundDao getExample1XcsiToUpdate() {
        return getNewXhbCppStagingInboundDao("July documentation - Updated doc test", "489", "VS", "JimT", "SusanH");
    }

    private static XhbCppStagingInboundDao getNewXhbCppStagingInboundDao(String documentName, String courtCode,
        String validationStatus, String createdBy, String lastUpdatedBy) {
        XhbCppStagingInboundDao ex = new XhbCppStagingInboundDao();
        ex.setDocumentName(documentName);
        ex.setDocumentType("PD");
        ex.setCourtCode(courtCode);
        ex.setClobId(Long.valueOf(1));
        ex.setTimeLoaded(LocalDateTime.now());
        ex.setValidationStatus(validationStatus);
        ex.setCreationDate(LocalDateTime.now());
        ex.setLastUpdateDate(LocalDateTime.now());
        ex.setCreatedBy(createdBy);
        ex.setLastUpdatedBy(lastUpdatedBy);
        return ex;
    }

    public static XhbCppStagingInboundDao getExample2XcsiToUpdate() {
        XhbCppStagingInboundDao ex = new XhbCppStagingInboundDao();
        ex.setDocumentName("Super cali fragilistic");
        ex.setDocumentType("PD");
        ex.setCourtCode("476");
        ex.setTimeLoaded(LocalDateTime.now());
        ex.setCreatedBy("Dina");
        ex.setLastUpdatedBy("Dina");
        return ex;
    }

    public static XhbCppStagingInboundDao getUpdatedXcsi(XhbCppStagingInboundDao ex) {
        ex.setDocumentName("Again - Updated doc via EM again");
        ex.setDocumentType("PD");
        ex.setCourtCode("407");
        ex.setValidationStatus("NP");
        ex.setLastUpdatedBy("EM_Scott3");
        return ex;
    }
}
