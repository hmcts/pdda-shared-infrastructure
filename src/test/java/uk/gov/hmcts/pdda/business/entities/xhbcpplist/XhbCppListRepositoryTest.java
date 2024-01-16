package uk.gov.hmcts.pdda.business.entities.xhbcpplist;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class XhbCppListRepositoryTest extends AbstractRepositoryTest<XhbCppListDao> {

    private static final Integer BYLISTDATE = 1;
    private static final Integer BYSTARTDATE = 2;
    private static final Integer BYCLOBID = 3;

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCppListRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCppListRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCppListRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Test
    void testFindByCourtCodeAndListTypeAndListDateSuccess() {
        boolean result = testFind(getDummyDao(), BYLISTDATE);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByCourtCodeAndListTypeAndListDateFailure() {
        boolean result = testFind(null, BYLISTDATE);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindSuccess() {
        boolean result = testFind(getDummyDao(), BYSTARTDATE);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindFailure() {
        boolean result = testFind(null, BYSTARTDATE);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByClobIdSuccess() {
        boolean result = testFind(getDummyDao(), BYCLOBID);
        assertTrue(result, TRUE);
    }

    @Test
    void testFindByClobIdFailure() {
        boolean result = testFind(null, BYCLOBID);
        assertTrue(result, TRUE);
    }

    private boolean testFind(XhbCppListDao dao, Integer whichTest) {
        List<XhbCppListDao> list = new ArrayList<>();
        if (dao != null) {
            list.add(dao);
        }
        List<XhbCppListDao> resultList = null;
        if (BYLISTDATE.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            resultList = (List<XhbCppListDao>) getClassUnderTest().findByCourtCodeAndListTypeAndListDate(
                getDummyDao().getCourtCode(), getDummyDao().getListType(), LocalDateTime.now());
        } else if (BYSTARTDATE.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            resultList = (List<XhbCppListDao>) getClassUnderTest()
                .findByCourtCodeAndListTypeAndListStartDateAndListEndDate(getDummyDao().getCourtCode(),
                    getDummyDao().getListType(), LocalDateTime.now(), LocalDateTime.now());
        } else if (BYCLOBID.equals(whichTest)) {
            Mockito.when(getEntityManager().createNamedQuery(isA(String.class))).thenReturn(mockQuery);
            Mockito.when(mockQuery.getResultList()).thenReturn(list);
            XhbCppListDao result = getClassUnderTest().findByClobId(getDummyDao().getListClobId());
            if (dao != null) {
                assertNotNull(result, "Result is Null");
                assertSame(dao, result, SAME);
            } else {
                assertNull(result, SAME);
            }
            return true;
        }
        assertNotNull(resultList, "Result is Null");
        if (dao != null) {
            assertSame(dao, resultList.get(0), SAME);
        } else {
            assertSame(0, resultList.size(), SAME);
        }
        return true;
    }

    @Override
    protected XhbCppListDao getDummyDao() {
        Integer cppListId = Double.valueOf(Math.random()).intValue();
        Integer courtCode = 453;
        String listType = "D";
        LocalDateTime timeLoaded = LocalDateTime.now();
        LocalDateTime listStartDate = LocalDateTime.now().minusMinutes(5);
        LocalDateTime listEndDate = LocalDateTime.now();
        Long listClobId = Double.valueOf(Math.random()).longValue();
        Long mergedClobId = Double.valueOf(Math.random()).longValue();
        String status = "MS";
        String errorMessage = null;
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.now().minusMinutes(1);
        String lastUpdatedBy = "Test2";
        String createdBy = "Test1";
        Integer version = Integer.valueOf(3);
        String obsInd = "N";

        XhbCppListDao result = new XhbCppListDao();
        result.setCppListId(cppListId);
        result.setCourtCode(courtCode);
        result.setListType(listType);
        result.setTimeLoaded(timeLoaded);
        result.setListStartDate(listStartDate);
        result.setListEndDate(listEndDate);
        result.setListClobId(listClobId);
        result.setMergedClobId(mergedClobId);
        result.setStatus(status);
        result.setErrorMessage(errorMessage);
        result.setObsInd(obsInd);
        result.setLastUpdateDate(lastUpdateDate);
        result.setCreationDate(creationDate);
        result.setLastUpdatedBy(lastUpdatedBy);
        result.setCreatedBy(createdBy);
        result.setVersion(version);
        cppListId = result.getPrimaryKey();
        assertNotNull(cppListId, NOTNULL);
        return new XhbCppListDao(result);
    }

}
