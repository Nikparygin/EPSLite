<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="email">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4"
                                       page-height="29.7cm" page-width="21.0cm" margin="2cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="times" color="red" font-size="16pt" font-weight="bold">
                        Письмо!
                    </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        Отправитель: <xsl:value-of select="Sender"/>
                    </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        Получатель: <xsl:value-of select="Recipient"/>
                    </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        Адрес: <xsl:value-of select="Address"/>
                    </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        Организация: <xsl:value-of select="OrgName"/>
                     </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        -------------------------------------------------------------
                    </fo:block>
                    <fo:block font-family="times" color="black" font-size="11pt">
                        <xsl:value-of select="Message"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>