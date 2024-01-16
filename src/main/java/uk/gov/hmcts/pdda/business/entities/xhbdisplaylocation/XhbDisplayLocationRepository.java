package uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.io.Serializable;
import java.util.List;



@Repository
public class XhbDisplayLocationRepository extends AbstractRepository<XhbDisplayLocationDao> implements Serializable  {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(XhbDisplayLocationRepository.class);

    public XhbDisplayLocationRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayLocationDao> getDaoClass() {
        return XhbDisplayLocationDao.class;
    }

    /**
     * findByVIPCourtSite.
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbDisplayLocationDao> findByVipCourtSite(Integer courtSiteId) {
        LOG.debug("findByVIPCourtSite()");
        Query query =
            getEntityManager().createNamedQuery("XHB_DISPLAY_LOCATION.findByVIPCourtSite");
        query.setParameter("courtSiteId", courtSiteId);
        return query.getResultList();
    }

    /**
     * findByCourtSite.
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbDisplayLocationDao> findByCourtSite(Integer courtSiteId) {
        LOG.debug("findByCourtSite()");
        Query query = getEntityManager().createNamedQuery("XHB_DISPLAY_LOCATION.findByCourtSite");
        query.setParameter("courtSiteId", courtSiteId);
        return query.getResultList();
    }
}
