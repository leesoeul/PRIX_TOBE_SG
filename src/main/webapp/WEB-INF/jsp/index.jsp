<jsp:include page="inc/introduction.jsp" flush="true" />
<!-- header ³¡-->

<table width="990" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" width="190" rowspan="2" align="right" valign="top" style="padding-right:15px">
		<table border="0" cellpadding="0" cellspacing="0">
		  <!--tr>
			<td height="53" valign="bottom"></td>
		  </tr-->
		  
		  <tr>
			<td height="320" align="center" valign="top"><img src="/images/subimg01.gif" width="161" height="277" /></td>
		  </tr>
		  
		  <!--tr>
			<td height="100%" align="center" valign="bottom"><img src="/images/memo.gif" width="161" height="160"></td>
		  </tr-->

		  <!--tr>
			<td height="100%" align="center" valign="bottom"><img src="/images/logo_hanyang_.gif" width="85" height="85"></td>
		  </tr-->
		</table>
	</td>

    <td height="10" valign="top">
	
			<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
			  <tr>
				<td style="padding-left:15px;"><font class="drakBR" size="4">Welcome!  <br/></td>
		        <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
<%
	if( session.getAttribute("id") != null && !session.getAttribute("id").equals("4") )
	{
%>
          <tr>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="modplus/history.jsp" class="menu">History</a> &nbsp; &nbsp;</td>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="login.jsp?action=logout" class="menu">Logout</a> &nbsp; &nbsp;</td>
          </tr>
<%
	}
%>
				</table></td>
			  </tr>
			</table>
			<table width="95%" border="0" align="center" cellpadding="1">
				
				<tr>
				  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"> This site features <a href="modplus/search.jsp" class="under">MODPlus</a><font class="drakBR"> (advanced version of MOD<sup>i</sup>),</font> a powerful modification search engine that uses mass spectrometry data to identify protein modifications. MODPlus facilitates cutting-edge proteomics research by extensively searching through hundreds of potential modifications efficiently. Among the existing search tools, MODPlus can take the most number of modifications as its search parameter.<br/><br/></td>
				</tr>
				<!--tr>
				  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Looking to dig deeper? Jump right into <a href="modi/search.jsp" class="under">MODmap</a> !<br/></td>
				</tr>
				<tr>
				  <td align="left">&nbsp;&nbsp;After the initial MOD<sup>i</sup> search, a more in-depth analysis for potential novel modification can be performed using MODmap in tandem with MOD<sup>i</sup>. MODmap makes use of MOD<sup>i</sup>'s unsuccessful identifications and explores for potentially important rare and unknown modifications from modified regions in MS/MS spectra.<br/><br/></td>
				</tr-->
				<tr>
				  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Special search engine for disulfide linkage analysis, <a href="dbond/dbond_search.jsp" class="under">DBond</a>.<br/></td>
				</tr>
				<tr>
				  <td align="left">&nbsp;&nbsp;Certain post-translational modifications involve more than one peptide and thus cannot be easily searched by conventional modification search algorithms. We provide specialized software, DBond that can analyze intact disulfide linkage between two peptides. Disulfide linkages not only contribute to the stability of a protein's structure, but it is known to regulate mediation of various signaling pathways in a cell. It is of particular interest to biopharmaceutical industry as disulfide bridge characterization is an important part of acceptance criteria for biological products.
				  <br/><br/></td>
				</tr>

				<tr>
				  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Multi-blind unrestrictive modification search, <a href="download/moda.jsp" class="under">MODa</a>.<br/></td>
				</tr>
				<tr>
				  <td align="left">&nbsp;&nbsp;We provide specialized software, MODa that can search spectra for all possible modifications at once, in unrestrictive mode. MODa is the first practical multi-blind unrestrictive approach for the identification of multiply modified peptides.
				  <br/><br/></td>
				</tr>
				
				<!--tr>
				  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> <a href="antibodylib.jsp" class="under">Antibody Peptide Library</a>.<br/></td>
				</tr>
				<tr>
				  <td align="left">&nbsp;&nbsp;We now provide antibody peptide library online.
				  <br/><br/></td>
				</tr-->

				<!--tr>
					<td align="left"><br/><br/><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Sponsors<br></td>
					<table width="60%" border="0" cellpadding="1">				
						<tr>
							<td align="center" valign="bottom"><a href="http://www.proteome.re.kr/" target="_blank"><img src="/images/proteomics.gif" width="40" height="38" /></a></td>
							<td align="center"><a href="http://ccsddr.ewha.ac.kr/" target="_blank"><img src="/images/ccsddr.jpg" width="80" height="49" /></a></td>				
						</tr>
						<tr>
							<td align="center">Functional Proteomics Center</td>
							<td align="center">National Core Research Center</td>
						</tr>
				   </table>
					
				</tr-->
			</table>		
	</td>
  </tr>

</table>

<jsp:include page="inc/footer.jsp" flush="true" />
 