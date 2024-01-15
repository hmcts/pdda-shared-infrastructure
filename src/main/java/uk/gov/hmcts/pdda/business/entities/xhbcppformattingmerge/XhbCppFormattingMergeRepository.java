package uk.gov.hmcts.pdda.business.entities.xhbcppformattingmerge;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbCppFormattingMergeRepository extends AbstractRepository<XhbCppFormattingMergeDao> {

    public XhbCppFormattingMergeRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCppFormattingMergeDao> getDaoClass() {
        return XhbCppFormattingMergeDao.class;
    }
}
