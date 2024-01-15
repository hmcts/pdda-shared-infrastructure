package uk.gov.hmcts.pdda.business.entities.xhbcourtlogeventdesc;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbCourtLogEventDescRepository extends AbstractRepository<XhbCourtLogEventDescDao> {

    public XhbCourtLogEventDescRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCourtLogEventDescDao> getDaoClass() {
        return XhbCourtLogEventDescDao.class;
    }
}
