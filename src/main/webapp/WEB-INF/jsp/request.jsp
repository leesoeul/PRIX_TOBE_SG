<jsp:include page="./inc/download.jsp" flush="true" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header ³¡-->

<%--????
 <%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="mail.Mailer" %> --%>

<%-- ????
<%
//	response.setCharacterEncoding("UTF-8");
//	request.setCharacterEncoding("UTF-8");

	String software = request.getParameter("software");
	if (software == null) software="xxx";
	if( "xxx".compareTo(software) == 0 ) response.sendRedirect("publications.jsp"); // 

	String agree = request.getParameter("agreement");
	if (agree == null) agree = "0xno";
	String name = request.getParameter("name");
	String affiliation = request.getParameter("affiliation");
	String title = request.getParameter("title");
	String email = request.getParameter("email");
	String instrument = request.getParameter("instrument");

	int success = 0;
	if( "1xyes".compareTo(agree) == 0 && email != null )
	{
		String subject  = software + " request from " +name;
		try {
			int sent = 0;
			Mailer mt = new Mailer();
			mt.sendEmailToMe(subject, name, affiliation, title, email, instrument);
			sent = 1;

			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
			Connection conn = ds.getConnection();

			if ( conn != null && sent == 1 ) {
				name= name.replace("'", "\\'");
				affiliation= affiliation.replace("'", "\\'");
				title= title.replace("'", "\\'");
				instrument= instrument.replace("'", "\\'");
				String sql = "insert into px_software_request (date, name, affiliation, title, email, instrument, software, state) values ( now(), '" + name + "', '" + affiliation + "', '" + title + "', '" + email + "', '" + instrument + "', '" + software + "', 0);";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				conn.close();
				success = 1;
			}

		}
		catch ( Exception e ){
			success = 2;
		}
	}
%> --%>
<%-- 
<script language="javascript">
function request()
{
	var form = document.req;
	if (form.agreement[0].checked == true)
	{
		alert("You must accept the license agreement to download this software.");
		return;
	}
	if (form.name.value == '')
	{
		alert("Please input your name.");
		return;
	}
	if (form.affiliation.value == '')
	{
		alert("Please input your affiliation.");
		return;
	}
	if (form.title.value == '')
	{
		alert("Please input your title.");
		return;
	}
	if (form.instrument.value == '')
	{
		alert("Please input your instrument type.");
		return;
	}
	if (form.email.value == '')
	{
		alert("Please input your email address.");
		return;
	}
	var filter = /(\S+)@(\S+)\.(\S+)/;
	if (!form.email.value.match(filter))
	{
		alert("Invalid email address.");
		return;
	}
	
	form.submit();
}
</script> --%>

<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">SOFTWARE REQUEST (${software}) </td>
			<td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
		  </tr>
		</table>
   </td>
  </tr>
