<?xml version="1.0" encoding="UTF-8"?>
<!--
	+       &#x00A9; Crown copyright 2003. All rights reserved.                                         +
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cs="http://www.courtservice.gov.uk/schemas/courtservice">
	<!-- Import common running List transform -->
	<xsl:include href="runningListHtml_common.xsl"/>
	<!-- Version and name Informaiton -->
	<xsl:variable name="majorVersion" select="'2'"/>
	<xsl:variable name="minorVersion" select="'0'"/>
	<xsl:variable name="stylesheet" select="'runningListHtml_cy.xsl'"/>
	<xsl:variable name="last-modified-date" select="'2005-11-01'"/>
	<!-- End Version and name Informaiton -->
	<!-- top level match -->
	<xsl:template match="/">
		<!-- Add Comments to HTML to help future maintenance -->
		<xsl:comment>
			<xsl:text>Produced by : </xsl:text>
			<xsl:value-of select="$stylesheet"/>
			<xsl:text> Version : </xsl:text>
			<xsl:value-of select="$majorVersion"/>
			<xsl:text>.</xsl:text>
			<xsl:value-of select="$minorVersion"/>
			<xsl:text>  last modified : </xsl:text>
			<xsl:value-of select="$last-modified-date"/>
		</xsl:comment>
		<xsl:comment>&#x00A9; Crown copyright 2003. All rights reserved.</xsl:comment>
		<xsl:comment>
			<xsl:text>Document Unique Id : </xsl:text>
			<xsl:value-of select="//cs:DocumentID/cs:UniqueID"/>
			<xsl:text> Document version : </xsl:text>
			<xsl:value-of select="//cs:DocumentID/cs:Version"/>
			<xsl:text> Document timestamp : </xsl:text>
			<xsl:value-of select="//cs:DocumentID/cs:TimeStamp"/>
			<xsl:text> Document stylesheet URL : </xsl:text>
			<xsl:value-of select="//cs:DocumentID/cs:XSLstylesheetURL"/>
		</xsl:comment>
		<!-- End of Add Comments to HTML to help future maintenance -->
		<!-- Call template to display running List -->
		<xsl:call-template name="DisplayRunningList">
			<xsl:with-param name="language">cy</xsl:with-param>
		</xsl:call-template>
		<div style="height=200px"/>
		<xsl:call-template name="DisplayRunningList">
			<xsl:with-param name="language">en</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
</xsl:stylesheet>
