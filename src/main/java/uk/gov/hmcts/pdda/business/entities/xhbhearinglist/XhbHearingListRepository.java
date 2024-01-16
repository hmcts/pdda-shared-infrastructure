package uk.gov.hmcts.pdda.business.entities.xhbhearinglist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.time.LocalDateTime;
import java.util.List;



@Repository
public class XhbHearingListRepository extends AbstractRepository<XhbHearingListDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbHearingListRepository.class);

    public XhbHearingListRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbHearingListDao> getDaoClass() {
        return XhbHearingListDao.class;
    }

    /**
     * findByCourtIdAndDate.
     * 
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<XhbHearingListDao> findByCourtIdAndDate(Integer courtId, LocalDateTime startDate) {
        LOG.debug("In XhbHearingRepository.findByCourtIdAndDate");
        Query query = getEntityManager().createNamedQuery("XHB_HEARING_LIST.findByCourtIdAndDate");
        query.setParameter("courtId", courtId);
        query.setParameter("startDate", startDate);
        return query.getResultList();
    }
}
