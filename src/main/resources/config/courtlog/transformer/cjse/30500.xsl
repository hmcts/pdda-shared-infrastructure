<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes"/>
    <xsl:include href="basic_elements.xsl"/>
    <!-- Declare Global Variables -->
    <xsl:variable name="Event_Header_Text"> 
        <xsl:text>Hearing finished</xsl:text>
    </xsl:variable>
    <xsl:template match="event">
        <xsl:call-template name="BasicElements"/>
    </xsl:template>
</xsl:stylesheet>