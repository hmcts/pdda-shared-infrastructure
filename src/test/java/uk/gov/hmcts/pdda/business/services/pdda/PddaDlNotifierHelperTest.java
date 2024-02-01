package uk.gov.hmcts.pdda.business.services.pdda;

import jakarta.persistence.EntityManager;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyCourtUtil;
import uk.gov.hmcts.DummyPdNotifierUtil;
import uk.gov.hmcts.DummyServicesUtil;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdda.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdda.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdda.business.entities.xhbpddadlnotifier.XhbPddaDlNotifierDao;
import uk.gov.hmcts.pdda.business.entities.xhbpddadlnotifier.XhbPddaDlNotifierRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * Title: PDDA Dl Notifier Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2022
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 */
@ExtendWith(EasyMockExtension.class)
class PddaDlNotifierHelperTest {

    private static final String NOT_FALSE = "Result is not False";
    private static final String NOT_TRUE = "Result is not True";
    private static final String YES = "Y";
    private static final String NO = "N";
    private static final DateTimeFormatter DL_NOTIFIER_EXECUTION_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbConfigPropRepository mockXhbConfigPropRepository;

    @Mock
    private XhbCourtRepository mockXhbCourtRepository;

    @Mock
    private XhbPddaDlNotifierRepository mockXhbPddaDlNotifierRepository;

    @Mock
    private PddaDlNotifierHelper.Notifier mockNotifier;

    @TestSubject
    private final PddaDlNotifierHelper classUnderTest =
        new PddaDlNotifierHelper(mockEntityManager, mockXhbConfigPropRepository);


    private static class Config {
        static final String PDDA_SWITCHER = "PDDA_SWITCHER";
        static final String DL_NOTIFIER_PROVIDER_URL = "DL_NOTIFIER_PROVIDER_URL";
        static final String DL_NOTIFIER_CONNECTION_FACTORY = "DL_NOTIFIER_CONNECTION_FACTORY";
        static final String DL_NOTIFIER_DESTINATION = "DL_NOTIFIER_DESTINATION";
        static final String DL_NOTIFIER_EXECUTION_TIME = "DL_NOTIFIER_EXECUTION_TIME";
    }

    @Test
    void testDefaultConstructor() {
        boolean result = true;
        new PddaDlNotifierHelper(mockEntityManager);
        assertTrue(result, NOT_TRUE);
    }
    
