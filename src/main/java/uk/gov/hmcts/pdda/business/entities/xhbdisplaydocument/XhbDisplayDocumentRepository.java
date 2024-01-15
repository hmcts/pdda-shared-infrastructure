package uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;



@Repository
public class XhbDisplayDocumentRepository extends AbstractRepository<XhbDisplayDocumentDao> {

    public XhbDisplayDocumentRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbDisplayDocumentDao> getDaoClass() {
        return XhbDisplayDocumentDao.class;
    }
}
