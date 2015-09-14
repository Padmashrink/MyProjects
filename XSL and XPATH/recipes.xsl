<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> 
<xsl:template match="/">    
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head><title>Recipes</title></head>     
<body>        
     <h2>Recipes</h2>
	 <h3>DESCRIPTION:<xsl:value-of select="//description"/></h3>
     <xsl:for-each select="collection/recipe">
	 <table border="1">
	     <tr bgcolor="silver">
		     <th>ID:<xsl:value-of select="@id"/></th>
			 <th>TITLE:<xsl:value-of select="title"/></th>
			 <th>DATE:<xsl:value-of select="date"/></th>
		 </tr>
		 <tr bgcolor="thistle">
		     <th>INGREDIENTS:</th>
		 </tr>
		 <tr bgcolor="paleGoldenRod"> 
		     <th>NAME:</th>
			 <th>AMOUNT:</th>
			 <th>UNIT:</th>
		 </tr>
		 <xsl:for-each select="ingredient">
		 <tr>
		     <td><i><font color="red"><xsl:value-of select="@name"/></font></i></td>
			 <xsl:if test="@amount">
			 <td><i><font color="red"><xsl:value-of select="@amount"/></font></i></td>
			 </xsl:if>
			 <xsl:if test="@unit">
			 <td><i><font color="red"><xsl:value-of select="@unit"/></font></i></td>
			 </xsl:if>
		 </tr>
		 <xsl:for-each select="ingredient">
		 <tr>
		     <td><font color="blue"><xsl:value-of select="@name"/></font></td>
			 <xsl:if test="@amount">
			 <td><font color="blue"><xsl:value-of select="@amount"/></font></td>
			 </xsl:if>
			 <xsl:if test="@unit">
			 <td><font color="blue"><xsl:value-of select="@unit"/></font></td>
			 </xsl:if>
		 </tr>
		 <xsl:for-each select="ingredient">
		 <tr>
		     <td><font color="green"><xsl:value-of select="@name"/></font></td>
			 <xsl:if test="@amount">
			 <td><font color="green"><xsl:value-of select="@amount"/></font></td>
			 </xsl:if>
			 <xsl:if test="@unit">
			 <td><font color="green"><xsl:value-of select="@unit"/></font></td>
			 </xsl:if>
		 </tr>
		 <xsl:for-each select="ingredient">
		 <tr>
		     <td><font color="chocolate"><xsl:value-of select="@name"/></font></td>
			 <xsl:if test="@amount">
			 <td><font color="chocolate"><xsl:value-of select="@amount"/></font></td>
			 </xsl:if>
			 <xsl:if test="@unit">
			 <td><font color="chocolate"><xsl:value-of select="@unit"/></font></td>
			 </xsl:if>
		 </tr>
		 <xsl:if test="preparation/step">
		 <xsl:for-each select="preparation/step">
		 <tr>
		     <td><b>PREPARATION:</b><xsl:value-of select="text()"/></td>
		 </tr>
		 </xsl:for-each>
		 </xsl:if>
		 </xsl:for-each>
		 <xsl:if test="preparation/step">
		 <xsl:for-each select="preparation/step">
		 <tr>
		     <td><b>PREPARATION:</b><xsl:value-of select="text()"/></td>
		 </tr>
		 </xsl:for-each>
		 </xsl:if>
		 </xsl:for-each>
		 <xsl:if test="preparation/step">
		 <xsl:for-each select="preparation/step">
		 <tr>
		     <td><b>PREPARATION:</b><xsl:value-of select="text()"/></td>
		 </tr>
		 </xsl:for-each>
		 </xsl:if>
		 </xsl:for-each>
		 <xsl:if test="preparation/step">
		 <xsl:for-each select="preparation/step">
		 <tr>
		     <td><b>PREPARATION:</b><xsl:value-of select="text()"/></td>
		 </tr>
		 </xsl:for-each>
		 </xsl:if>
		 </xsl:for-each>
		 <tr bgcolor="lightBlue">
		     <th>PREPARATION STEPS:</th>
		 </tr>
		 <xsl:for-each select="preparation/step">
		 <tr>
		     <td><xsl:value-of select="text()"/></td>
		 </tr>
         </xsl:for-each>
         <xsl:if test="comment">
             <tr bgcolor="linen">
		         <th>COMMENTS:</th>
		     </tr>	
             <tr><td><xsl:value-of select="comment"/></td></tr>	
         </xsl:if>
         <tr bgcolor="lightGreen">
		     <th>NUTRITION:</th>
		 </tr>	 
         <tr><td><b>CALORIES:</b><xsl:value-of select="nutrition/@calories"/></td></tr>	
         <tr><td><b>FAT:</b><xsl:value-of select="nutrition/@fat"/></td></tr>
         <tr><td><b>CARBOHYDRATES:</b><xsl:value-of select="nutrition/@carbohydrates"/></td></tr>
         <tr><td><b>PROTEIN:</b><xsl:value-of select="nutrition/@protein"/></td></tr>
         <xsl:if test="nutrition/@alcohol">
             <tr><td><b>ALCOHOL:</b><xsl:value-of select="nutrition/@alcohol"/></td></tr>
         </xsl:if>		 
	 </table>
	 </xsl:for-each>
</body>
</html>
</xsl:template>
</xsl:stylesheet>