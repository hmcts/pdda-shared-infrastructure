package uk.gov.hmcts.pdda.business.entities.xhbblob;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;


@Repository
public class XhbBlobRepository extends AbstractRepository<XhbBlobDao> {

    public XhbBlobRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbBlobDao> getDaoClass() {
        return XhbBlobDao.class;
    }
}
