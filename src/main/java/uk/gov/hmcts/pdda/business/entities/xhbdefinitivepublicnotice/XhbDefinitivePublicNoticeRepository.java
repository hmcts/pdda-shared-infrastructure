package uk.gov.hmcts.pdda.business.entities.xhbdefinitivepublicnotice;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbDefinitivePublicNoticeRepository
    extends AbstractRepository<XhbDefinitivePublicNoticeDao> {

    public XhbDefinitivePublicNoticeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDefinitivePublicNoticeDao> getDaoClass() {
        return XhbDefinitivePublicNoticeDao.class;
    }
}
