package uk.gov.hmcts.pdda.business.entities.xhbhearing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbHearingRepository extends AbstractRepository<XhbHearingDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbHearingRepository.class);

    public XhbHearingRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbHearingDao> getDaoClass() {
        return XhbHearingDao.class;
    }

    /**
     * findByCaseId.
     * 
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<XhbHearingDao> findByCaseId(Integer caseId) {
        LOG.debug("In XhbHearingRepository.findByCaseId");
        Query query = getEntityManager().createNamedQuery("XHB_HEARING.findByCaseId");
        query.setParameter("caseId", caseId);
        return query.getResultList();
    }
}
