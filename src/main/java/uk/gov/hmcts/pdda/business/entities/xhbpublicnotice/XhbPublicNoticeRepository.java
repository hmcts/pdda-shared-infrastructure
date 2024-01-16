package uk.gov.hmcts.pdda.business.entities.xhbpublicnotice;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbPublicNoticeRepository extends AbstractRepository<XhbPublicNoticeDao> {


    public XhbPublicNoticeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbPublicNoticeDao> getDaoClass() {
        return XhbPublicNoticeDao.class;
    }
}
