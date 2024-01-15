<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:include href="basic_elements.xsl"/>

<!-- Declare Global Variables -->
<xsl:variable name="Event_Header_Text">Defendants NOT to attend:
<xsl:if test="event/E40703_Directions_Non_Attendees!=''">
<xsl:for-each select="event/E40703_Directions_Non_Attendees/E40703_Defendant_List">
<xsl:if test="./E40703_Defendant_Name!=''">
 - <xsl:value-of select="./E40703_Defendant_Name"/> 
	
</xsl:if>

</xsl:for-each></xsl:if></xsl:variable><xsl:template match="event"><xsl:call-template name="BasicElements"/></xsl:template></xsl:stylesheet>
