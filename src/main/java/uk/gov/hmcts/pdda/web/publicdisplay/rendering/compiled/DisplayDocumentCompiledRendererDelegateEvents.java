
package uk.gov.hmcts.pdda.web.publicdisplay.rendering.compiled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.vos.translation.TranslationBundle;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.DefendantName;
import uk.gov.hmcts.pdda.common.publicdisplay.renderdata.nodes.BranchEventXmlNode;

import java.util.Collection;

public abstract class DisplayDocumentCompiledRendererDelegateEvents
    extends AbstractCompiledRendererDelegate {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG =
        LoggerFactory.getLogger(DisplayDocumentCompiledRendererDelegateEvents.class);

    protected static final String EMPTY_STRING = "";
    protected static final String E30200_LAO_DATE = "E30200_LAO_Date";
    protected static final String E30200_LAO_JUDGE_NAME = "E30200_LAO_Reserved_To_Judge_Name";

    protected DisplayDocumentCompiledRendererDelegateEvents() {
        super();
    }

    public void appendEvents(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("10100".equals(type)) {
            AppendEventsTenToTwentyFiveUtils.appendEvent10100(buffer, documentI18n);
        } else if ("10500".equals(type)) {
            AppendEventsTenToTwentyFiveUtils.appendEvent10500(buffer, documentI18n);
        } else if ("20502".equals(type)) {
            AppendEventsTenToTwentyFiveUtils.appendEvent20502(buffer, branchNode, documentI18n);
        } else {
            append20600Events(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20600Events(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20602".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20602(buffer, documentI18n);
        } else if ("20603".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20603(buffer, branchNode, documentI18n);
        } else if ("20604".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20604(buffer, documentI18n);
        } else if ("20605".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20605(buffer, documentI18n);
        } else if ("20606".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20606(buffer, branchNode, documentI18n,
                defendantNames);
        } else if ("20607".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20607(buffer, documentI18n);
        } else {
            append20600ExcessEvents(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20600ExcessEvents(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20608".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20608(buffer, documentI18n);
        } else if ("20609".equals(type)) {
            AppendEventsTwentySixHundredUtils.appendEvent20609(buffer, documentI18n);
        } else if ("20610".equals(type)) {
            AppendEventsTwentySixHundredTenUtils.appendEvent20610(buffer, documentI18n);
        } else if ("20611".equals(type)) {
            AppendEventsTwentySixHundredTenUtils.appendEvent20611(buffer, documentI18n);
        } else if ("20612".equals(type)) {
            AppendEventsTwentySixHundredTenUtils.appendEvent20612(buffer, documentI18n);
        } else if ("20613".equals(type)) {
            AppendEventsTwentySixHundredTenUtils.appendEvent20613(buffer, branchNode, documentI18n);
        } else {
            append20900Events(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20900Events(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20901".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20901(buffer, branchNode, documentI18n);
        } else if ("20902".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20902(buffer, documentI18n);
        } else if ("20903".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20903(buffer, branchNode, documentI18n);
        } else if ("20904".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20904(buffer, branchNode, documentI18n);
        } else if ("20905".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20905(buffer, documentI18n);
        } else {
            append20900ExcessEvents(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20900ExcessEvents(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20906".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20906(buffer, branchNode, documentI18n,
                defendantNames);
        } else if ("20907".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20907(buffer, documentI18n);
        } else if ("20908".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20908(buffer, documentI18n);
        } else if ("20909".equals(type)) {
            AppendEventsTwentyNineHundredUtils.appendEvent20909(buffer, branchNode, documentI18n,
                defendantNames);
        } else {
            append20910To20930Events(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20910To20930Events(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20910".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20910(buffer, branchNode, documentI18n,
                defendantNames);
        } else if ("20911".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20911(buffer, documentI18n);
        } else if ("20912".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20912(buffer, documentI18n);
        } else if ("20914".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20914(buffer, documentI18n);
        } else if ("20916".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20916(buffer, documentI18n);
        } else if ("20917".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20917(buffer, documentI18n);
        } else {
            append20910To20930ExcessEvents(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append20910To20930ExcessEvents(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("20918".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20918(buffer, documentI18n);
        } else if ("20919".equals(type)) {
            AppendEventsTwentyNineHundredTenUtils.appendEvent20919(buffer, documentI18n);
        } else if ("20920".equals(type)) {
            AppendEventsTwentyNineHundredExcessUtils.appendEvent20920(buffer, branchNode,
                documentI18n);
        } else if ("20931".equals(type)) {
            AppendEventsTwentyNineHundredExcessUtils.appendEvent20931(buffer, branchNode,
                documentI18n);
        } else if ("20932".equals(type)) {
            AppendEventsTwentyNineHundredExcessUtils.appendEvent20932(buffer, branchNode,
                documentI18n);
        } else if ("20935".equals(type)) {
            AppendEventsTwentyNineHundredExcessUtils.appendEvent20935(buffer, branchNode,
                documentI18n);
        } else {
            append21000To40000Events(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append21000To40000Events(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("21100".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent21100(buffer, documentI18n);
        } else if ("21200".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent21200(buffer, documentI18n);
        } else if ("21201".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent21201(buffer, documentI18n);
        } else if ("30100".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30100(buffer, branchNode,
                documentI18n);
        } else if ("30200".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30200(buffer, branchNode,
                documentI18n, defendantNames);
        } else if ("30300".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30300(buffer, documentI18n);
        } else if ("30400".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30400(buffer, documentI18n);
        } else {
            append30000To40000Events(buffer, branchNode, documentI18n, defendantNames, type);
        }
    }

    private void append30000To40000Events(StringBuilder buffer, BranchEventXmlNode branchNode,
        TranslationBundle documentI18n, Collection<DefendantName> defendantNames, String type) {
        if ("30500".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30500(buffer, documentI18n);
        } else if ("30600".equals(type)) {
            AppendEventsTwentyOneHundredToThirtyUtils.appendEvent30600(buffer, branchNode,
                documentI18n, defendantNames);
        } else if ("31000".equals(type)) {
            AppendEventsThirtyOneToFourtyUtils.appendEvent31000(buffer, branchNode, documentI18n);
        } else if ("32000".equals(type)) {
            AppendEventsThirtyOneToFourtyUtils.appendEvent32000(buffer, branchNode, documentI18n);
        } else if ("40601".equals(type)) {
            AppendEventsThirtyOneToFourtyUtils.appendEvent40601(buffer, documentI18n);
        } else if ("CPP".equals(type)) {
            AppendEventsThirtyOneToFourtyUtils.appendEventCpp(buffer, branchNode, documentI18n);
        } else {
            LOG.error("Unrecognised Event Type: " + type);
        }
    }
}