<%-- ????
<% if( success == 0 ) { %> --%>

  <form name="req" method="post" action="request.jsp">
  <input type="hidden" name="software" value="${software}"/>
  <tr>
	<td>
	<table width="560" border="0" align="left" cellpadding="1">
		<tr><td><font size="3" class="blue">License Agreement<br><br></font>
<pre>
<img src="images/p_st1.GIF" width="4" height="9"> LICENSE INFORMATION
Eunok Paek ("Author") has the right to license on a non-exclusive
basis the parsing engine software that she, Eunok Paek, developed.

Eunok Paek grants to the individual academic researcher listed below
("Licensee") a non-exclusive, non-transferable run-time license to use the
accompanying parsing engine software ("Software"), subject to the restrictions
listed below under "Scope of Grant".

<img src="images/p_st1.GIF" width="4" height="9"> SCOPE OF GRANT
The Licensee may:
* use the Software for educational or research purposes;
* permit others UNDER THE LICENSEE'S SUPERVISION AT THE SAME SITE to use 
  the Software for educational or research purposes;
* copy the Software for archival purposes, provided that any such copy contains
  all of the original proprietary notices.

The Licensee may not:
* use the Software for commercial purposes;
* allow any individual who is not under the direct supervision of the Licensee
  to use the Software, without prior written permission from the Author;
* redistribute the Software, without prior written permission from the 
  Author;
* copy the Software other than as specified above;
* rent, lease, grant a security interest in, or otherwise transfer rights to 
  the Software;
* publish any research in which the Software was used without providing a
  citation acknowledging that the Software was developed at the Hanyang
  University by the Author and with a web URL at which the Software may
  be obtained;
* remove any proprietary notices or labels accompanying the Software.

<img src="images/p_st1.GIF" width="4" height="9"> DISCLAIMER
The Author makes no representations or warranties about the suitability of the
Software, either express or implied, including but not limited to the implied
warranties of merchantability, fitness for a particular purpose, or
non-infringement. The Author shall not be liable for any damages suffered by
Licensee as a result of using, modifying or distributing the Software or its
derivatives.

<img src="images/p_st1.GIF" width="4" height="9"> CONSENT
By downloading, using or copying the Software, Licensee agrees to abide by the
intellectual property laws, and all other applicable laws of the Republic of Korea,
and the terms of this License. Ownership of the Software shall remain solely with 
the Owner.

<img src="images/p_st1.GIF" width="4" height="9"> TERMINATION
The Author shall have the right to terminate this license at any time by
written notice. Licensee shall be liable for any infringement or damages
resulting from Licensee's failure to abide by the terms of this License.
</pre></td></tr>

		<tr>
			<td align="right"><input name="agreement" value="0xno" type="radio" checked>Decline&nbsp;&nbsp;&nbsp;<input name="agreement" value="1xyes" type="radio">Accept<td>
		</tr>
	</table>
	</td>
  </tr>
  <tr>
    <td align="left" valign="top" style="padding:10px;">

            <table width="560" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
              <tr>
                <th width="250">Name</th>
                <td><input name="name" type="text" size="55"></td>
              </tr>
              <tr>
                <th width="250">Affiliation</th>
                <td><input name="affiliation" type="text" size="55"></td>
              </tr>
			  <tr>
                <th width="250">Title</th>
                <td><input name="title" type="text" size="55"></td>
              </tr>
              <tr>
                <th width="250">E-mail (at the affiliated institute)</th>
                <td><input name="email" type="text" size="55">
				</td>
              </tr>
			  <tr>
                <th width="250">Instrument Type</th>
                <td><input name="instrument" type="text" size="55">
				</td>
              </tr>
            </table>
            <br>
			<table width="560" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td align="right">
		     <input name="Cancel2223" type="button" class="btn" value="Cancel" onClick="window.location='publications.jsp'">
             <input name="Request222" type="button" class="btn" value="Request" onClick="request()">
			</tr>
			</table>
			<%-- ????
			<!--br>
			<table width="500" border="0" align="left" cellpadding="1">
		<% if( "MODa".compareTo(software) == 0 ) { %>
			<tr>
			  <td align="left"><img src="images/p_st1.GIF" width="4" height="9"><font class="drakBR"> MODa Release Note</font><br>
			  &nbsp;<font class="blue">- version 1.02 (Oct 01, 2012)</font>: modifying a bug in reading a dta file format<br>
			  &nbsp;- version 1.01 (Feb 01, 2012)<br>
			  </td>
			</tr>
		<% }  else { %>
			<tr>
			  <td align="left"><img src="images/p_st1.GIF" width="4" height="9"><font class="drakBR"> DBond Release note</font><br>
			 &nbsp;<font class="blue">- version 3.02 (Oct 01, 2012)</font><br>
			 &nbsp;- version 3.01 (Jan 01, 2012)<br>
			 </td>
			</tr>	
		<% } %>	
		</table-->	 --%>
    </td>
  </tr>
  </form>

<%-- ????
<% } else if( success == 1 ) { %>
  <tr>
    <td align="left" valign="top" style="padding:10px;">
		<font class="blue">Your request has been successfully processed. You'll receive the software via email (<%=email%>) in a few days.<br>If you don't receive the email, please contact <a class="under" href="mailto:prix@hanyang.ac.kr">Eunok Paek</a></font>
  </td>
<% }  else { %>
 <tr>
    <td align="left" valign="top" style="padding:10px;">
		<font class="blue"><%=software%>Your request has not been processed. Please retry.<br>
		If this problem is not resolved, please contact <a class="under" href="mailto:prix@hanyang.ac.kr">Eunok Paek</a></font>
  </td>
</tr>
<% } %> --%>

</table>

<jsp:include page="./inc/footer.jsp" flush="true" />
