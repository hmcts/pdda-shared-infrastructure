package uk.gov.hmcts.pdda.business.entities.xhbdisplaytype;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbDisplayTypeRepository extends AbstractRepository<XhbDisplayTypeDao> {

    public XhbDisplayTypeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayTypeDao> getDaoClass() {
        return XhbDisplayTypeDao.class;
    }
}
