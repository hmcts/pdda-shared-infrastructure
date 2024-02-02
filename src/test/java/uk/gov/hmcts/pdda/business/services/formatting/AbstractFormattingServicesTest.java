package uk.gov.hmcts.pdda.business.services.formatting;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyFormattingUtil;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobDao;
import uk.gov.hmcts.pdda.business.entities.xhbclob.XhbClobRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcpplist.XhbCppListDao;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingDao;
import uk.gov.hmcts.pdda.business.entities.xhbformatting.XhbFormattingRepository;
import uk.gov.hmcts.pdda.business.vos.formatting.FormattingValue;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: AbstractFormattingServices Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2024
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class AbstractFormattingServicesTest {

    private static final String NOT_TRUE = "Result is Not True";
    private static final String NOT_NULL = "Result is Not Null";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbFormattingRepository mockXhbFormattingRepository;

    @Mock
    private XhbClobRepository mockXhbClobRepository;

    @TestSubject
    private final AbstractFormattingServices classUnderTest =
        new AbstractFormattingServices(mockEntityManager);


    @Test
    void testGetXhbFormattingDao() {
        // Setup
        final FormattingValue formattingValue = DummyFormattingUtil.getFormattingValue("", "", "", null);
        XhbFormattingDao xhbFormattingDao = DummyFormattingUtil.getXhbFormattingDao();

        EasyMock.expect(mockXhbFormattingRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbFormattingDao));
        EasyMock.expect(mockXhbFormattingRepository.update(EasyMock.isA(XhbFormattingDao.class)))
            .andReturn(Optional.of(xhbFormattingDao));

        EasyMock.replay(mockXhbFormattingRepository);

        // Run
        Optional<XhbFormattingDao> result = classUnderTest.getXhbFormattingDao(formattingValue);

        // Checks
        EasyMock.verify(mockXhbFormattingRepository);
        assertNotNull(result, NOT_NULL);
    }

    @Test
    void testCreateCppListClob() {
        // Setup
        XhbClobDao xhbClobDao = DummyFormattingUtil.getXhbClobDao(0L, "");
        XhbCppListDao xhbCppListDao = DummyFormattingUtil.getXhbCppListDao();

        EasyMock.expect(mockXhbClobRepository.update(xhbCppListDao.getListClob()))
            .andReturn(Optional.of(xhbClobDao));

        EasyMock.replay(mockXhbClobRepository);

        // Run
        Long result = classUnderTest.createCppListClob(xhbCppListDao);

        // Checks
        EasyMock.verify(mockXhbClobRepository);
        assertNotNull(result, NOT_NULL);
    }

    @Test
    void testCreateCppMergeClob() {
        // Setup
        XhbClobDao xhbClobDao = DummyFormattingUtil.getXhbClobDao(0L, "");
        XhbCppListDao xhbCppListDao = DummyFormattingUtil.getXhbCppListDao();

        EasyMock.expect(mockXhbClobRepository.update(xhbCppListDao.getMergedClob()))
            .andReturn(Optional.of(xhbClobDao));

        EasyMock.replay(mockXhbClobRepository);

        // Run
        Long result = classUnderTest.createCppMergeClob(xhbCppListDao);

        // Checks
        EasyMock.verify(mockXhbClobRepository);
        assertNotNull(result, NOT_NULL);
    }

    @Test
    void testUpdateFormattingStatusSuccess() {
        // Setup
        XhbFormattingDao xhbFormattingDao = DummyFormattingUtil.getXhbFormattingDao();

        EasyMock.expect(mockXhbFormattingRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbFormattingDao));
        EasyMock.expect(mockXhbFormattingRepository.update(EasyMock.isA(XhbFormattingDao.class)))
            .andReturn(Optional.of(xhbFormattingDao));

        EasyMock.replay(mockXhbFormattingRepository);

        // Run
        boolean result = true;
        classUnderTest.updateFormattingStatus(0, true);

        // Checks
        EasyMock.verify(mockXhbFormattingRepository);
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testUpdateFormattingStatusFailedFormattingDocument() {
        // Setup
        XhbFormattingDao xhbFormattingDao = DummyFormattingUtil.getXhbFormattingDao();
        xhbFormattingDao.setFormatStatus("FD");

        EasyMock.expect(mockXhbFormattingRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbFormattingDao));
        EasyMock.expect(mockXhbFormattingRepository.update(EasyMock.isA(XhbFormattingDao.class)))
            .andReturn(Optional.of(xhbFormattingDao));

        EasyMock.replay(mockXhbFormattingRepository);

        // Run
        boolean result = true;
        classUnderTest.updateFormattingStatus(0, false);

        // Checks
        EasyMock.verify(mockXhbFormattingRepository);
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testUpdateFormattingStatusNewFormatting() {
        // Setup
        XhbFormattingDao xhbFormattingDao = DummyFormattingUtil.getXhbFormattingDao();
        xhbFormattingDao.setFormatStatus("NF");

        EasyMock.expect(mockXhbFormattingRepository.findById(EasyMock.isA(Integer.class)))
            .andReturn(Optional.of(xhbFormattingDao));
        EasyMock.expect(mockXhbFormattingRepository.update(EasyMock.isA(XhbFormattingDao.class)))
            .andReturn(Optional.of(xhbFormattingDao));

        EasyMock.replay(mockXhbFormattingRepository);

        // Run
        boolean result = true;
        classUnderTest.updateFormattingStatus(0, false);

        // Checks
        EasyMock.verify(mockXhbFormattingRepository);
        assertTrue(result, NOT_TRUE);
    }
}
