package uk.gov.hmcts.pdda.business.services.publicdisplay;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.framework.business.exceptions.CourtNotFoundException;
import uk.gov.hmcts.framework.exception.CsBusinessException;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsetdd.XhbRotationSetDdRepository;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsDao;
import uk.gov.hmcts.pdda.business.entities.xhbrotationsets.XhbRotationSetsRepository;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.PublicDisplayCheckedException;
import uk.gov.hmcts.pdda.business.services.publicdisplay.exceptions.RotationSetNotFoundCheckedException;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayNotifier;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.RotationSetComplexValue;
import uk.gov.hmcts.pdda.common.publicdisplay.vos.publicdisplay.RotationSetDdComplexValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("static-access")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RotationSetMaintainHelperTest {

    private static final String TEST = "Test";
    private static final String NOT_TRUE = "Result is Not True";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbRotationSetsRepository mockXhbRotationSetsRepository;

    @Mock
    private XhbRotationSetDdRepository mockXhbRotationSetDdRepository;

    @Mock
    private PublicDisplayNotifier mockPublicDisplayNotifier;

    @Mock
    private RotationSetComplexValue mockRotationSetComplexValue;

    @InjectMocks
    private final RotationSetMaintainHelper classUnderTest = new RotationSetMaintainHelper();

    @Test
    void testGetCourtsForPublicDisplay() {
        // Setup
        RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
        // Expects
        Mockito.when(mockXhbCourtRepository.findById(rotationSetComplexValue.getCourtId()))
            .thenReturn(Optional.of(DummyCourtUtil.getXhbCourtDao(-453, "Test1")));
        Mockito
            .when(
                mockXhbRotationSetsRepository.update(rotationSetComplexValue.getRotationSetsDao()))
            .thenReturn(Optional.of(rotationSetComplexValue.getRotationSetsDao()));
        // Run
        boolean result = false;
        try {
            classUnderTest.createRotationSets(rotationSetComplexValue, mockXhbCourtRepository,
                mockXhbRotationSetsRepository, mockXhbRotationSetDdRepository);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testSetDisplayDocumentsForRotationSet() {
        // Setup
        XhbRotationSetsDao xhbRotationSetDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
        List<XhbRotationSetDdDao> xhbRotationSetDds = new ArrayList<>();
        xhbRotationSetDds.add(DummyPublicDisplayUtil.getXhbRotationSetDdDao());
        xhbRotationSetDao.setXhbRotationSetDds(xhbRotationSetDds);
        RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
        // Expects
        Mockito.when(mockXhbRotationSetsRepository.findById(Mockito.isA(Long.class)))
            .thenReturn(Optional.of(xhbRotationSetDao));
        // Run
        boolean result = false;
        try {
            classUnderTest.setDisplayDocumentsForRotationSet(rotationSetComplexValue,
                mockPublicDisplayNotifier, mockXhbRotationSetsRepository,
                mockXhbRotationSetDdRepository);
            result = true;
        } catch (PublicDisplayCheckedException exception) {
            fail(exception);
        }
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testSetDisplayDocumentsForRotationSetFail() {
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            xhbRotationSetDao.setDefaultYn("Y");
            RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
            // Expects
            Mockito.when(mockXhbRotationSetsRepository.findById(Mockito.isA(Long.class)))
                .thenReturn(Optional.of(xhbRotationSetDao));
            // Run
            classUnderTest.setDisplayDocumentsForRotationSet(rotationSetComplexValue,
                mockPublicDisplayNotifier, mockXhbRotationSetsRepository,
                mockXhbRotationSetDdRepository);
        });
    }

    @Test
    void deleteRotationSet() {
        // Setup
        RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
        // Expects
        Mockito.when(mockXhbRotationSetsRepository.findById(Mockito.isA(Long.class)))
            .thenReturn(Optional.of(DummyPublicDisplayUtil.getXhbRotationSetsDao()));
        // Run
        boolean result = false;
        try {
            classUnderTest.deleteRotationSet(rotationSetComplexValue, mockXhbRotationSetsRepository,
                mockXhbRotationSetDdRepository);
            result = true;
        } catch (PublicDisplayCheckedException exception) {
            fail(exception);
        }
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void deleteRotationSetFail() {
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            xhbRotationSetDao.setDefaultYn("Y");
            RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
            // Expects
            Mockito.when(mockXhbRotationSetsRepository.findById(Mockito.isA(Long.class)))
                .thenReturn(Optional.of(xhbRotationSetDao));
            // Run
            classUnderTest.deleteRotationSet(rotationSetComplexValue, mockXhbRotationSetsRepository,
                mockXhbRotationSetDdRepository);
        });
    }

    @Test
    void deleteRotationSetNotEmptyDisplays() {
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            // Setup
            XhbRotationSetsDao xhbRotationSetDao = DummyPublicDisplayUtil.getXhbRotationSetsDao();
            List<XhbDisplayDao> xhbDisplayDaos = new ArrayList<>();
            xhbDisplayDaos.add(DummyPublicDisplayUtil.getXhbDisplayDao());
            xhbRotationSetDao.setXhbDisplays(xhbDisplayDaos);
            RotationSetComplexValue rotationSetComplexValue = getDummyRotationSetComplexValue();
            // Expects
            Mockito.when(mockXhbRotationSetsRepository.findById(Mockito.isA(Long.class)))
                .thenReturn(Optional.of(xhbRotationSetDao));
            // Run
            classUnderTest.deleteRotationSet(rotationSetComplexValue, mockXhbRotationSetsRepository,
                mockXhbRotationSetDdRepository);
        });
    }

    @Test
    void testPublicDisplayCheckedException() {
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            throw new PublicDisplayCheckedException(TEST, TEST);
        });
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            throw new PublicDisplayCheckedException(TEST, new Object[] {}, TEST);
        });
        Assertions.assertThrows(PublicDisplayCheckedException.class, () -> {
            throw new PublicDisplayCheckedException(TEST, new Object[] {}, TEST,
                new CsBusinessException());
        });
    }

    @Test
    void testWrappers() {
        Assertions.assertThrows(CourtNotFoundException.class, () -> {
            classUnderTest.createRotationSets(mockRotationSetComplexValue, mockEntityManager);
        });

        Assertions.assertThrows(RotationSetNotFoundCheckedException.class, () -> {
            classUnderTest.setDisplayDocumentsForRotationSet(mockRotationSetComplexValue,
                mockPublicDisplayNotifier, mockEntityManager);
        });

        boolean result = true;
        classUnderTest.deleteRotationSet(mockRotationSetComplexValue, mockEntityManager);
        assertTrue(result, NOT_TRUE);
    }

    private RotationSetComplexValue getDummyRotationSetComplexValue() {
        RotationSetComplexValue result = new RotationSetComplexValue();
        result.setRotationSetDao(DummyPublicDisplayUtil.getXhbRotationSetsDao());

        RotationSetDdComplexValue[] rotationSetDdComplexValuesArray =
            {getDummyRotationSetDdComplexValue(), getDummyRotationSetDdComplexValue()};
        result.setRotationSetDdComplexValues(rotationSetDdComplexValuesArray);
        return result;
    }

    private RotationSetDdComplexValue getDummyRotationSetDdComplexValue() {
        return new RotationSetDdComplexValue(DummyPublicDisplayUtil.getXhbRotationSetDdDao(),
            DummyPublicDisplayUtil.getXhbDisplayDocumentDao());
    }
}
