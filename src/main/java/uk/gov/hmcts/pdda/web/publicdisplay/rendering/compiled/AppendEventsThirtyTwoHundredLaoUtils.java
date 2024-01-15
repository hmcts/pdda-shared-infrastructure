
package uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled;

import uk.gov.hmcts.pdda.business.vos.translation.TranslationBundle;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.nodes.BranchEventXmlNode;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.nodes.LeafEventXmlNode;

public final class AppendEventsThirtyTwoHundredLaoUtils {

    private static final String SPACE = " ";
    private static final String E30200_LAO_DATE = "E30200_LAO_Date";

    private AppendEventsThirtyTwoHundredLaoUtils() {
    }

    public static void appendLaoTypeText(String laoType, TranslationBundle documentI18n,
        StringBuilder buffer, BranchEventXmlNode laoOptions) {

        // Add LAO Type text
        if ("E30200_Case_to_be_listed_in_week_commencing".equals(laoType)) {
            appendE30200LaoDate(buffer, documentI18n,
                laoOptions,
                "Case_to_be_listed_in_week_commencing");
        } else if ("E30200_Case_to_be_listed_on".equals(laoType)) {
            appendE30200LaoDate(buffer, documentI18n,
                laoOptions, "Case_to_be_listed_on");
        } else if ("E30200_Case_to_be_listed_on_date_to_be_fixed".equals(laoType)) {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, "Case_to_be_listed_on_date_to_be_fixed"));
        } else if ("E30200_Case_to_be_listed_for_Sentence".equals(laoType)) {
            appendE30200LaoDate(buffer, documentI18n,
                laoOptions,
                "Case_to_be_listed_for_Sentence_on");
        } else if ("E30200_Case_to_be_listed_for_Further_Mention/PAD".equals(laoType)) {
            appendE30200LaoDate(buffer, documentI18n,
                laoOptions,
                "Case_to_be_listed_for_Further_Mention/PAD_on");
        } else if ("E30200_Case_to_be_listed_for_trial".equals(laoType)) {
            appendE30200LaoDate(buffer, documentI18n,
                laoOptions, "Case_to_be_listed_for_Trial_on");
        } else {
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Adjourned"));
        }
    }

    public static void appendE30200LaoDate(StringBuilder buffer, TranslationBundle documentI18n,
        BranchEventXmlNode laoOptions, String title) {
        AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, title));
        AppendUtils.append(buffer, SPACE);
        AppendAttributeFieldsExtensionUtils.appendFormattedDate(buffer,
            (LeafEventXmlNode) laoOptions.get(E30200_LAO_DATE), documentI18n);
    }

}
