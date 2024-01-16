package uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbDefendantOnCaseRepository extends AbstractRepository<XhbDefendantOnCaseDao> {

    public XhbDefendantOnCaseRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDefendantOnCaseDao> getDaoClass() {
        return XhbDefendantOnCaseDao.class;
    }
}
