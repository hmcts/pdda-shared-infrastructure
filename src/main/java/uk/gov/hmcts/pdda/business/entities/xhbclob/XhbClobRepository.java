package uk.gov.hmcts.pdda.business.entities.xhbclob;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.io.Serializable;



@Repository
public class XhbClobRepository extends AbstractRepository<XhbClobDao> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public XhbClobRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbClobDao> getDaoClass() {
        return XhbClobDao.class;
    }
}
