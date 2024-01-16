package uk.gov.hmcts.pdda.business.services.publicdisplay.datasource.query;

import jakarta.persistence.EntityManager;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.DummyPdNotifierUtil;
import uk.gov.hmcts.DummyPublicDisplayUtil;
import uk.gov.hmcts.pdda.business.entities.xhbconfiguredpublicnotice.XhbConfiguredPublicNoticeDao;
import uk.gov.hmcts.pdda.business.entities.xhbconfiguredpublicnotice.XhbConfiguredPublicNoticeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbdefinitivepublicnotice.XhbDefinitivePublicNoticeDao;
import uk.gov.hmcts.pdda.business.entities.xhbdefinitivepublicnotice.XhbDefinitivePublicNoticeRepository;
import uk.gov.hmcts.pdda.business.entities.xhbpublicnotice.XhbPublicNoticeDao;
import uk.gov.hmcts.pdda.business.entities.xhbpublicnotice.XhbPublicNoticeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>
 * Title: PublicNoticeQuery Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2023
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 */
@ExtendWith(EasyMockExtension.class)
class PublicNoticeQueryTest {

    private static final String TRUE = "Result is not True";

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private XhbConfiguredPublicNoticeRepository mockXhbConfiguredPublicNoticeRepository;

    @Mock
    private XhbPublicNoticeRepository mockXhbPublicNoticeRepository;

    @Mock
    private XhbDefinitivePublicNoticeRepository mockXhbDefinitivePublicNoticeRepository;

    @TestSubject
    private PublicNoticeQuery classUnderTest =
        new PublicNoticeQuery(mockEntityManager, mockXhbConfiguredPublicNoticeRepository, mockXhbPublicNoticeRepository,
            mockXhbDefinitivePublicNoticeRepository);

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testDefaultConstructor() {
        boolean result = false;
        try {
            classUnderTest = new PublicNoticeQuery(mockEntityManager);
            result = true;
        } catch (Exception exception) {
            fail(exception);
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoNoticesEmpty() {
        List<XhbConfiguredPublicNoticeDao> xhbConfiguredPublicNoticeDaoList = new ArrayList<>();
        boolean result = testGetDataNoNotices(xhbConfiguredPublicNoticeDaoList, Optional.empty(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoNoticesNoNotices() {
        List<XhbConfiguredPublicNoticeDao> xhbConfiguredPublicNoticeDaoList = new ArrayList<>();
        xhbConfiguredPublicNoticeDaoList.add(DummyPdNotifierUtil.getXhbConfiguredPublicNoticeDao("0"));
        boolean result = testGetDataNoNotices(xhbConfiguredPublicNoticeDaoList, Optional.empty(), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoNoticesNoDefinitiveNotices() {
        List<XhbConfiguredPublicNoticeDao> xhbConfiguredPublicNoticeDaoList = new ArrayList<>();
        xhbConfiguredPublicNoticeDaoList.add(DummyPdNotifierUtil.getXhbConfiguredPublicNoticeDao("0"));
        boolean result = testGetDataNoNotices(xhbConfiguredPublicNoticeDaoList,
            Optional.of(DummyPublicDisplayUtil.getXhbPublicNoticeDao()), Optional.empty());
        assertTrue(result, TRUE);
    }

    @Test
    void testGetDataNoNoticesSuccess() {
        List<XhbConfiguredPublicNoticeDao> xhbConfiguredPublicNoticeDaoList = new ArrayList<>();
        xhbConfiguredPublicNoticeDaoList.add(DummyPdNotifierUtil.getXhbConfiguredPublicNoticeDao("0"));
        boolean result = testGetDataNoNotices(xhbConfiguredPublicNoticeDaoList,
            Optional.of(DummyPublicDisplayUtil.getXhbPublicNoticeDao()),
            Optional.of(DummyPublicDisplayUtil.getXhbDefinitivePublicNoticeDao()));
        assertTrue(result, TRUE);
    }

    private boolean testGetDataNoNotices(List<XhbConfiguredPublicNoticeDao> xhbConfiguredPublicNoticeDaoList,
        Optional<XhbPublicNoticeDao> xhbPublicNoticeDao,
        Optional<XhbDefinitivePublicNoticeDao> xhbDefinitivePublicNoticeDao) {
        // Setup
        Integer courtRoomId = Integer.valueOf(8112);

        // Expects
        EasyMock.expect(mockXhbConfiguredPublicNoticeRepository.findActiveCourtRoomNotices(courtRoomId))
            .andReturn(xhbConfiguredPublicNoticeDaoList);
        if (!xhbConfiguredPublicNoticeDaoList.isEmpty()) {
            EasyMock.expect(mockXhbPublicNoticeRepository.findById(EasyMock.isA(Integer.class)))
                .andReturn(xhbPublicNoticeDao);
            if (xhbPublicNoticeDao.isPresent()) {
                EasyMock.expect(mockXhbDefinitivePublicNoticeRepository.findById(EasyMock.isA(Integer.class)))
                    .andReturn(xhbDefinitivePublicNoticeDao);
            }
        }
        // Replays
        EasyMock.replay(mockXhbConfiguredPublicNoticeRepository);
        if (!xhbConfiguredPublicNoticeDaoList.isEmpty()) {
            EasyMock.replay(mockXhbPublicNoticeRepository);
            if (xhbPublicNoticeDao.isPresent()) {
                EasyMock.replay(mockXhbDefinitivePublicNoticeRepository);
            }
        }

        // Run
        classUnderTest.execute(courtRoomId);

        // Checks
        EasyMock.verify(mockXhbConfiguredPublicNoticeRepository);
        if (!xhbConfiguredPublicNoticeDaoList.isEmpty()) {
            EasyMock.verify(mockXhbPublicNoticeRepository);
            if (xhbPublicNoticeDao.isPresent()) {
                EasyMock.verify(mockXhbDefinitivePublicNoticeRepository);
            }
        }
        return true;
    }
}
