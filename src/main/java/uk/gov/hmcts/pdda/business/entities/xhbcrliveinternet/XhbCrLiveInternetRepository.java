package uk.gov.hmcts.pdda.business.entities.xhbcrliveinternet;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbCrLiveInternetRepository extends AbstractRepository<XhbCrLiveInternetDao> {

    public XhbCrLiveInternetRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCrLiveInternetDao> getDaoClass() {
        return XhbCrLiveInternetDao.class;
    }

}
