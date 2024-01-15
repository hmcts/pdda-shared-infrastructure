package uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.vos.translation.TranslationBundle;
import uk.gov.hmcts.pdda.web.publicdisplay.types.document.DisplayDocument;

import java.util.Date;
import java.util.Iterator;

public class JuryCurrentStatusCompiledRendererDelegate
    extends DisplayDocumentCompiledRendererDelegate {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG =
        LoggerFactory.getLogger(JuryCurrentStatusCompiledRendererDelegate.class);

    /**
     * Append the html for the display document.
     * 
     * @param buffer the buffer to append to
     * @param displayDocument the document containing the information to render
     */
    @Override
    protected void appendDisplayDocumentHtml(StringBuilder buffer, DisplayDocument displayDocument,
        TranslationBundle documentI18n, Date date) {
        LOG.debug("appendDisplayDocumentHtml()");

        String heading = TranslationUtils.translate(documentI18n, "Jury_Current_Status");
        AppendFieldsUtils.appendHtmlHeader(buffer, displayDocument, documentI18n, date, "banner",
            heading);

        if (RendererUtils.isEmpty(displayDocument)) {
            AppendAttributeFieldsUtils.appendNoInformation(buffer, documentI18n);
        } else {
            AppendFieldsUtils.appendTableHeaders(buffer);
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Court", "10");
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Judge", "15");
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Name", "35");
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Case_No", "15");
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Hearing_Type", "15");
            AppendFieldsUtils.appendTableData(buffer, documentI18n, "Not_Before", "10");
            AppendFieldsUtils.appendTableHeadersEnd(buffer);

            String rowType = DocumentUtils.getRowType(null);
            boolean restrictionsApplyToThisList = false;

            Iterator<?> items = AttributesUtils.getTable(displayDocument).iterator();
            while (items.hasNext()) {
                final Object item = items.next();

                AppendUtils.append(buffer, "<tr class=\"");
                AppendUtils.append(buffer, rowType);
                AppendUtils.appendln(buffer, "\">");

                AppendAttributeFieldsExtensionUtils.appendCourtRoomNameOrUnassigned(buffer,
                    documentI18n, item);
                AppendAttributeFieldsUtils.appendJudgeNameRestrictedWidth(buffer,
                    AttributesUtils.getJudgeName(documentI18n, item));
                AppendAttributeFieldsExtensionUtils.appendDefendantsOrTitle(buffer, documentI18n,
                    item);
                AppendAttributeFieldsUtils.appendCaseNumber(buffer, item);
                AppendAttributeFieldsUtils.appendHearingDescription(buffer,
                    AttributesUtils.getHearingDescription(documentI18n, item));
                AppendAttributeFieldsUtils.appendNotBeforeTime(buffer,
                    DateUtils.getNotBeforeTimeAsString(item));

                AppendUtils.appendln(buffer, TABLEROWEND);

                AppendAttributeFieldsExtensionUtils.appendDefendantOverspill(buffer, item, rowType);

                // Update loop variables
                rowType = DocumentUtils.getRowType(rowType);
                if (RendererUtils.isReportingRestricted(item)) {
                    restrictionsApplyToThisList = true;
                }
            }

            AppendUtils.appendln(buffer, TABLEBODYEND);
            AppendUtils.appendln(buffer, TABLEEND);
            AppendUtils.appendln(buffer, DIVEND);

            AppendFieldsUtils.appendShowRestrictions(buffer, documentI18n,
                restrictionsApplyToThisList);
        }

        AppendUtils.appendFooter(buffer);
    }
}
