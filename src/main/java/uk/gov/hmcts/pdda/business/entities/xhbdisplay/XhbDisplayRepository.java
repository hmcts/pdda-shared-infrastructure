package uk.gov.hmcts.pdda.business.entities.xhbdisplay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.io.Serializable;
import java.util.List;



@Repository
public class XhbDisplayRepository extends AbstractRepository<XhbDisplayDao> implements Serializable  {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(XhbDisplayRepository.class);

    public XhbDisplayRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayDao> getDaoClass() {
        return XhbDisplayDao.class;
    }

    /**
     * findByRotationSetId.
     * 
     * @param rotationSetId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbDisplayDao> findByRotationSetId(Integer rotationSetId) {
        LOG.debug("findByRotationSetId()");
        Query query = getEntityManager().createNamedQuery("XHB_DISPLAY.findByRotationSetId");
        query.setParameter("rotationSetId", rotationSetId);
        return query.getResultList();
    }

    /**
     * findByDisplayLocationId.
     * 
     * @param displayLocationId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbDisplayDao> findByDisplayLocationId(Integer displayLocationId) {
        LOG.debug("findByDisplayLocationId()");
        Query query = getEntityManager().createNamedQuery("XHB_DISPLAY.findByDisplayLocationId");
        query.setParameter("displayLocationId", displayLocationId);
        return query.getResultList();
    }
}
