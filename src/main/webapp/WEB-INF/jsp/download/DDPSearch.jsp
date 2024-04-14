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
			<td align="left"><font class="drakBR" size="3">DDP Search</font> - Data-dependent scoring parameter optimization</td>
            <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
<%
	if( session.getAttribute("id") != null && !session.getAttribute("id").equals("4") )
	{
%>
          <tr>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="/modplus/history.jsp" class="menu">History</a> &nbsp; &nbsp;</td>
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
			  <td width="600" align="left">DDP search conducts a preliminary search for the spectra selected by the spectrum quality filter. Search results from the preliminary search are used to generate data-dependent scoring parameters, then the full search over the entire input spectra are conducted using the learned scoring parameters.</td>
			</tr>
			<tr>
			  <td align="left">&nbsp;</td>
			</tr>

			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Software</font><br/></td>
			</tr>
			<tr>			
				<!--td align="left">
				&nbsp;&nbsp;- <font class="blue">Oct 01, 2012: version 1.02</font> ( <a href="/request.jsp?software=MODa" class="under">Software download</a> )<br>
				&nbsp;&nbsp;- Mar 01, 2012: version 1.01 <br><br>
				</td-->
				<td align="left"><ul>
					<li>Requirement</li><br><font class="blue"> 
					DDP Search was implemented using both JAVA programming language and bash shell script, and requires both Java Running Environment (JRE) and bash shell under Linux operating system.<br> 
					If JRE is not installed, please visit <a href="http://www.oracle.com/technetwork/java/index.html" target="_blank" class="under">http://www.oracle.com/technetwork/java/index.html</a><br>
					*Dependency: MS-GF+ software <a href="https://github.com/MSGFPlus/msgfplus"> (download the latest version via GitHub) </a>
					<br><br></font>

					<li>Release Note</li><br>
					- <font class="blue"><a href="release_note_ddp.txt" class="under">Jul 26, 2018: version 1.00</font> ( <a href="/request.jsp?software=DDPSearch" class="under">Software download</a> )<br>

			   </ul></td>
			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Publication</font><br/></td>
			</tr>
			<tr>
			  <td>&nbsp;&nbsp;&nbsp;<font class="drakBR">Data-dependent scoring parameter optimization in MS-GF+ using spectrum quality filter</font><br>&nbsp;&nbsp;&nbsp;<em>Hyunjin Jo and Eunok Paek.</em><br>&nbsp;&nbsp;&nbsp;Journal of Proteome Research, 2018, XX, XXXXXX. <a href="https://pubs.acs.org/doi/10.1021/acs.jproteome.8b00415" target="_blank" class="under">[ DOI: 10.1021/acs.jproteome.8b00415 ]</a></td>
			</tr>	

		</table>	
	</td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
