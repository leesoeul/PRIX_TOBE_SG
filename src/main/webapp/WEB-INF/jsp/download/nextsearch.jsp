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
			<td align="left"><font class="drakBR" size="3">NextSearch</font> - Exon Graph Search</td>
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
			  <td width="600" align="left">NextSearch includes modules (1) that constructs a compact nucleotide exon graph while incorporating novel splice variations (2) that identifies peptides by searching tandem mass spectra against the nucleotide exon graph built during the previous module.</td>
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
					NextSearch requires Java Running Environment (JRE) 1.7.0_45 or later. JRE can be installed by visiting <a href="http://www.oracle.com/technetwork/java/index.html" target="_blank" class="under">http://www.oracle.com/technetwork/java/index.html.</a> If you want to use the Hadoop version of NextSearch, Hadoop version 1.2.1 or later is necessary. Please visit <a href="http://hadoop.apache.org/" target="_blank" class="under">http://hadoop.apache.org/.</a><br><br></font>

					<li>Release Note</li><br>
					<font class="blue"><a href="release_note_nextsearch.txt" class="under">- Feb 20, 2017: version 1.2</font>( <a href="/request.jsp?software=NextSearch" 
					class="under">Software download</a> )<br>
					- Apr 22, 2016: version 1.11<br>
					- Dec 15, 2015: version 1.1<br>
					- June 10, 2015: version 1.00<br>


			   </ul></td>
			<tr>
			  <td class="grayTD" align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Publication</font><br/></td>
			</tr>
			<tr>
			  <td>&nbsp;&nbsp;&nbsp;<font class="drakBR">NextSearch: A Search Engine for Mass Spectrometry Data against a Compact Nucleotide Exon Graph.</font><br>&nbsp;&nbsp;&nbsp;<em>Hyunwoo Kim, Heejin Park, and Eunok Paek.</em><br>&nbsp;&nbsp;&nbsp;Journal of Proteome Research, 2015, 14(7), 2784-2791. <a href="http://www.ncbi.nlm.nih.gov/pubmed/26004133" target="_blank" class="under">[ PMID: 26004133 ]</a></td>
			</tr>	

		</table>	
	</td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
