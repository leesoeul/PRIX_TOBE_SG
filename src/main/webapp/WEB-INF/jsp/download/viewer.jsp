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
			<td align="left"><font class="drakBR" size="3">Spectral viewers</font> - for MOD<sup>i</sup> and DBond</td>
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
	<table width="95%" border="0" align="center" cellpadding="1">
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Spectral Viewer for MS/MS seach results</font><br/>&nbsp;&nbsp;- *.xml files for viewers can be downloaded from search result page (Extarct as XML: *.modi.xml and *.bond.xml for MOD<sup>i</sup> and DBond viewer, respectively). The xml file and files (spectra, DB) used in msms search should be in the same directory. Viewers were designed to run on Microsoft Windows.<br/><br/></td>
			</tr>
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Viewer Snapshots</font><br/></td>
			</tr>
			<tr>
			  <td class="grayTD" align="left">&nbsp;&nbsp;<font class="drakBR">MOD<sup>i</sup> viewer </font>( <font color="#000000">version 3.1</font> - <a href="ModeyeViewer_v3.1.zip" class="under"> download </a>)<br/></td>
			</tr>
			<tr>
			  <td align="left"><img src="/images/modi_view.gif" width="850" height="360"></td>
			</tr>
			<tr>
				<td align="center">MOD<sup>i</sup> viewer snapshot</td>
			<tr>
			<tr>
				<td align="center">&nbsp;</td>
			<tr>
			<tr>
			  <td class="grayTD" align="left">&nbsp;&nbsp;<font class="drakBR">DBond viewer </font>( <font color="#000000">version 3.01</font> - <a href="DBond Viewer v3.01.zip" class="under"> download </a>) <br/></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;( <a href="/help.jsp#BONDION" class="under">about fragment ion annotation</a> )</td>
			<tr>
			<tr>
			  <td align="left"><img src="/images/dbond_view.gif" width="850" height="360"></td>
			</tr>
			<tr>
				<td align="center">DBond viewer snapshot</td>
			<tr>
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Version update</font><br/>
			  &nbsp;&nbsp;- Version 3.01 (October 01, 2011) : <br/>
			  &nbsp;&nbsp;- Version 2.03 (June 14, 2010) : New function - Supporting to export results as *.CSV file.<br/>
			  <br/></td>
			</tr>
		</table>	
	</td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
