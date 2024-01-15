package uk.gov.hmcts.pdda.business.entities.xhbdefendant;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbDefendantRepository extends AbstractRepository<XhbDefendantDao> {

    public XhbDefendantRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDefendantDao> getDaoClass() {
        return XhbDefendantDao.class;
    }
}
