package uk.gov.hmcts.pdda.business.entities.xhbxmldocument;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.pdda.business.entities.AbstractRepository;

import java.time.LocalDateTime;
import java.util.List;



@Repository
public class XhbXmlDocumentRepository extends AbstractRepository<XhbXmlDocumentDao> {

    private static final Logger LOG = LoggerFactory.getLogger(XhbXmlDocumentRepository.class);

    public XhbXmlDocumentRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Class<XhbXmlDocumentDao> getDaoClass() {
        return XhbXmlDocumentDao.class;
    }

    /**
     * findListByClobId.
     * 
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<XhbXmlDocumentDao> findListByClobId(Long xmlDocumentClobId,
        LocalDateTime timeDelay) {
        LOG.debug("In XhbXmlDocumentRepository.XhbXmlDocumentRepository");
        Query query = getEntityManager().createNamedQuery("XHB_XML_DOCUMENT.findListByClobId");
        query.setParameter("xmlDocumentClobId", xmlDocumentClobId);
        query.setParameter("timeDelay", timeDelay);
        return query.getResultList();
    }
}
