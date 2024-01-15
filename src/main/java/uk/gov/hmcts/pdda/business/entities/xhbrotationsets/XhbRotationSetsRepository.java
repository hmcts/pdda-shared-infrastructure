package uk.gov.hmcts.pdda.business.entities.xhbrotationsets;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbRotationSetsRepository extends AbstractRepository<XhbRotationSetsDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbRotationSetsRepository.class);

    public XhbRotationSetsRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbRotationSetsDao> getDaoClass() {
        return XhbRotationSetsDao.class;
    }

    /**
     * findByCourtId.
     * 
     * @param courtId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbRotationSetsDao> findByCourtId(Integer courtId) {
        LOG.debug("findByCourtId()");
        Query query = getEntityManager().createNamedQuery("XHB_ROTATION_SETS.findByCourtId");
        query.setParameter("courtId", courtId);
        return query.getResultList();
    }
}
