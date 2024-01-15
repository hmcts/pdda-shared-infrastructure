package uk.gov.hmcts.pdda.business.services.formatting;

import javax.xml.xpath.XPathExpressionException;

public class DailyListXmlMergeUtils extends AbstractListXmlMergeUtils {

    public DailyListXmlMergeUtils() throws XPathExpressionException {
        super(new String[] {"DailyList/CourtLists/CourtList/Sittings"});
    }
}
