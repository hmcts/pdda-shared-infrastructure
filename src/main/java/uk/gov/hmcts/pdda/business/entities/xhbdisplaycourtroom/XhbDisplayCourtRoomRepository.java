package uk.gov.hmcts.pdda.business.entities.xhbdisplaycourtroom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbDisplayCourtRoomRepository extends AbstractRepository<XhbDisplayCourtRoomDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbDisplayCourtRoomRepository.class);

    public XhbDisplayCourtRoomRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayCourtRoomDao> getDaoClass() {
        return XhbDisplayCourtRoomDao.class;
    }

    /**
     * findByDisplayId.
     * 
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbDisplayCourtRoomDao> findByDisplayId(Integer displayId) {
        LOG.debug("findByDisplayId()");
        Query query = getEntityManager().createNamedQuery("XHB_DISPLAY_COURT_ROOM.findByDisplayId");
        query.setParameter("displayId", displayId);
        return query.getResultList();
    }
}
