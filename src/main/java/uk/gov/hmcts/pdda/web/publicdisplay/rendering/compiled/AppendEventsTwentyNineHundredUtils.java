
package uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.vos.translation.TranslationBundle;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.DefendantName;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.nodes.BranchEventXmlNode;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.nodes.LeafEventXmlNode;

import java.util.Collection;

public final class AppendEventsTwentyNineHundredUtils {

    private static final Logger LOG =
        LoggerFactory.getLogger(AppendEventsTwentyNineHundredUtils.class);

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String SEMI_COLON = ";";
    private static final String INTERPRETER_SWORN = "Interpreter_Sworn";
    private static final String WITNESS_NUMBER = "Witness_Number";
    private static final String DEFENDANT_ON_CASE_ID = "defendant_on_case_id";
    private static final String E20901_TEO_TIME = "E20901_TEO_time";
    private static final String TRIAL_TIME_ESTIMATE = "Trial_Time_Estimate:";
    private static final String DEFENCE = "Defence";
    private static final String DEFENDANT_NAME = "defendant_name";

    private AppendEventsTwentyNineHundredUtils() {
    }

    // Not sure if no longer used
    public static void appendEvent20901(StringBuilder buffer, BranchEventXmlNode node,
        TranslationBundle documentI18n) {
        BranchEventXmlNode teOptions =
            (BranchEventXmlNode) node.get("E20901_Time_Estimate_Options");
        String teoUnit = ((LeafEventXmlNode) teOptions.get("E20901_TEO_units")).getValue();

        if ("E20901_days".equals(teoUnit)) {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, TRIAL_TIME_ESTIMATE));
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer,
                ((LeafEventXmlNode) teOptions.get(E20901_TEO_TIME)).getValue());
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Day(s)"));
        } else if ("E20901_weeks".equals(teoUnit)) {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, TRIAL_TIME_ESTIMATE));
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer,
                ((LeafEventXmlNode) teOptions.get(E20901_TEO_TIME)).getValue());
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Week(s)"));
        } else if ("E20901_months".equals(teoUnit)) {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, TRIAL_TIME_ESTIMATE));
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer,
                ((LeafEventXmlNode) teOptions.get(E20901_TEO_TIME)).getValue());
            AppendUtils.append(buffer, SPACE);
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Month(s)"));
        } else {
            LOG.debug("Invalid E20901_TEO_units: " + teoUnit + " entered.");
        }
    }

    // OK
    public static void appendEvent20902(StringBuilder buffer, TranslationBundle documentI18n) {
        AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Jury_Sworn_In"));
    }

    // OK
    public static void appendEvent20903(StringBuilder buffer, BranchEventXmlNode node,
        TranslationBundle documentI18n) {
        if ("E20903_Prosecution_Opening".equals(
            ((LeafEventXmlNode) ((BranchEventXmlNode) node.get("E20903_Prosecution_Case_Options"))
                .get("E20903_PCO_Type")).getValue())) {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, "Prosecution_Opening"));
        } else {
            AppendUtils.append(buffer,
                TranslationUtils.translate(documentI18n, "Prosecution_Case"));
        }
    }

    // OK
    public static void appendEvent20904(StringBuilder buffer, BranchEventXmlNode node,
        TranslationBundle documentI18n) {
        BranchEventXmlNode wsOptions =
            (BranchEventXmlNode) node.get("E20904_Witness_Sworn_Options");
        String wsoType = ((LeafEventXmlNode) wsOptions.get("E20904_WSO_Type")).getValue();

        if ("E20904_Defendant_sworn".equals(wsoType)) {
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Defendant_Sworn"));
        } else if ("E20904_Interpreter_sworn".equals(wsoType)) {
            AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, INTERPRETER_SWORN));
        } else {
            if (!EMPTY_STRING
                .equals(((LeafEventXmlNode) wsOptions.get("E20904_WSO_Number")).getValue())) {
                AppendUtils.append(buffer,
                    TranslationUtils.translate(documentI18n, WITNESS_NUMBER));
                AppendUtils.append(buffer, SPACE);
                AppendUtils.append(buffer,
                    ((LeafEventXmlNode) wsOptions.get("E20904_WSO_Number")).getValue());
                AppendUtils.append(buffer, SPACE);
                AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, "Sworn"));
            }
        }
    }

    // OK
    public static void appendEvent20905(StringBuilder buffer, TranslationBundle documentI18n) {
        AppendUtils.append(buffer,
            TranslationUtils.translate(documentI18n, "Witness_evidence_concluded"));
    }

    // OK
    public static void appendEvent20906(StringBuilder buffer, BranchEventXmlNode node,
        TranslationBundle documentI18n, Collection<DefendantName> nameCollection) {
        AppendUtils.append(buffer, TranslationUtils.translate(documentI18n, DEFENCE));
        AppendUtils.append(buffer, SPACE);
        Integer defOnCaseId =
            Integer.valueOf(((LeafEventXmlNode) node.get(DEFENDANT_ON_CASE_ID)).getValue());
        if (!RendererUtils.isHideInPublicDisplay(defOnCaseId, nameCollection)) {
            AppendUtils.append(buffer,
                ((LeafEventXmlNode) node.get("E20906_Defence_CO_Name")).getValue());
            AppendUtils.append(buffer, SPACE);
        }

        AppendUtils.append(buffer,
            TranslationUtils.translate(documentI18n, "Case_Opened", DEFENCE));
    }

    // OK
    public static void appendEvent20907(StringBuilder buffer, TranslationBundle documentI18n) {
        AppendUtils.append(buffer,
            TranslationUtils.translate(documentI18n, "Prosecution_Closing_Speech"));
    }

    // OK
    public static void appendEvent20908(StringBuilder buffer, TranslationBundle documentI18n) {
        AppendUtils.append(buffer,
            TranslationUtils.translate(documentI18n, "Prosecution_Case_Closed"));
    }

    // OK
    public static void appendEvent20909(StringBuilder buffer, BranchEventXmlNode node,
        TranslationBundle documentI18n, Collection<DefendantName> nameCollection) {

        Integer defOnCaseId =
            Integer.valueOf(((LeafEventXmlNode) node.get(DEFENDANT_ON_CASE_ID)).getValue());
        if (!RendererUtils.isHideInPublicDisplay(defOnCaseId, nameCollection)) {
            AppendUtils.append(buffer, ((LeafEventXmlNode) node.get(DEFENDANT_NAME)).getValue());
            AppendUtils.append(buffer, SEMI_COLON);
            AppendUtils.append(buffer, SPACE);
        }
        AppendUtils.append(buffer,
            TranslationUtils.translate(documentI18n, "Defence_Closing_Speech"));
    }
}
