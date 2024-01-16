package uk.gov.hmcts.pdda.business.entities.xhbcasereference;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;

import java.util.List;



@Repository
public class XhbCaseReferenceRepository extends AbstractRepository<XhbCaseReferenceDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbConfigPropRepository.class);

    public XhbCaseReferenceRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbCaseReferenceDao> getDaoClass() {
        return XhbCaseReferenceDao.class;
    }

    /**
     * findByCaseId.
     * @param caseId Integer
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<XhbCaseReferenceDao> findByCaseId(Integer caseId) {
        LOG.debug("findByCaseId()");
        Query query = getEntityManager().createNamedQuery("XHB_CASE_REFERENCE.findByCaseId");
        query.setParameter("caseId", caseId);
        return query.getResultList();
    }
}
