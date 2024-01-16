package uk.gov.hmcts.pdda.business.entities.xhbcourtroom;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbCourtRoomRepositoryTest extends AbstractRepositoryTest<XhbCourtRoomDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCourtRoomRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCourtRoomRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCourtRoomRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindByCourtSiteIdSuccess() {
        boolean result = testFindByCourtSiteId(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByCourtSiteIdFailure() {
        boolean result = testFindByCourtSiteId(null);
        assertTrue(result, TRUE);
    }

    private boolean testFindByCourtSiteId(XhbCourtRoomDao dao) {
        List<XhbCourtRoomDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbCourtRoomDao> result =
            (List<XhbCourtRoomDao>) getClassUnderTest().findByCourtSiteId(getDummyDao().getCourtSiteId());
        assertNotNull(result, NOTNULL);
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Test
    void testFindVipMultiSiteSuccess() {
        boolean result = testFindVipMultiSite(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testFindVipMultiSiteFailure() {
        boolean result = testFindVipMultiSite(null);
        assertTrue(result, TRUE);
    }

    private boolean testFindVipMultiSite(XhbCourtRoomDao dao) {
        final Integer courtId = getDummyId();
        List<XhbCourtRoomDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbCourtRoomDao> result = (List<XhbCourtRoomDao>) getClassUnderTest().findVipMultiSite(courtId);
        assertNotNull(result, NOTNULL);
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Test
    void testFindVipMNoSiteSuccess() {
        boolean result = testFindVipMNoSite(getDummyDao());
        assertTrue(result, TRUE);
    }

    @Test
    void testFindVipMNoSiteFailure() {
        boolean result = testFindVipMNoSite(null);
        assertTrue(result, TRUE);
    }

    private boolean testFindVipMNoSite(XhbCourtRoomDao dao) {
        final Integer courtId = getDummyId();
        List<XhbCourtRoomDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(list);
        List<XhbCourtRoomDao> result = (List<XhbCourtRoomDao>) getClassUnderTest().findVipMNoSite(courtId);
        assertNotNull(result, NOTNULL);
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Override
    protected XhbCourtRoomDao getDummyDao() {
        return DummyCourtUtil.getXhbCourtRoomDao();
    }

}