    @Test
    void testIsDailyNotifierRequiredSuccess() {
        // Setup
        String minuteAgo = LocalDateTime.now().minusMinutes(1).format(DL_NOTIFIER_EXECUTION_TIME_FORMAT);
        expectXhbConfigPropDao(Config.DL_NOTIFIER_EXECUTION_TIME, minuteAgo);
        expectXhbConfigPropDao(Config.PDDA_SWITCHER, "1");
        EasyMock.replay(mockXhbConfigPropRepository);
        // Run
        boolean result = classUnderTest.isDailyNotifierRequired();
        // Checks
        EasyMock.verify(mockXhbConfigPropRepository);
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testIsDailyNotifierRequiredFailure() {
        // Setup
        expectXhbConfigPropDao(Config.DL_NOTIFIER_EXECUTION_TIME, "InvalidEntry");
        EasyMock.replay(mockXhbConfigPropRepository);
        // Run
        boolean result = classUnderTest.isDailyNotifierRequired();
        // Checks
        EasyMock.verify(mockXhbConfigPropRepository);
        assertFalse(result, NOT_FALSE);
    }

    @Test
    void testRunDailyListNotifierSuccess() {
        boolean result = testRunDailyListNotifier(DlNotifierStatusEnum.RUNNING, DlNotifierStatusEnum.SUCCESS);
        assertTrue(result, NOT_TRUE);
    }

    @Test
    void testRunDailyListNotifierFailure() {
        boolean result = testRunDailyListNotifier(DlNotifierStatusEnum.RUNNING, DlNotifierStatusEnum.FAILURE);
        assertTrue(result, NOT_TRUE);
    }

    private boolean testRunDailyListNotifier(final DlNotifierStatusEnum expectedFirstSaveStatus,
        final DlNotifierStatusEnum expectedSecondSaveStatus) {
        // Add Captured Values
        Capture<XhbPddaDlNotifierDao> firstSave = EasyMock.newCapture();
        Capture<XhbPddaDlNotifierDao> secondSave = EasyMock.newCapture();
        // Setup
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayAgo = now.minusDays(1);
        String providerUrl = "providerUrl";
        expectXhbConfigPropDao(Config.DL_NOTIFIER_PROVIDER_URL, providerUrl);
        String connectionFactoryName = "connectionFactoryName";
        expectXhbConfigPropDao(Config.DL_NOTIFIER_CONNECTION_FACTORY, connectionFactoryName);
        String destinationName = "destinationName";
        expectXhbConfigPropDao(Config.DL_NOTIFIER_DESTINATION, destinationName);
        List<XhbCourtDao> xhbCourtDaoList = getXhbCourtDaoList();
        EasyMock.expect(mockXhbCourtRepository.findAll()).andReturn(xhbCourtDaoList);

        for (XhbCourtDao courtDao : xhbCourtDaoList) {
            List<XhbPddaDlNotifierDao> xhbPddaDlNotifierDaoList = DummyServicesUtil.getNewArrayList();
            XhbPddaDlNotifierDao xhbPddaDlNotifierDao =
                DummyPdNotifierUtil.getXhbPddaDlNotifierDao(courtDao.getCourtId(), dayAgo);
            // Ignore obsolete courts
            if (!YES.equals(courtDao.getObsInd())) {
                // Only find the first court, so no notifier for this court
                if (courtDao.equals(xhbCourtDaoList.get(0))) {
                    // Test the already run for the day
                    xhbPddaDlNotifierDaoList.add(xhbPddaDlNotifierDao);
                    EasyMock.expect(mockXhbPddaDlNotifierRepository
                        .findByCourtAndLastRunDate(EasyMock.isA(Integer.class), EasyMock.isA(LocalDateTime.class)))
                        .andReturn(xhbPddaDlNotifierDaoList);
                } else {
                    // Test the running for the day
                    EasyMock.expect(mockXhbPddaDlNotifierRepository
                        .findByCourtAndLastRunDate(EasyMock.isA(Integer.class), EasyMock.isA(LocalDateTime.class)))
                        .andReturn(xhbPddaDlNotifierDaoList);
                    EasyMock
                        .expect(mockXhbPddaDlNotifierRepository.update(
                            EasyMock.and(EasyMock.capture(firstSave), EasyMock.isA(XhbPddaDlNotifierDao.class))))
                        .andReturn(Optional.of(xhbPddaDlNotifierDao));
                    mockNotifier.setProviderUrl(providerUrl);
                    mockNotifier.setConnectionFactoryName(connectionFactoryName);
                    mockNotifier.setDestinationName(destinationName);
                    mockNotifier.setCourtId(courtDao.getCourtId());
                    mockNotifier.setDate(EasyMock.isA(String.class));
                    mockNotifier.run();
                    if (DlNotifierStatusEnum.FAILURE == expectedSecondSaveStatus) {
                        EasyMock.expectLastCall().andThrow(getRuntimeException());
                    }
                    EasyMock.expect(mockXhbPddaDlNotifierRepository
                        .findByCourtAndLastRunDate(EasyMock.isA(Integer.class), EasyMock.isA(LocalDateTime.class)))
                        .andReturn(xhbPddaDlNotifierDaoList);
                    EasyMock
                        .expect(mockXhbPddaDlNotifierRepository.update(
                            EasyMock.and(EasyMock.capture(secondSave), EasyMock.isA(XhbPddaDlNotifierDao.class))))
                        .andReturn(Optional.of(xhbPddaDlNotifierDao));
                }
            }
        }

        EasyMock.replay(mockXhbConfigPropRepository);
        EasyMock.replay(mockXhbCourtRepository);
        EasyMock.replay(mockXhbPddaDlNotifierRepository);
        EasyMock.replay(mockNotifier);
        // Run
        classUnderTest.runDailyListNotifier();
        // Checks
        EasyMock.verify(mockXhbConfigPropRepository);
        EasyMock.verify(mockXhbCourtRepository);
        EasyMock.verify(mockXhbPddaDlNotifierRepository);
        EasyMock.verify(mockNotifier);
        assertSame(expectedFirstSaveStatus.getStatus(), firstSave.getValue().getStatus(), "Result is not Same");
        assertSame(expectedSecondSaveStatus.getStatus(), secondSave.getValue().getStatus(), "Result is not Same");
        return true;
    }

    private void expectXhbConfigPropDao(String propertyName, String propertyValue) {
        List<XhbConfigPropDao> dummyXhbConfigPropDaoList = DummyServicesUtil.getNewArrayList();
        dummyXhbConfigPropDaoList.add(DummyServicesUtil.getXhbConfigPropDao(propertyName, propertyValue));
        EasyMock.expect(mockXhbConfigPropRepository.findByPropertyName(propertyName))
            .andReturn(dummyXhbConfigPropDaoList);
    }

    private List<XhbCourtDao> getXhbCourtDaoList() {
        List<XhbCourtDao> result = DummyServicesUtil.getNewArrayList();
        XhbCourtDao courtDao1 = DummyCourtUtil.getXhbCourtDao(Integer.valueOf(453), "Court1");
        courtDao1.setObsInd(null);
        result.add(courtDao1);
        XhbCourtDao courtDao2 = DummyCourtUtil.getXhbCourtDao(Integer.valueOf(777), "Court2");
        courtDao2.setObsInd(NO);
        result.add(courtDao2);
        XhbCourtDao courtDao3 = DummyCourtUtil.getXhbCourtDao(Integer.valueOf(999), "Court3");
        courtDao2.setObsInd(YES);
        result.add(courtDao3);
        return result;
    }

    private RuntimeException getRuntimeException() {
        return new RuntimeException();
    }
}
