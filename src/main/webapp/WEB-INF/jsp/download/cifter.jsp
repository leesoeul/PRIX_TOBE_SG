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
			<td align="left"><font class="drakBR" size="3">CIFTER</font> - Charge State Determination ( <a href="cifter.zip" class="under">Software download</a> )</td>
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
			<!--tr>
			  <td class="grayTD" align="left">&nbsp;<img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> CIFTER is a tool for determining charge states of low-resolution MS/MS spectra.</font>( <a href="cifter.zip" class="under">Software download</a> )<br></td>
			</tr-->
			<tr>
			  <td width="600" align="left">Low-resolution mass spectrometers cannot completely resolve isotopes of multiply-charged peptides and therefore cannot reliably determine their charge states and masses. Instead, every possible charge state, commonly +2 and +3, is assumed to calculate its mass and repetitive database searches are required for each hypothetical mass. This greatly increases the overall time needed for searching candidate peptides, and also requires additional efforts to differentiate the correct match from false positives. To alleviate these problems, CIFTER is proposed to differentiate charge states of peptides obtained from low-resolution mass spectrometers.</td>
			</tr>
			<tr>
			  <td align="left">&nbsp;</td>
			</tr>

			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> USAGE </font><br/></td>
			</tr>
			<tr>			
				<td align="left"><ul>
					<li>Requirement</li><br><font class="blue"> 
					CIFTER was implemented using JAVA programming language, and requires Java Running Environment (JRE). If JRE is not installed, please visit <a href="http://www.oracle.com/technetwork/java/index.html" target="_blank" class="under">http://www.oracle.com/technetwork/java/index.html</a><br><br></font>

					<li>Input and Output</li><br><font class="blue"> 
					As input, CIFTER takes only a centroided mzXML file.<br><br> 
					As output, *.csd.txt file is automatically generated, <br>
					&nbsp;&nbsp;&nbsp;&nbsp;where each MS/MS scan is given its charge state.<br>
					Body of output consists of three columns: [Scan No] [Estimated value] [Group]<br>
					- Scan No: scan number in mzXML<br> 
					- Estimated value: a positive value represents that a precursor ion is doubly charged and<br> 
					&nbsp;&nbsp;&nbsp;&nbsp;a negative value represents that a precursor ion is triply charged.<br>
					- Group: three groups, 2, 3, 2OR3<br><br>

					As output, an MGF file can be optionally generated, <br>
					&nbsp;&nbsp;&nbsp;&nbsp;where charge states of MS/MS spectra are predicted by CIFTER.<br><br></font>

					<li>Command line</li><br><font class="blue"> 
						<tt> java -jar cifter.jar -i foo.mzXML [option]</tt><br><br>
						option ' <tt>-mgf</tt> ' can be used to make MGF file (Its name would be foo.mgf).</font>
					

			   </ul></td>

			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Publication</font><br/></td>
			</tr>
			<tr>
			  <td>&nbsp;&nbsp;&nbsp;<font class="drakBR">CIFTER: automated charge state determination for peptide tandem mass spectra.</font><br>&nbsp;&nbsp;&nbsp;<em>Seungjin Na, Eunok Paek, and Cheolju Lee.</em><br>&nbsp;&nbsp;&nbsp;Analytical Chemistry, 2008, 80, 1520-1528. <a href="http://www.ncbi.nlm.nih.gov/pubmed/22186716" target="_blank" class="under">[ PMID: 18247484 ]</a></td>
			</tr>	

		</table>	
	</td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
