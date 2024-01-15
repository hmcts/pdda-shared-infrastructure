package uk.gov.hmcts.pdda.business.entities.xhbdisplay;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaylocation.XhbDisplayLocationDao;
import uk.gov.hmcts.pdda.business.entities.xhbdisplaytype.XhbDisplayTypeDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbDisplayRepositoryTest extends AbstractRepositoryTest<XhbDisplayDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbDisplayRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbDisplayRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbDisplayRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindByRotationSetIdSuccess() {
        boolean result = testFindByRotationSetId(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByRotationSetIdFailure() {
        boolean result = testFindByRotationSetId(null);
        assertTrue(result, TRUE);
    }

    private boolean testFindByRotationSetId(XhbDisplayDao dao) {
        List<XhbDisplayDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbDisplayDao> result =
            (List<XhbDisplayDao>) getClassUnderTest().findByRotationSetId(getDummyDao().getRotationSetId());
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Test
    void testFindByDisplayLocationIdSuccess() {
        boolean result = testFindByDisplayLocationId(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByDisplayLocationIdFailure() {
        boolean result = testFindByDisplayLocationId(null);
        assertTrue(result, TRUE);
    }

    private boolean testFindByDisplayLocationId(XhbDisplayDao dao) {
        List<XhbDisplayDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbDisplayDao> result =
            (List<XhbDisplayDao>) getClassUnderTest().findByDisplayLocationId(getDummyDao().getDisplayLocationId());
        assertNotNull(result, "Result is Null");
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Override
    protected XhbDisplayDao getDummyDao() {
        Integer displayId = getDummyId();
        Integer displayTypeId = Integer.valueOf(-1);
        Integer displayLocationId = Integer.valueOf(-1);
        Integer rotationSetId = Integer.valueOf(-1);
        String descriptionCode = "descriptionCode";
        String locale = "GB_en";
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        String showUnassignedYN = "N";
        XhbDisplayDao result = new XhbDisplayDao();
        result.setDisplayId(displayId);
        result.setDisplayTypeId(displayTypeId);
        result.setDisplayLocationId(displayLocationId);
        result.setRotationSetId(rotationSetId);
        result.setDescriptionCode(descriptionCode);
        result.setLocale(locale);
        result.setLastUpdateDate(lastUpdateDate);
        result.setCreationDate(creationDate);
        result.setLastUpdatedBy(lastUpdatedBy);
        result.setCreatedBy(createdBy);
        result.setVersion(version);
        result.setShowUnassignedYn(showUnassignedYN);
        displayId = result.getPrimaryKey();
        assertNotNull(displayId, NOTNULL);
        result.setXhbRotationSet(result.getXhbRotationSet());

        result.setXhbDisplayType(getDummyXhbDisplayTypeDao());
        result.setDisplayTypeId(result.getDisplayTypeId());

        result.setXhbDisplayLocation(getDummyXhbDisplayLocationDao());
        result.setDisplayLocationId(result.getDisplayLocationId());
        return new XhbDisplayDao(result);
    }

    private XhbDisplayTypeDao getDummyXhbDisplayTypeDao() {
        Integer displayTypeId = getDummyId();
        String descriptionCode = "descriptionCode";
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        XhbDisplayTypeDao result = new XhbDisplayTypeDao(displayTypeId, descriptionCode, lastUpdateDate, creationDate,
            lastUpdatedBy, createdBy, version);
        return new XhbDisplayTypeDao(result);
    }

    private XhbDisplayLocationDao getDummyXhbDisplayLocationDao() {
        Integer displayLocationId = getDummyId();
        String descriptionCode = "descriptionCode";
        Integer courtSiteId = Integer.valueOf(-1);
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        XhbDisplayLocationDao result = new XhbDisplayLocationDao(displayLocationId, descriptionCode, courtSiteId,
            lastUpdateDate, creationDate, lastUpdatedBy, createdBy, version);
        return new XhbDisplayLocationDao(result);
    }
}
