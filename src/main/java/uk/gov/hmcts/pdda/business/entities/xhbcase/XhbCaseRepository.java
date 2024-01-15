package uk.gov.hmcts.pdda.business.entities.xhbcase;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbCaseRepository extends AbstractRepository<XhbCaseDao> {

    public XhbCaseRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCaseDao> getDaoClass() {
        return XhbCaseDao.class;
    }
}
