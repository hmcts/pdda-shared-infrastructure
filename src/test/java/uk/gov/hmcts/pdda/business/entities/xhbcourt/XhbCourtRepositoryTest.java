package uk.gov.hmcts.pdda.business.entities.xhbcourt;

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
import uk.gov.hmcts.DummyServicesUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbCourtRepositoryTest extends AbstractRepositoryTest<XhbCourtDao> {

    private static final String NOTNULL = "Result is Null";
    private static final Integer BYCRESTCOURTID = 1;
    private static final Integer BYNONOBSCRESTCOURTID = 2;
    private static final Integer BYSHORTNAME = 3;
    private static final Integer BYNONOBSSHORTNAME = 4;

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCourtRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCourtRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCourtRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindByCrestCourtIdValueSuccess() {
        boolean result = testFind(getDummyDao(), BYCRESTCOURTID);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByCrestCourtIdValueFailure() {
        boolean result = testFind(null, BYCRESTCOURTID);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNonObsoleteByCrestCourtIdValueSuccess() {
        boolean result = testFind(getDummyDao(), BYNONOBSCRESTCOURTID);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNonObsoleteByCrestCourtIdValueFailure() {
        boolean result = testFind(null, BYNONOBSCRESTCOURTID);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByShortNameValueSuccess() {
        boolean result = testFind(getDummyDao(), BYSHORTNAME);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByShortNameValueFailure() {
        boolean result = testFind(null, BYSHORTNAME);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNonObsoleteByShortNameValueSuccess() {
        boolean result = testFind(getDummyDao(), BYNONOBSSHORTNAME);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindNonObsoleteByShortNameValueFailure() {
        boolean result = testFind(null, BYNONOBSSHORTNAME);
        assertTrue(result, TRUE);
    }

    private boolean testFind(XhbCourtDao dao, Integer whichTest) {
        List<XhbCourtDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        List<XhbCourtDao> result = null;
        if (BYCRESTCOURTID.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCourtDao>) getClassUnderTest().findByCrestCourtIdValue(getDummyDao().getCrestCourtId());
        } else if (BYNONOBSCRESTCOURTID.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCourtDao>) getClassUnderTest()
                .findNonObsoleteByCrestCourtIdValue(getDummyDao().getCrestCourtId());
        } else if (BYSHORTNAME.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result = (List<XhbCourtDao>) getClassUnderTest().findByShortNameValue(getDummyDao().getShortName());
        } else if (BYNONOBSSHORTNAME.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            result =
                (List<XhbCourtDao>) getClassUnderTest().findNonObsoleteByShortNameValue(getDummyDao().getShortName());
        }
        assertNotNull(result, NOTNULL);
        if (dao != null) {
            assertSame(dao, result.get(0), SAME);
        } else {
            assertSame(0, result.size(), SAME);
        }
        return true;
    }

    @Override
    protected XhbCourtDao getDummyDao() {
        return DummyCourtUtil.getXhbCourtDao(DummyServicesUtil.getDummyId(), "Court1");
    }

}
