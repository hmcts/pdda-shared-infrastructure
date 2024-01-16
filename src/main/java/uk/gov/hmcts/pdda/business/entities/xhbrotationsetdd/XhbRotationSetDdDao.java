
package uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import uk.gov.hmcts.pdda.business.entities.AbstractVersionedDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaydocument.XhbDisplayDocumentDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsDao;

import java.io.Serializable;


@Entity(name = "XHB_ROTATION_SET_DD")
@NamedQuery(name = "XHB_ROTATION_SET_DD.findByRotationSetId",
    query = "SELECT o from XHB_ROTATION_SET_DD o WHERE o.rotationSetId = :rotationSetId ")
public class XhbRotationSetDdDao extends AbstractVersionedDao implements Serializable {

    private static final long serialVersionUID = -2723700446890851397L;

    @Id
    @GeneratedValue(generator = "xhb_rotation_set_dd_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_rotation_set_dd_seq", sequenceName = "xhb_rotation_set_dd_seq",
        allocationSize = 1)
    @Column(name = "ROTATION_SET_DD_ID")
    private Integer rotationSetDdId;

    @Column(name = "ROTATION_SET_ID")
    private Integer rotationSetId;

    // Not populated and the XhbDisplayDocumentDAO member variable is available instead
    @jakarta.persistence.Transient
    private Integer displayDocumentId;

    @Column(name = "PAGE_DELAY")
    private Integer pageDelay;

    @Column(name = "ORDERING")
    private Integer ordering;

    // Non-columns
    @jakarta.persistence.Transient
    private XhbRotationSetsDao xhbRotationSets;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISPLAY_DOCUMENT_ID", nullable = false)
    private XhbDisplayDocumentDao xhbDisplayDocument;

    public XhbRotationSetDdDao() {
        super();
    }

    public XhbRotationSetDdDao(XhbRotationSetDdDao otherData) {
        this();
        setRotationSetDdId(otherData.getRotationSetDdId());
        setRotationSetId(otherData.getRotationSetId());
        setDisplayDocumentId(otherData.getDisplayDocumentId());
        setPageDelay(otherData.getPageDelay());
        setOrdering(otherData.getOrdering());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
    }

    public Integer getPrimaryKey() {
        return getRotationSetDdId();
    }

    public Integer getRotationSetDdId() {
        return rotationSetDdId;
    }

    public final void setRotationSetDdId(Integer rotationSetDdId) {
        this.rotationSetDdId = rotationSetDdId;
    }

    public Integer getDisplayDocumentId() {
        if (getXhbDisplayDocument() != null) {
            return getXhbDisplayDocument().getDisplayDocumentId();
        }
        return displayDocumentId;
    }

    public final void setDisplayDocumentId(Integer displayDocumentId) {
        this.displayDocumentId = displayDocumentId;
    }

    public Integer getPageDelay() {
        return pageDelay;
    }

    public final void setPageDelay(Integer pageDelay) {
        this.pageDelay = pageDelay;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public final void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getRotationSetId() {
        return rotationSetId;
    }

    public final void setRotationSetId(Integer rotationSetId) {
        this.rotationSetId = rotationSetId;
    }

    public XhbRotationSetsDao getXhbRotationSets() {
        return xhbRotationSets;
    }

    public final void setXhbRotationSets(XhbRotationSetsDao xhbRotationSets) {
        this.xhbRotationSets = xhbRotationSets;
    }

    public XhbDisplayDocumentDao getXhbDisplayDocument() {
        return xhbDisplayDocument;
    }

    public final void setXhbDisplayDocument(XhbDisplayDocumentDao xhbDisplayDocument) {
        this.xhbDisplayDocument = xhbDisplayDocument;
    }

}
