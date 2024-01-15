<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xhibit="http://www.courtservice.gov.uk/schemas/courtservice/xhibit" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
    <!-- Import the common List Distribution Letters templates -->
    <xsl:include href="letters_common_en_GB.xsl"/>
    <xsl:include href="firmListLetterFO_common.xsl"/>
    <!-- Parameter passed in to hold the current date -->
    <xsl:param name="java-date"/>
    <!-- Handle multiple letters -->
    <xsl:template match="xhibit:FirmListLetters">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <!-- Call template to set up Page -->
            <xsl:call-template name="PageSetUp"/>
            <!-- DISPLAY WELSH LETTERS -->
            <!-- Display Solicitor letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Solicitor">
                <xsl:sort select="translate(xhibit:FirmName,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">cy</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
            <!-- Display Prosecution letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Prosecution">
                <xsl:sort select="translate(xhibit:ProsecutingOrganisation/xhibit:OrganisationName,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">cy</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
            <!-- Display Defendant letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Defendant">
                <xsl:sort select="translate(xhibit:PersonalDetails/xhibit:Name/xhibit:CitizenNameSurname,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">cy</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
            <!-- DISPLAY ENGLISH LETTERS -->
            <!-- Display Solicitor letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Solicitor">
                <xsl:sort select="translate(xhibit:FirmName,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">en</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
            <!-- Display Prosecution letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Prosecution">
                <xsl:sort select="translate(xhibit:ProsecutingOrganisation/xhibit:OrganisationName,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">en</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
            <!-- Display Defendant letters in alphabetical order -->
            <xsl:for-each select="xhibit:FirmListLetter/xhibit:Recipient/xhibit:Defendant">
                <xsl:sort select="translate(xhibit:PersonalDetails/xhibit:Name/xhibit:CitizenNameSurname,' ','.')"/>
                <!-- Call template to set up Page Sequence and display the Daily List Letter Info-->
                <xsl:for-each select="../..">
                    <xsl:call-template name="DisplayFirmListLetter">
                        <xsl:with-param name="language">en</xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
        </fo:root>
    </xsl:template>
    <xsl:template match="xhibit:FirmListLetter">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <!-- Call template to set up Page -->
            <xsl:call-template name="PageSetUp"/>
            <!-- Call template to set up Page Sequence and display the Warned List Letter Info-->
            <xsl:call-template name="DisplayFirmListLetter">
                <xsl:with-param name="language">cy</xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="DisplayFirmListLetter">
                <xsl:with-param name="language">en</xsl:with-param>
            </xsl:call-template>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
