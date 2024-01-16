package uk.gov.hmcts.pdda.business.entities.xhbcase;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyCaseUtil;
import uk.gov.hmcts.pdda.business.entities.AbstractRepositoryTest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings("PMD.TestClassWithoutTestCases")
class XhbCaseRepositoryTest extends AbstractRepositoryTest<XhbCaseDao> {

    @Mock
    private EntityManager mockEntityManager;

    @InjectMocks
    private XhbCaseRepository classUnderTest;

    @Override
    protected EntityManager getEntityManager() {
        return mockEntityManager;
    }

    @Override
    protected XhbCaseRepository getClassUnderTest() {
        if (classUnderTest == null) {
            classUnderTest = new XhbCaseRepository(getEntityManager());
        }
        return classUnderTest;
    }

    @Override
    protected XhbCaseDao getDummyDao() {
        return DummyCaseUtil.getXhbCaseDao();
    }

}
