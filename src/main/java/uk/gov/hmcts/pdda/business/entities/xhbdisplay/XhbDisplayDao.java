package uk.gov.hmcts.pdda.business.entities.xhbdisplay;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import uk.gov.hmcts.pdda.business.entities.AbstractVersionedDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourtroom.XhbCourtRoomDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaytype.XhbDisplayTypeDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "XHB_DISPLAY")
@NamedQuery(name = "XHB_DISPLAY.findByRotationSetId",
    query = "SELECT o from XHB_DISPLAY o WHERE o.rotationSetId = :rotationSetId ")
@NamedQuery(name = "XHB_DISPLAY.findByDisplayLocationId",
    query = "SELECT o from XHB_DISPLAY o WHERE o.displayLocationId = :displayLocationId ")
public class XhbDisplayDao extends AbstractVersionedDao implements Serializable {

    private static final long serialVersionUID = -2723700446890851397L;

    @Id
    @GeneratedValue(generator = "xhb_display_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "xhb_display_seq", sequenceName = "xhb_display_seq",
        allocationSize = 1)
    @Column(name = "DISPLAY_ID")
    private Integer displayId;

    @Column(name = "DISPLAY_TYPE_ID", nullable = false)
    private Integer displayTypeId;

    @Column(name = "DISPLAY_LOCATION_ID", nullable = false)
    private Integer displayLocationId;

    @Column(name = "ROTATION_SET_ID")
    private Integer rotationSetId;

    @Column(name = "DESCRIPTION_CODE")
    private String descriptionCode;

    @Column(name = "LOCALE")
    private String locale;

    @Column(name = "SHOW_UNASSIGNED_YN")
    private String showUnassignedYn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISPLAY_TYPE_ID", insertable = false, updatable = false)
    private XhbDisplayTypeDao xhbDisplayType;

    @jakarta.persistence.Transient
    private XhbRotationSetsDao xhbRotationSet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISPLAY_LOCATION_ID", insertable = false, updatable = false)
    private XhbDisplayLocationDao xhbDisplayLocation;

    @ManyToMany
    @JoinTable(name = "XHB_DISPLAY_COURT_ROOM", joinColumns = @JoinColumn(name = "DISPLAY_ID"),
        inverseJoinColumns = @JoinColumn(name = "COURT_ROOM_ID"))
    private List<XhbCourtRoomDao> xhbCourtRooms = new ArrayList<>();

    public XhbDisplayDao() {
        super();
    }

    public XhbDisplayDao(XhbDisplayDao otherData) {
        this();
        setDisplayId(otherData.getDisplayId());
        setDisplayTypeId(otherData.getDisplayTypeId());
        setDisplayLocationId(otherData.getDisplayLocationId());
        setRotationSetId(otherData.getRotationSetId());
        setDescriptionCode(otherData.getDescriptionCode());
        setLocale(otherData.getLocale());
        setLastUpdateDate(otherData.getLastUpdateDate());
        setCreationDate(otherData.getCreationDate());
        setLastUpdatedBy(otherData.getLastUpdatedBy());
        setCreatedBy(otherData.getCreatedBy());
        setVersion(otherData.getVersion());
        setShowUnassignedYn(otherData.getShowUnassignedYn());
    }

    public Integer getPrimaryKey() {
        return getDisplayId();
    }

    public Integer getDisplayId() {
        return displayId;
    }

    public final void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public Integer getDisplayTypeId() {
        if (getXhbDisplayType() != null) {
            return getXhbDisplayType().getDisplayTypeId();
        }
        return displayTypeId;
    }

    public final void setDisplayTypeId(Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public Integer getDisplayLocationId() {
        if (getXhbDisplayLocation() != null) {
            return getXhbDisplayLocation().getDisplayLocationId();
        }
        return displayLocationId;
    }

    public final void setDisplayLocationId(Integer displayLocationId) {
        this.displayLocationId = displayLocationId;
    }

    public Integer getRotationSetId() {
        return rotationSetId;
    }

    public final void setRotationSetId(Integer rotationSetId) {
        this.rotationSetId = rotationSetId;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public final void setDescriptionCode(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }

    public String getLocale() {
        return locale;
    }

    public final void setLocale(String locale) {
        this.locale = locale;
    }

    public String getShowUnassignedYn() {
        return showUnassignedYn;
    }

    public final void setShowUnassignedYn(String showUnassignedYn) {
        this.showUnassignedYn = showUnassignedYn;
    }

    public XhbDisplayTypeDao getXhbDisplayType() {
        return xhbDisplayType;
    }

    public final void setXhbDisplayType(XhbDisplayTypeDao xhbDisplayType) {
        this.xhbDisplayType = xhbDisplayType;
    }

    public XhbRotationSetsDao getXhbRotationSet() {
        return xhbRotationSet;
    }

    public final void setXhbRotationSet(XhbRotationSetsDao xhbRotationSet) {
        this.xhbRotationSet = xhbRotationSet;
    }

    public XhbDisplayLocationDao getXhbDisplayLocation() {
        return xhbDisplayLocation;
    }

    public final void setXhbDisplayLocation(XhbDisplayLocationDao xhbDisplayLocation) {
        this.xhbDisplayLocation = xhbDisplayLocation;
    }

    public List<XhbCourtRoomDao> getXhbCourtRooms() {
        return xhbCourtRooms;
    }

    public final void setXhbCourtRooms(List<XhbCourtRoomDao> xhbCourtRooms) {
        this.xhbCourtRooms = xhbCourtRooms;
    }

}
