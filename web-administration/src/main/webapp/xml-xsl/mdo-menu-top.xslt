<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

	<xsl:param name="selectedMenuItemId"/>
	
	<xsl:template match="menu">
		<div id="menu-top">
			<xsl:apply-templates/>
		</div>
	</xsl:template>

	 <xsl:template match="menu-entry">
        <ul>
			<li>
				<h2>
					<xsl:if test="@selected='true'">
						<xsl:attribute name="class">
							toggle
						</xsl:attribute>
					</xsl:if>
					<a>
						<xsl:if test="@selected='true'">
							<xsl:attribute name="class">
								toggle
							</xsl:attribute>
						</xsl:if>
						<xsl:attribute name="href">
							<xsl:value-of select="link"/><xsl:variable name="linkVar" select="link"/><xsl:if test="contains($linkVar, '?')">&amp;</xsl:if><xsl:if test="not(contains($linkVar, '?'))">?</xsl:if><xsl:value-of select="$selectedMenuItemId"/>=<xsl:value-of select="@generated-id"/>
						</xsl:attribute>
						<xsl:value-of select="label"/>
					</a>
				</h2>
	            <xsl:apply-templates select="sub-menu"/>                       
            </li>
		</ul>
	</xsl:template>
	
	 <xsl:template match="sub-menu">
        <ul>
			<li>
				<h2>
					<xsl:if test="@selected='true'">
						<xsl:attribute name="class">
							toggle
						</xsl:attribute>
					</xsl:if>
					<a>
						<xsl:if test="@selected='true'">
							<xsl:attribute name="class">
								toggle
							</xsl:attribute>
						</xsl:if>
						<xsl:attribute name="href">
							<xsl:value-of select="link"/>
							<xsl:variable name="linkVar" select="link"/>
							<xsl:if test="contains($linkVar, '?')">&amp;</xsl:if>
							<xsl:if test="not(contains($linkVar, '?'))">?</xsl:if>
							<xsl:value-of select="$selectedMenuItemId"/>=<xsl:value-of select="@generated-id"/>
						</xsl:attribute>
						<xsl:value-of select="label"/>
					</a>
				</h2>
	            <xsl:apply-templates select="item"/>
            </li>
		</ul>
	</xsl:template>
	
	<xsl:template match="item">
        <li>
        	<h2>
				<xsl:if test="@selected='true'">
					<xsl:attribute name="class">
						toggle
					</xsl:attribute>
				</xsl:if>
				<a>
					<xsl:if test="@selected='true'">
						<xsl:attribute name="class">
							toggle
						</xsl:attribute>
					</xsl:if>
        			<xsl:attribute name="href">
        				<xsl:value-of select="link"/><xsl:variable name="linkVar" select="link"/><xsl:if test="contains($linkVar, '?')">&amp;</xsl:if><xsl:if test="not(contains($linkVar, '?'))">?</xsl:if><xsl:value-of select="$selectedMenuItemId"/>=<xsl:value-of select="@generated-id"/>
        			</xsl:attribute>
        			<xsl:value-of select="label"/>
        		</a>
        	</h2>
        	<xsl:apply-templates select="sub-menu"/>                      
        </li>
	</xsl:template>

</xsl:stylesheet>