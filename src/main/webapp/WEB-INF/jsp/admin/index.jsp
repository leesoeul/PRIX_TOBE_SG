<%-- wt
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%
	String url = request.getParameter("url");
	if (url == null)
		url = "configuration.jsp"; 
	String id = (String)session.getAttribute("id");
	if (id == null)
		response.sendRedirect("adlogin.jsp?url=" + url);
	Integer level = (Integer)session.getAttribute("level");
	if (level == null) {
		level = 0;
		Context initContext = new InitialContext();
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
		Connection conn = ds.getConnection();
		if (conn != null)
		{
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select level from px_account where id=" + id);
			if (rs.next())
			{
				level = rs.getInt(1);
			}
			rs.close();
			state.close();
			conn.close();
		}
		session.setAttribute("level", level);
	}
	if (level > 1)
		response.sendRedirect("configuration.jsp");
%> --%>
<jsp:include page="../inc/misc.jsp" flush="true" />
<!-- header ³¡-->
<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="10" valign="top">
      <table border="0" cellspacing="0" cellpadding="0" id="TitTable">
	<tr>
          <td align="left"><font class="drakBR" size="3">You are not authorized to view this page.</td>
          <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<jsp:include page="../inc/footer.jsp" flush="true" />
