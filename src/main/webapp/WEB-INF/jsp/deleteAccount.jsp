<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>

<%-- ????
<%
	if(session.getAttribute("id") != null){
		String id = session.getAttribute("id").toString();
		boolean success = false;
		String action = request.getParameter("action");
		if (action != null && action.equalsIgnoreCase("'delete'"))
		{
			session.removeAttribute("id");
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
			Connection conn = ds.getConnection();
			if (conn != null)
			{
				Statement state = conn.createStatement();
				state.executeUpdate("delete from px_account where id='" + id +"'"); 
				state.close();
				conn.close();
				success = true;
			}

			if (success)
			{
				response.sendRedirect("/");	
			}
		}

	}

%> --%>


<jsp:include page="./inc/misc.jsp" flush="true" />
<!-- header ³¡-->
<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    

    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">DELETE ACCOUNT</td>
        <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <form method="post" action="deleteAccount?action=delete">
  <tr>
    <td align="center" valign="top" style="padding:10px;"><img src="/images/tit03_deleteAccount.gif" width="178" height="40">
        <div id="container" style="background: #fff; width:400px;" > <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
            <div class="box">
			<% if(!( session.getAttribute("id") == null || session.getAttribute("id").equals("4") )) { %>
				<H3  align="left">Do you want to delete your account?<br>
				It does not recover when you delete your acount once.</H3><br>
			<% } %>
			<input value="cancel" type="button" class="btn" onClick="history.go(-1);">
            <input type="submit" class="btn" value="delete" onClick="submit()">
            </div>

          <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div></td>
  </tr>
  </form>
</table>

<jsp:include page="./inc/footer.jsp" flush="true" />
