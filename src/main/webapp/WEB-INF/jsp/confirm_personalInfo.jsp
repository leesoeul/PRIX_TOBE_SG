<jsp:include page="./inc/misc.jsp" flush="true" />
<!-- header 끝-->

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%-- ㅈㅍㅈㅅ
<%
	String url = request.getParameter("url");
	if (url == null)
		url = "index.jsp";
	String name = "";//request.getParameter("name");
	String job = "";//request.getParameter("job");
	String email = request.getParameter("email");
	String pw = request.getParameter("pw1");
	boolean success = true;
//	if ( email != '' )
	if ( email != null && email.length() > 0 )
	{
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
			Connection conn = ds.getConnection();
			String index = "";
			if (conn != null)
			{
				Statement state = conn.createStatement();
				ResultSet rs = state.executeQuery("select * from px_account where email='" + email + "';");
				if ( rs.next() )
					success = false;
				rs.close();
				
				if (success)
				{
					conn.setAutoCommit(false);
				//	String sql = "insert into px_account (name, email, password, affiliation) values ('" + name + "', '" + email + "', password('" + pw + "'), '" + job + "');";
				    String sql = "insert into px_account (name, email, password, affiliation) values ('" + email + "', '" + email + "', password('" + pw + "'), '" + job + "');";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.executeUpdate();
					conn.commit();

					rs = state.executeQuery("select * from px_account where email='" + email + "';");
					if (rs.next())
					{
						session.setAttribute("id", rs.getString(1));
					}
					rs.close();
				}
				state.close();
				conn.close();
			}
			if (success)
				response.sendRedirect(url);
		}
		catch ( Exception e ){
			success = false;
		}
	}
%>

<script language="javascript">
function doSubmit()
{
	var form = document.reg;
/*	if (form.name.value == '')
	{
		alert("Please input name.");
		return;
	}*/
	if (form.email.value == '')
	{
	//	alert("Please input email address.");
	    alert("Please input user id.");
		return;
	}
/*	if (form.email.value.indexOf("@") == -1 || form.email.value.indexOf(".") == -1)
	{
		alert("Invalid email address.");
		return;
	}//*/
	if (form.pw1.value != form.pw2.value)
	{
		alert("Passwords mismatch.");
		return;
	}
//	form.action="registration.jsp";
	form.submit();
}
</script> --%>

<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">REGISTRATION</td>
        <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
      </tr>
    </table>
      </td>
  </tr>
  <form name="reg" method="post" action="registration.jsp">
  <%-- ㅈㅍㅈㅅ
	<input type="hidden" name="url" value="<%=url%>"/> --%>
  <tr>
    <td align="center" valign="top" style="padding:10px;"><img src="/images/tit_join.gif">
<%-- ㅈㅍㅈㅅ
<%
	if (!success)
	{
%>
	<!--br/><font color="red">Email address "<%=email%>" already exists.</font-->
	<br/><font color="red">The ID "<%=email%>" already exists.</font>
<%
	}
%> --%>
      <div id="container" style="background: #fff; width:1000px"> <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
          <div class="box">
            <table width="80%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; " >
				<tr>
					<div align="left">
					<H2>Personal Information Policy</H2>
					<H3>In accordance with the Personal Information Protection Act, we will inform you of the purpose of collecting and using the following personal information.</H3><br>

					<b>1. Purpose of collecting personal information</b><br>
					The PRIX service is provided through the account and we collect ID and password to create these accounts.<br><br>
					<b>2. Personal information items to collect</b><br>
					ID and Password<br><br>
					<b>3. Personal Information Retention and Use Period</b><br>
					We will permanently retain your ID and password unless you opt out of PRIX.<br><br>
					<b>4. You may refuse to consent to the collection, use, or provision of personal information, but you may be restricted from using the website.</b><br><br>
					</div>
				</tr>
            </table>
            <br>
            <%-- ㅈㅍㅈㅅ
						<input name="Submit2223" type="button" class="btn" value="Decline" onClick="window.location='login.jsp?url=<%=url%>'">
            <input name="Submit22222" type="button" class="btn" value="Accept" onClick="window.location='registration.jsp?url=<%=url%>'"> --%>
						<input name="Submit2223" type="button" class="btn" value="Decline" onClick="window.location='login'">
            <input name="Submit22222" type="button" class="btn" value="Accept" onClick="window.location='registration'">

          </div>
    <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div></td>
  </tr>
  </form>
</table>

<jsp:include page="./inc/footer.jsp" flush="true" />
