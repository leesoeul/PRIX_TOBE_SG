<jsp:include page="/inc/misc.jsp" flush="true" />
<!-- header end-->

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>

<%
	response.setCharacterEncoding("UTF-8");
	
	String id = (String)session.getAttribute("id");
	Integer level = (Integer)session.getAttribute("level");
	if (id == null) response.sendRedirect("adlogin.jsp?url=users.jsp");	
	else if (level == null || level <= 1) response.sendRedirect("index.jsp?url=users.jsp");

	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	
	int update = 0, delete = 0;
	String userid = "";
	if (request.getParameter("up") != null) {
		update = 2;
		userid = request.getParameter("up");
	}
	else if (request.getParameter("down") != null) {
		update = 1;
		userid = request.getParameter("down");
	}
	else if (request.getParameter("del") != null) {
		delete = 1;
		userid = request.getParameter("del");
	}

	if (update > 0 && conn != null) {
		Statement state = conn.createStatement();
		String sql = "update px_account set level=" + update + " where id=" + userid + ";";
		try {
			state.executeUpdate(sql);
		} catch (Exception e) {
			state.executeUpdate(sql);
		}
		state.close();
	}

	if (delete > 0 && conn != null) {
	/*	Statement state = conn.createStatement();
		String sql = "delete from px_account where id=" + userid + ";";
	//	String sql = "update px_account set password=password('**')" + " where id=" + userid + ";";
		try {
			state.executeUpdate(sql);
		} catch (Exception e) {
			state.executeUpdate(sql);
		}//*/

	/*	int index = 0;
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "select msfile, db, result from px_search_log where user_id=" + userid + " order by date desc, id desc;";
		ResultSet rs = null;
		try {
			rs = state.executeQuery(sql);
		} catch (Exception e) {
			rs = state.executeQuery(sql);
		}
		while (rs.next())
		{
			if( ++index == 30 ) break;
		}
		while (rs.next())
		{
			Statement delData = conn.createStatement();
			delData.executeUpdate("delete from px_data where id=" + rs.getInt(1) + ";");
			delData.executeUpdate("delete from px_data where id=" + rs.getInt(2) + ";");
			delData.executeUpdate("delete from px_data where id=" + rs.getInt(3) + ";");
			delData.close();
			rs.deleteRow();
		}
		rs.close();
		state.close();//*/
	}

%>
<!--script language="javascript">
function del()
{
	var form = document.req;	
	form.submit();
}
</script-->

<table width="99%" border="0" cellpadding="0" cellspacing="0">
  <tr>

    <td width="10" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		&nbsp;
	</td>

    <td>
	
		<table border="0" cellspacing="2" cellpadding="2" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">ADMINISTRATION&nbsp;&nbsp;</font>
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="configuration.jsp" class="menu">Configuration</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="searchlog.jsp" class="menu">Search Log</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<u>Users</u>&nbsp;&nbsp;&nbsp;&nbsp;
		    <img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="requestlog.jsp" class="menu">Request Log</a>
			</td>
			<td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
		  </tr>
		  <tr align="left">
            <td><font color="blue"> &gt; Users</td>
          </tr>
		</table>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
    <tr>    
      <td class="grayTH"><div align="center">ID</div></td>
	  <td class="grayTH">Name</td>
      <td class="grayTH">Affiliation</td>
      <td class="grayTH">E-mail</td>
	  <td class="grayTH"><div align="center">Group (user/admin)</div></td>
	  <td class="grayTH"><div align="center">Level</div></td>
	  <td class="grayTH"><div align="center">Delete</div></td>
    </tr>
<%
	if (conn != null)
	{
		Statement state = conn.createStatement();
		String sql = "select id, name, affiliation, email, level from px_account;";
		ResultSet rs = null;
		try {
			rs = state.executeQuery(sql);
		} catch (Exception e) {
			rs = state.executeQuery(sql);
		}
		while (rs.next())
		{
			String name = rs.getString(2);
			String affiliation = rs.getString(3);
			String email = rs.getString(4);
			if (name == null || name.length() == 0)
				name = "&nbsp;";
			if (affiliation == null || affiliation.length() == 0)
				affiliation = "&nbsp;";
			if (email == null || email.length() == 0)
				email = "&nbsp;";
%>
	<tr onMouseOver="this.style.backgroundColor='#fafad2'" onMouseOut="this.style.backgroundColor='#ffffff'">
	  <td class="grayTD"><div align="center"><%=rs.getInt(1)%><a/></div></td>
	  <td class="grayTD"><%=name%></td>
      <td class="grayTD"><%=affiliation%></td>
      <td class="grayTD"><%=email%></td>
	  <td class="grayTD"><div align="center"><%=rs.getInt(5) > 1 ? "admin" : "user"%></div></td>
	  <td class="grayTD"><div align="center">
<%
		  if ( id != null && id.compareTo(rs.getString(1)) == 0 ) {
%>
			&nbsp;</div></td><td class="grayTD"><div align="center">&nbsp;</div></td>
<%
		} else {
%>
			<input name="btup" type="button" class="btnSmall" value=" up " onclick="document.location='users.jsp?up=<%=rs.getInt(1)%>';" />&nbsp;
			<input name="btdown" type="button" class="btnSmall" value=" dn " onclick="document.location='users.jsp?down=<%=rs.getInt(1)%>';"  /></div></td>

		<td class="grayTD"><div align="center">
		<input name="deluser" type="button" class="btnBlueSmall" value=" del " onclick="if(window.confirm('Sure to delete it??')) document.location='users.jsp?del=<%=rs.getInt(1)%>'" /></div></td>

<%
		}
%>
	</tr> 
<%		
		}
		rs.close();
		state.close();
		conn.close();
	}
%>
 
</table></td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
