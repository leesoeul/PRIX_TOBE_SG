<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- ??
 <%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %> --%>
<%-- ??
<%
	String url = request.getParameter("url");
	if (url == null)
		url = "configuration.jsp"; 
	String id = request.getParameter("id");
	if (id == null)
		id = "";
	String pw = request.getParameter("pw");
	boolean success = false;
	String action = request.getParameter("action");
	if (action != null && action.compareTo("logout") == 0)
	{
		session.removeAttribute("id");
	}
	else
	{
		if (id.length() > 0)
		{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
			Connection conn = ds.getConnection();
			String index = "";
			int level = 0;
			if (conn != null)
			{
				Statement state = conn.createStatement();
				ResultSet rs = state.executeQuery("select id, level from px_account where email='" + id + "' and password=password('" + pw + "');");
				if (rs.next())
				{
					index = rs.getString(1);
					level = rs.getInt(2);
					success = true;
				}
				rs.close();
				state.close();
				conn.close();
			}

			if (success)
			{
				session.setAttribute("id", index);
				session.setAttribute("level", level);
				if (level > 1)
					response.sendRedirect(url);
				else
					response.sendRedirect("index.jsp");
			}
		}
	}
%> --%>
<jsp:include page="../inc/misc.jsp" flush="true" />
<!-- header ³¡-->
<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    

    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">ADMINISTRATOR LOGIN</td>
        <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <%-- 
	<form method="post" action="adlogin"> --%>
	<form:form method="post" action = "/admin/adlogin" modelAttribute="accountDto"> 

  <tr>
    <td align="center" valign="top" style="padding:10px;">
    <%-- ??
		<%
    	if (id.length() > 0 && !success)
    	{
    		out.println("<br><font color=\"red\">login failed</font><br>");
    	}
    %> --%>
        <div id="container" style="background: #fff; width:310px"> <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
            <div class="box">
              <table width="40%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
                <tr>
                  <th>E-mail</th>
                  <%-- <td><input name="id" type="text" size="30" value="<%=id%>"></td> --%>
									<td><form:input type="text" size="30" path="email" /></td>
                </tr>
                <tr>
                  <th>Password</th>
                  <%-- <td><input name="pw" type="password" size="30"></td> --%>
									<td><form:input type="password" size="30" path="password" /></td>
									<form:input type="hidden" size="30" path="level" />
                </tr>
              </table>
              <br>
              <%-- ??
							<input type="hidden" name="url" value="<%=url%>"> --%>
              <input name="Submit2223" type="submit" class="btn" value="login" />
             
            </div>
          <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div></td>
  </tr>
	</form:form>
</table>

<jsp:include page="../inc/footer.jsp" flush="true" />
