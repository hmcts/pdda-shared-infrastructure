<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:crestformsbf="http://www.courtservice.gov.uk/schemas/courtservice/xhibit/crestformsbf" version="1.0">
	<xsl:template match="crestformsbf:CrestFormD">
		<!-- Test to see if a page break is required -->
		<xsl:if test="count(../crestformsbf:CrestFormB) &gt; 0 or count(../crestformsbf:CrestFormC) &gt; 0 or count(../crestformsbf:CrestFormD) &gt; 0 or count(../crestformsbf:CrestFormE) &gt; 0 or count(../crestformsbf:CrestFormF) &gt; 0 or count(../crestformsbf:CrestFormFMisc) &gt; 0">
			<fo:block break-before="page" line-height="1pt" space-after="1pt"> </fo:block>
		</xsl:if>
		<fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="{$Col1_Total}"/>
			<fo:table-column column-width="{$Col2_Total}"/>
			<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
				<fo:table-row>
					<fo:table-cell>
						<fo:block line-height="15pt" space-after="15pt"> </fo:block>
						<fo:block font-size="18pt" font-weight="bold">
							Court Clerk's Hearing Record (D)
						</fo:block>
						<fo:block font-size="15pt" font-weight="bold" line-height="30pt">
							Results of offences committed for sentence
						</fo:block>
					</fo:table-cell>
					<fo:table-cell border-width=".6mm">
						<fo:block>
						<fo:table table-layout="fixed" border-collapse="separate" border-color="#000000" border-style="solid" border-width=".2mm" height="20mm">
							<fo:table-column column-width="{$Col2_Inner}"/>
							<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
								<fo:table-row>
									<fo:table-cell>
										<!-- call common template to display Court Clerk Details -->
										<xsl:call-template name="CourtClerkDetails"/>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
				<fo:table-row>
					<fo:table-cell border-width=".6mm">
						<fo:block>
						<fo:table table-layout="fixed" border-collapse="separate" border-color="#000000" border-style="solid" border-width=".2mm" height="20mm">
							<fo:table-column column-width="{$Col1_Inner}"/>
							<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
								<fo:table-row>
									<fo:table-cell>
										<!-- call common template to display defendant details -->
										<xsl:call-template name="DefendantDetails"/>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell border-width=".6mm">
						<fo:block>
						<fo:table table-layout="fixed" border-collapse="separate" border-color="#000000" border-style="solid" border-width=".2mm" height="20mm">
							<fo:table-column column-width="{$Col2_Inner}"/>
							<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
								<fo:table-row>
									<fo:table-cell>
										<!-- call common template to display Case No and Defendant Details -->
										<xsl:call-template name="CaseNoDetails"/>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		</fo:block>
		<!-- apply the Offence template -->
		<xsl:apply-templates select="crestformsbf:Charges/crestformsbf:Charge/crestformsbf:Offence" mode="FormD"/>
		<fo:block space-after="8pt"/>
		<!-- Test if there are any unrelated disposals to display - if there are display title and call the template -->
		<xsl:if test="count(crestformsbf:UnrelatedDisposals/crestformsbf:Disposal) &gt; 0">
			<fo:block font-family="Times" font-size="14pt" font-weight="bold" line-height="20pt">
				Disposal(s) not related to specific offence(s)
			</fo:block>
			<fo:block>
				<xsl:call-template name="UnrelatedDisposal"/>
			</fo:block>
			<fo:block space-after="8pt"/>
		</xsl:if>
		<xsl:if test="crestformsbf:TICS">
			<fo:block>
			<fo:table table-layout="fixed">
				<fo:table-column column-width="140mm"/>
				<fo:table-column column-width="40mm"/>
				<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-family="Times" font-size="14pt" font-weight="bold" line-height="20pt">
								Number of offences taken into consideration
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block font-family="Courier" text-decoration="underline" font-size="15pt" text-align="right">
								<xsl:value-of select="crestformsbf:TICS"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
			</fo:block>
		</xsl:if>
	</xsl:template>

	<xsl:template match="crestformsbf:Offence" mode="FormD">
		<fo:block>
			<fo:leader leader-pattern="rule" space-before.optimum="2pt" space-after.optimum="2pt" color="#777777" rule-thickness="5pt" leader-length="100%"/>
		</fo:block>
		<fo:block space-after="8pt"> </fo:block>
		<fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="35mm"/>
			<fo:table-column column-width="65mm"/>
			<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Times" font-size="11pt" line-height="20pt">
							Offence No.
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block font-family="Courier" text-decoration="underline" font-size="15pt">
							<xsl:value-of select="crestformsbf:OffenceNo"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		</fo:block>
		<fo:block line-height="8pt" space-after="8pt"> </fo:block>
		<fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="35mm"/>
			<fo:table-column column-width="150mm"/>
			<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Times" font-size="11pt" line-height="20pt">
							Offence description
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block font-family="Courier" text-decoration="underline" font-size="15pt">
							<!-- check to ensure that there is an Offence -->
							<xsl:if test="crestformsbf:OffenceCode!=''">
								<xsl:value-of select="crestformsbf:OffenceCode"/>
								<xsl:text> - </xsl:text>
								<xsl:value-of select="crestformsbf:OffenceDesc"/>
							</xsl:if>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		</fo:block>
		<fo:block line-height="8pt" space-after="8pt"> </fo:block>
		<fo:block>
		<fo:table table-layout="fixed">
			<fo:table-column column-width="35mm"/>
			<fo:table-column column-width="150mm"/>
			<fo:table-body font-family="Times" font-weight="normal" font-size="11pt" line-height="20pt">
				<fo:table-row>
					<fo:table-cell>
						<fo:block font-family="Times" font-size="11pt" line-height="20pt">
							Disposal(s)
						</fo:block>
					</fo:table-cell>
					<fo:table-cell>
						<fo:block font-family="Courier" text-decoration="underline" font-size="15pt">
							<xsl:apply-templates select="crestformsbf:Disposal"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		</fo:block>
		<fo:block line-height="18pt" space-after="18pt"> </fo:block>
	</xsl:template>
</xsl:stylesheet>