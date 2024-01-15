package uk.gov.hmcts.pdda.business.entities.xhbdefendantoncase;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyDefendantUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("PMD.TestClassWithoutTestCases")
class XhbDefendantOnCaseRepositoryTest extends AbstractRepositoryTest<XhbDefendantOnCaseDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbDefendantOnCaseRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbDefendantOnCaseRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbDefendantOnCaseRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Override
    protected XhbDefendantOnCaseDao getDummyDao() {
        return DummyDefendantUtil.getXhbDefendantOnCaseDao();
    }

}
