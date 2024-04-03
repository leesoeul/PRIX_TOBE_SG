<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%-- <%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %> --%>
<%--????
<%
	String url = request.getParameter("url");
	if (url == null)
		url = "index.jsp";
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
			if (conn != null)
			{
				Statement state = conn.createStatement();
				ResultSet rs = state.executeQuery("select * from px_account where email='" + id + "' and password=password('" + pw + "');");
				if (rs.next())
				{
					index = rs.getString(1);
					success = true;
				}
				rs.close();
				state.close();
				conn.close();
			}

			if (success)
			{
				session.setAttribute("id", index);
				response.sendRedirect(url);
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
			<td align="left"><font class="drakBR" size="3">LOGIN</td>
        <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <%-- ??
	<form method="post"> --%>
	<form:form method="post" action = "/login" modelAttribute="accountDto"> 


  <tr>
    <td align="center" valign="top" style="padding:10px;"><img src="/images/tit_login.gif" width="178" height="40">
    <%-- ????
		<%
    	if (id.length() > 0 && !success)
    	{
    		out.println("<br><font color=\"red\">login failed</font><br>");
    	}
    %> --%>
        <div id="container" style="background: #fff; width:400px"> <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
            <div class="box">
              <table width="40%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
                <tr>
                  <th>ID</th>
                  <%-- ????
									<td><input name="id" type="text" size="30" value="<%=id%>"></td> --%>
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
              <%-- ????
							<input type="hidden" name="url" value="<%=url%>"> --%>
              <input name="Submit2223" type="submit" class="btn" value="login" />
              <%-- ????
							 <input name="Submit22222" type="button" class="btn" value="New Account" onClick="window.location='confirm_personalInfo.jsp?url=<%=url%>'"> --%>
							  <input name="Submit22222" type="button" class="btn" value="New Account" onClick="window.location='confirm_personalInfo'" />
            </div>
          <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div></td>
  </tr>
  </form:form>
</table>

<jsp:include page="./inc/footer.jsp" flush="true" />
