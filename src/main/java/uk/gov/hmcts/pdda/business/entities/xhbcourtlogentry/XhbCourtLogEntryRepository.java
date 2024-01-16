package uk.gov.hmcts.pdda.business.entities.xhbcourtlogentry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbCourtLogEntryRepository extends AbstractRepository<XhbCourtLogEntryDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbCourtLogEntryRepository.class);

    public XhbCourtLogEntryRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCourtLogEntryDao> getDaoClass() {
        return XhbCourtLogEntryDao.class;
    }

    /**
     * findByCaseId.
     * 
     * @param caseId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbCourtLogEntryDao> findByCaseId(final Integer caseId) {
        LOG.debug("findByCaseId()");
        Query query = getEntityManager().createNamedQuery("XHB_COURT_LOG_ENTRY.findByCaseId");
        query.setParameter("caseId", caseId);
        return query.getResultList();
    }
}
