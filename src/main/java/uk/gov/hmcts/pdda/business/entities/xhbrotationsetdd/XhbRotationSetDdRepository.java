package uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbRotationSetDdRepository extends AbstractRepository<XhbRotationSetDdDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbRotationSetDdRepository.class);

    public XhbRotationSetDdRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbRotationSetDdDao> getDaoClass() {
        return XhbRotationSetDdDao.class;
    }

    /**
     * findByRotationSetId.
     * 
     * @param rotationSetId Integer 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbRotationSetDdDao> findByRotationSetId(Integer rotationSetId) {
        LOG.debug("findByRotationSetId()");
        Query query =
            getEntityManager().createNamedQuery("XHB_ROTATION_SET_DD.findByRotationSetId");
        query.setParameter("rotationSetId", rotationSetId);
        return query.getResultList();
    }
}
