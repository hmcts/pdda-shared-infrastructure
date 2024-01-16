package uk.gov.hmcts.pdda.business.entities.xhbscheduledhearing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.util.List;



@Repository
public class XhbScheduledHearingRepository extends AbstractRepository<XhbScheduledHearingDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbScheduledHearingRepository.class);

    private static final String LIST_ID = "listId";
    private static final String COURT_ROOM_ID = "courtId";
    private static final String SCHEDULED_HEARING_ID = "scheduledHearingId";
    private static final String SITTING_ID = "sittingId";

    public XhbScheduledHearingRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbScheduledHearingDao> getDaoClass() {
        return XhbScheduledHearingDao.class;
    }

    /**
     * findActiveCasesInRoom.
     * 
     * @param listId Integer
     * @param courtRoomId Integer
     * @param scheduledHearingId INteger
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbScheduledHearingDao> findActiveCasesInRoom(Integer listId, Integer courtRoomId,
        Integer scheduledHearingId) {
        LOG.debug("findActiveCasesInRoom()");
        Query query =
            getEntityManager().createNamedQuery("XHB_SCHEDULED_HEARING.findActiveCasesInRoom");
        query.setParameter(COURT_ROOM_ID, courtRoomId);
        query.setParameter(LIST_ID, listId);
        query.setParameter(SCHEDULED_HEARING_ID, scheduledHearingId);
        return (List<XhbScheduledHearingDao>) query.getResultList();
    }

    /**
     * findBySittingId.
     * 
     * @param sittingId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbScheduledHearingDao> findBySittingId(Integer sittingId) {
        Query query = getEntityManager().createNamedQuery("XHB_SCHEDULED_HEARING.findBySittingId");
        query.setParameter(SITTING_ID, sittingId);
        return query.getResultList();
    }
}
