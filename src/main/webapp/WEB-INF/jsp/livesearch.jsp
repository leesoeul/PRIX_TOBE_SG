<jsp:include page="inc/livesearch.jsp" flush="true" />
<!-- header ³¡-->

<table width="990" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="190" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
	<!-- left menu -->
	<jsp:include page="inc/livesearch_menu.jsp" flush="true" />
	</td>
    <td height="10" valign="top"><table border="0" cellspacing="0" cellpadding="0" id="TitTable">
      <tr>
        <td align="left"><font class="drakBR" size="3">MS/MS ANALYSIS</td>
		
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
	  <tr>
		<td align="left"><font class="red" size="2">This service is currently stopped due to error correction.</td>
	  </tr>
	  <tr>
		<td align="left"><font class="red" size="2">Please download softwares instead of online service. </td>
	  </tr>
    </table>
	<table width="95%" border="0" align="center" cellpadding="1">
			<tr>
				<!-- on the construction -->
			  <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<a href="/modplus/search" class="thmenu"><font size="3" color="#0033FF">MODPlus</font></a><font class="drakBR">&nbsp;&nbsp;- advanced version of MOD<sup>i</sup></font><br/></td>
			 <!--<td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<font size="3" color="#0033FF">MODPlus</font></a><font class="drakBR">&nbsp;&nbsp;- advanced version of MOD<sup>i</sup></font><br/></td>-->
			</tr>
			<tr>
			  <td align="left">&nbsp;&nbsp;Identification of multiple post-translational modifications<br/><br/></td>
			</tr>
			<tr>
			<!-- on the construction -->
			  <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<a href="/dbond/dbond_search" class="thmenu"><font size="3" color="#0033FF">DBond</font></a><br/></td>
<!--			  <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<font size="3" color="#0033FF">DBond</font></a><br/></td>>-->
			</tr>
			<tr>
			  <td align="left">&nbsp;&nbsp;Identification of intact disulfide linkages<br/><br/></td>
			</tr>
			<tr>
			<!-- on the consntruction -->
			 <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<a href="/ACTG/search" class="thmenu"><font size="3" color="#0033FF">ACTG</font></a><br/></td>
<!--			 <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<font size="3" color="#0033FF">ACTG</font></a><br/></td>-->
			</tr>
			<tr>
			  <td align="left">&nbsp;&nbsp;Novel peptide mapping onto gene models<br/><br/></td>
			</tr>
			<!-- This function was closed becase of issue
			<tr>
			  <td align="left"><img src="images/p_st1.GIF" width="6" height="12">&nbsp;&nbsp;<a href="/by_UTRpept.jsp" class="thmenu"><font size="3" color="#0033FF">tUTR Search</font></a><br/></td>
			</tr>
			<tr>
			  <td align="left">&nbsp;&nbsp;Database for predicted translated UTR peptides<br/><br/></td>
			</tr>
			-->
		</table>	
	</td>
  </tr>
</table>

<jsp:include page="inc/footer.jsp" flush="true" />
