<jsp:include page="/inc/download.jsp" flush="true" />
<!-- header ³¡-->

<table width="1090" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="190" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		<!-- left menu -->
	<jsp:include page="/inc/download_menu.jsp" flush="true" />
	</td>
    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">DBond</font> - Identification of intact disulfide linkages</td>
            <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
<%
	if( session.getAttribute("id") != null && !session.getAttribute("id").equals("4") )
	{
%>
          <tr>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="/modi/history.jsp" class="menu">History</a> &nbsp; &nbsp;</td>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="/login.jsp?action=logout" class="menu">Logout</a> &nbsp; &nbsp;</td>
          </tr>
<%
	}
%>
        </table></td>
      </tr>
    </table>
	<table width="650" border="0" align="left" cellpadding="1">
			<tr>
			  <td width="600" align="left">DBond assigns disulfide bond sites in a protein via analysis of MS/MS spectra of disulfide linked peptides from protein digestion under non-reducing condition. A disulfide linked peptide produces multiple fragment ions from two peptides, and therefore, its MS/MS spectrum shows complex patterns and includes many overlapping fragment ions. DBond was designed to recognize diagnostic fragment ions resultant specifically from disulfide linkages.</td>
			</tr>
			<tr>
			  <td align="left">&nbsp;</td>
			</tr>

			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Software</font><br/></td>
			</tr>
			<tr>			
				<!--td align="left">
				&nbsp;&nbsp;- <font class="blue">Oct 01, 2012: version 3.02</font> ( <a href="/request.jsp?software=DBond" class="under">Software download</a> )<br>
				&nbsp;&nbsp;- Jan 01, 2012: version 3.01 <br><br>
				</td-->
				<td align="left"><ul>
					<li>Requirement</li><br><font class="blue"> 
					DBond was implemented using JAVA programming language, and requires Java Running Environment (JRE).<br> 
					If JRE is not installed, please visit <a href="http://www.oracle.com/technetwork/java/index.html" target="_blank" class="under">http://www.oracle.com/technetwork/java/index.html</a><br><br></font>

					<li>Release Note</li><br>
					- <font class="blue">Oct 01, 2012: version 3.02</font> ( <a href="/request.jsp?software=DBond" class="under">Software download</a> )<br>
					- Jan 01, 2012: version 3.01 <br><br>

					<li>Spectral Viewer ( <a href="DBond Viewer v3.01.zip" class="under"> download </a>)</li><br>
					To help users inspect MS/MS spectra manually, a spectral viewer is provided. The input XML file to a viewer is generated from DBond search. (*.bond.xml)<br>

			   </ul></td>
			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Publication</font><br/></td>
			</tr>
			<tr>
			  <td>&nbsp;&nbsp;&nbsp;<font class="drakBR">New algorithm for the identification of intact disulfide linkages based on fragmentation characteristics<br>&nbsp;&nbsp;&nbsp;in tandem mass spectra.</font><br>&nbsp;&nbsp;&nbsp;<em>Seonhwa Choi, Jaeho Jeong, Seungjin Na, Hyo Sun Lee, Hwa-Young Kim, Kong-Joo Lee, and Eunok Paek.</em><br>&nbsp;&nbsp;&nbsp;Journal of Proteome Research, 2010, 9, 626-635. <a href="http://www.ncbi.nlm.nih.gov/pubmed/19902913" target="_blank" class="under">[ PMID: 19902913 ]</a></td>
			</tr>	

		</table>	
	</td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
