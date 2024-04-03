<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta property="og:title" content="PRIX - PRoteome InformatiX">
<meta property="og:type" content="website">
<meta property="og:image" content="images/ci.gif">
<meta property="og:description" content="PRoteome InformatiX by Bioinformatics & Intelligent Systems Lab">
<title>PRIX - PRoteome InformatiX</title>
<link href="/css/home.css" rel="stylesheet" type="text/css">
<link rel="icon" href="images/ci.gif" type="image/gif" >
</head>

<body>
<table width="100%" height="119" border="0" cellpadding="0" cellspacing="0" style=" margin-bottom:10px">
 
  <tr>
    <td height="88" background="/images/menu_bg.gif">
	<table width="990" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="203"><a href="/index.jsp"><img src="/images/ci.gif" width="196" height="88"></a></td>
		<td><table width="100%" height="88" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="right" valign="top" style="padding-top:3px"><table  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="62" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
					  <% if(!( session.getAttribute("id") == null || session.getAttribute("id").equals("4") )) { %>
						  <td width="105" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
					  <% } %>
                      <td width="45" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
                      <td width="68" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
                      <td width="50" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
					  <td width="60" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
                    </tr>
					<tr>
                      <td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;
					  <% if( session.getAttribute("id") == null || session.getAttribute("id").equals("4") ) { %>
							<a href="/login.jsp" class="top_menu">LOG&nbsp;&nbsp;I&nbsp;N</a>
						  </td>
					  <% } else {%>
							<a href="/login.jsp?action=logout" class="top_menu">LOGOUT</a></td>
							<td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;
							<a href="/deleteAccount.jsp" class="top_menu">DELETE ACCOUNT</a>
							</td>
					  <% } %>
                      <td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/help.jsp" class="top_menu">HELP</a></td>
                      <td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/contact.jsp" class="top_menu">CONTACT</a></td>
					  <td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/admin/adlogin.jsp" class="top_menu">ADMIN</a></td>
					  <td> &nbsp;</td>
					</tr>
                  </table></td>
				</tr>

				<tr>
					<td valign="bottom"><table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td><a href="/"><img src="/images/menu04_01.gif" width="142" height="29" border="0"></a></td>
						<td><a href="/livesearch"><img src="/images/menu04_02.gif" width="135" height="29" border="0"></a></td>
						<td><a href="/download"><img src="/images/menu04_03.gif" width="133" height="29" border="0"></a></td>
						<td><a href="/publications"><img src="/images/menu04_04.gif" width="157" height="29" border="0"></a></td>
					  </tr>
					</table></td>
				</tr>
				
		</table></td>
      </tr>
    </table>
	</td>
  </tr>
 
  <tr>
    <td height="25" align="right" background="/images/menu_bg2.gif"><img src="/images/menu_bg3.gif" width="276" height="25"></td>
  </tr>
 
  <tr>
    <td height="6" background="/images/menu_bg4.gif"></td>
  </tr>

</table>
