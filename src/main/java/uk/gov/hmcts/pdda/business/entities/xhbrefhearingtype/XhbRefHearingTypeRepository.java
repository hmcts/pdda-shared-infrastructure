package uk.gov.hmcts.pdda.business.entities.xhbrefhearingtype;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbRefHearingTypeRepository extends AbstractRepository<XhbRefHearingTypeDao> {

    public XhbRefHearingTypeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbRefHearingTypeDao> getDaoClass() {
        return XhbRefHearingTypeDao.class;
    }
}
