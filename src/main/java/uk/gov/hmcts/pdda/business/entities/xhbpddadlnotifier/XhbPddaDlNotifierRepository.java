package uk.gov.hmcts.pdda.business.entities.xhbpddadlnotifier;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.time.LocalDateTime;
import java.util.List;



@Repository
public class XhbPddaDlNotifierRepository extends AbstractRepository<XhbPddaDlNotifierDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbPddaDlNotifierRepository.class);
    private static final String COURT_ID = "courtId";
    private static final String LAST_RUN_DATE = "lastRunDate";

    public XhbPddaDlNotifierRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbPddaDlNotifierDao> getDaoClass() {
        return XhbPddaDlNotifierDao.class;
    }

    /**
     * findByCourtAndLastRunDate.
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbPddaDlNotifierDao> findByCourtAndLastRunDate(Integer courtId,
        LocalDateTime lastRunDate) {
        LOG.debug("findByCourtAndLastRunDate()");
        Query query =
            getEntityManager().createNamedQuery("XHB_PDDA_DL_NOTIFIER.findByCourtAndLastRunDate");
        query.setParameter(COURT_ID, courtId);
        query.setParameter(LAST_RUN_DATE, lastRunDate);
        return query.getResultList();
    }
}
