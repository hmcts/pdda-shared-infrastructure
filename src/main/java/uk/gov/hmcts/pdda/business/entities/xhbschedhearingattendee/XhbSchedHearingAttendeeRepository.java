package uk.gov.hmcts.pdda.business.entities.xhbschedhearingattendee;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbSchedHearingAttendeeRepository
    extends AbstractRepository<XhbSchedHearingAttendeeDao> {

    public XhbSchedHearingAttendeeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbSchedHearingAttendeeDao> getDaoClass() {
        return XhbSchedHearingAttendeeDao.class;
    }
}
