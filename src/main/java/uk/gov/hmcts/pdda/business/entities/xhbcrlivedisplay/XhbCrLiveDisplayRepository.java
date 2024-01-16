package uk.gov.hmcts.pdda.business.entities.xhbcrlivedisplay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbCrLiveDisplayRepository extends AbstractRepository<XhbCrLiveDisplayDao> {

    @PersistenceContext
    private EntityManager entityManager;

    public XhbCrLiveDisplayRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCrLiveDisplayDao> getDaoClass() {
        return XhbCrLiveDisplayDao.class;
    }
}
