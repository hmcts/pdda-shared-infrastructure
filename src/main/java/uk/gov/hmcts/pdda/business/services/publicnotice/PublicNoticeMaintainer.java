package uk.gov.hmcts.pdda.business.services.publicnotice;

import uk.gov.hmcts.pdda.business.entities.PddaEntityHelper;
import uk.gov.hmcts.pdda.business.entities.xhbconfiguredpublicnotice.XhbConfiguredPublicNoticeDao;
import uk.gov.hmcts.pdda.business.vos.services.publicnotice.DefinitivePublicNoticeStatusValue;
import uk.gov.hmcts.pdda.business.vos.services.publicnotice.DisplayablePublicNoticeValue;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title: PublicNoticeMaintainer.
 * </p>
 * <p>
 * Description: A helper class to manipulate DisplayablePublicNoticeValue's so that they can be
 * written into the database with necessary optimistic lock checking.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Electronic Data Systems
 * </p>
 * 
 * @author Rakesh Lakhani
 * @version $Id: PublicNoticeMaintainer.java,v 1.1 2005/02/15 14:58:36 sz0t7n Exp $
 */
public final class PublicNoticeMaintainer {
    
    private static final String PN_ACTIVE = "1";
    private static final String PN_INACTIVE = "0";
    private static final String INVALID_PN_FOR_COURTROOM = "publicnotice.invalid_pn_for_courtroom";
    
    /**
     * Empty private constructor so that class can not be instantiated.
     */
    private PublicNoticeMaintainer() {
        // private constructor
    }

    public static void updateIsActive(DisplayablePublicNoticeValue value) {
        Optional<XhbConfiguredPublicNoticeDao> basicValue = PddaEntityHelper.xcpnFindByPrimaryKey(value.getId());
        if (basicValue.isPresent()) {
            basicValue.get()
                .setIsActive(value.isActive() ? PN_ACTIVE : PN_INACTIVE);

            // Set the version for optimistic lock checking.
            basicValue.get().setVersion(value.getVersion());

            PddaEntityHelper.cpnUpdate(basicValue.get());
        }
    }

    public static void updateActiveStatus(Integer courtRoomId, DefinitivePublicNoticeStatusValue value,
        boolean checkOptimisticLock) {
        // DisplayablePublicNoticeValue value)
        List<XhbConfiguredPublicNoticeDao> basicValues =
            PddaEntityHelper.xcpnFindByDefinitivePnCourtRoomValue(courtRoomId, value.getDefinitivePublicNoticeId());
        if (basicValues.isEmpty()) {
            throw new PublicNoticeException(INVALID_PN_FOR_COURTROOM, "Cannot find configuredPN with XhbCourtRoomId "
                + courtRoomId + " & definitivePNId" + value.getDefinitivePublicNoticeId());
        }
        XhbConfiguredPublicNoticeDao basicValue = basicValues.get(0);
        basicValue
            .setIsActive(value.isActive() ? PN_ACTIVE : PN_INACTIVE);

        if (checkOptimisticLock) {
            // Set the version for optimistic lock checking.
            basicValue.setVersion(value.getVersion());
        }

        PddaEntityHelper.cpnUpdate(basicValue);
    }

}
