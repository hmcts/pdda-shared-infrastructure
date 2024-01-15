package uk.gov.hmcts.pdda.business.entities.xhbrotationsets;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import uk.gov.hmcts.pdda.business.entities.AbstractVersionedDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PMD.LinguisticNaming")
@Entity(name = "XHB_ROTATION_SETS")
@NamedQuery(name = "XHB_ROTATION_SETS.findByCourtId",
    query = "SELECT o from XHB_ROTATION_SETS o WHERE o.courtId = :courtId ")
public class XhbRotationSetsDao extends AbstractVersionedDao implements Serializable {

    private static final long serialVersionUID = -2723700446890851397L;

    @Id
    @GeneratedValue(generator = "xhb_rotation_sets_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_rotation_sets_seq", sequenceName = "xhb_rotation_sets_seq",
        allocationSize = 1)
    @Column(name = "ROTATION_SET_ID")
    private Integer rotationSetId;

    @Column(name = "COURT_ID")
    private Integer courtId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEFAULT_YN")
    private String defaultYn;

    @jakarta.persistence.Transient
    private XhbCourtDao court;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ROTATION_SET_ID")
    private List<XhbRotationSetDdDao> xhbRotationSetDds = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ROTATION_SET_ID")
    private List<XhbDisplayDao> xhbDisplays = new ArrayList<>();

    public XhbRotationSetsDao() {
        // Default constructor
        super();
    }

    public XhbRotationSetsDao(XhbRotationSetsDao otherData) {
        this();
        setRotationSetId(otherData.getRotationSetId());
        setCourtId(otherData.getCourtId());
        setDescription(otherData.getDescription());
        setDefaultYn(otherData.getDefaultYn());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
    }

    public boolean setData(XhbRotationSetsDao otherData) {
        boolean modified = false;
        if (isModifiedDescription(otherData)) {
            setDescription(otherData.getDescription());
            modified = true;
        }
        if (isModifiedDefaultYn(otherData)) {
            setDefaultYn(otherData.getDefaultYn());
            modified = true;
        }
        if (isModifiedCreatedBy(otherData)) {
            setCreatedBy(otherData.getCreatedBy());
            modified = true;
        }
        if (isModifiedCreationDate(otherData)) {
            setCreationDate(otherData.getCreationDate());
            modified = true;
        }
        if (isModifiedLastUpdatedBy(otherData)) {
            setLastUpdatedBy(otherData.getLastUpdatedBy());
            modified = true;
        }
        if (isModifiedLastUpdateDate(otherData)) {
            setLastUpdateDate(otherData.getLastUpdateDate());
            modified = true;
        }
        if (isModifiedVersion(otherData)) {
            setVersion(otherData.getVersion());
            modified = true;
        }
        return modified;
    }

    private boolean isModifiedDescription(XhbRotationSetsDao otherData) {
        return otherData.getDescription() != null
            && !otherData.getDescription().equals(getDescription())
            || otherData.getDescription() == null && getDescription() != null;
    }

    private boolean isModifiedDefaultYn(XhbRotationSetsDao otherData) {
        return otherData.getDefaultYn() != null && !otherData.getDefaultYn().equals(getDefaultYn())
            || otherData.getDefaultYn() == null && getDefaultYn() != null;
    }

    private boolean isModifiedCreatedBy(XhbRotationSetsDao otherData) {
        return otherData.getCreatedBy() != null && !otherData.getCreatedBy().equals(getCreatedBy())
            || otherData.getCreatedBy() == null && getCreatedBy() != null;
    }

    private boolean isModifiedCreationDate(XhbRotationSetsDao otherData) {
        return otherData.getCreationDate() != null
            && !otherData.getCreationDate().equals(getCreationDate())
            || otherData.getCreationDate() == null && getCreationDate() != null;
    }

    private boolean isModifiedLastUpdatedBy(XhbRotationSetsDao otherData) {
        return otherData.getLastUpdatedBy() != null
            && !otherData.getLastUpdatedBy().equals(getLastUpdatedBy())
            || otherData.getLastUpdatedBy() == null && getLastUpdatedBy() != null;
    }

    private boolean isModifiedLastUpdateDate(XhbRotationSetsDao otherData) {
        return otherData.getLastUpdateDate() != null
            && !otherData.getLastUpdateDate().equals(getLastUpdateDate())
            || otherData.getLastUpdateDate() == null && getLastUpdateDate() != null;
    }

    private boolean isModifiedVersion(XhbRotationSetsDao otherData) {
        return otherData.getVersion() != null && !otherData.getVersion().equals(getVersion())
            || otherData.getVersion() == null && getVersion() != null;
    }

    public Integer getPrimaryKey() {
        return getRotationSetId();
    }

    public Integer getRotationSetId() {
        return rotationSetId;
    }

    public final void setRotationSetId(Integer rotationSetsId) {
        this.rotationSetId = rotationSetsId;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public final void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultYn() {
        return defaultYn;
    }

    public final void setDefaultYn(String defaultYn) {
        this.defaultYn = defaultYn;
    }

    public XhbCourtDao getCourt() {
        return court;
    }

    public final void setCourt(XhbCourtDao court) {
        this.court = court;
    }

    public List<XhbRotationSetDdDao> getXhbRotationSetDds() {
        return xhbRotationSetDds;
    }

    public final void setXhbRotationSetDds(List<XhbRotationSetDdDao> xhbRotationSetDds) {
        this.xhbRotationSetDds = xhbRotationSetDds;
    }

    public List<XhbDisplayDao> getXhbDisplays() {
        return xhbDisplays;
    }

    public final void setXhbDisplays(List<XhbDisplayDao> xhbDisplays) {
        this.xhbDisplays = xhbDisplays;
    }

}
