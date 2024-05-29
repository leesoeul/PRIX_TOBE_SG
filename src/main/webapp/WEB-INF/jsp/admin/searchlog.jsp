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
	if (id == null) response.sendRedirect("adlogin.jsp?url=searchlog.jsp");	
	else if (level == null || level <= 1) response.sendRedirect("index.jsp?url=searchlog.jsp");

	String strPage = request.getParameter("p");
	final int pageSize = 50;
	int curPage = 0;
	int totalPage = 0;
	if (strPage != null)
		curPage = Integer.parseInt(strPage);
	
	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
%>

<table width="99%" border="0" cellpadding="0" cellspacing="0">
  <tr>

    <td width="10" height="100%" rowspan="2" align="left" valign="top" style="padding-right:15px">
		&nbsp;
	</td>

    <td>	
	
	<table border="0" cellspacing="2" cellpadding="2" id="TitTable">
	  <tr>
		<td align="left"><font class="drakBR" size="3">ADMINISTRATION&nbsp;&nbsp;</font>
		<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="configuration.jsp" class="menu">Configuration</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<u>Search Log</u>&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="users.jsp" class="menu">Users</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="requestlog.jsp" class="menu">Request Log</a>
		</td>
		
	  </tr>
	  <tr align="left">
		<td><font color="blue"> &gt; Search Log</td>
	  </tr>
	</table>
	
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
    <tr>    
      <td class="grayTH"><div align="center">Index</div></td>
	  <td class="grayTH"><div align="center">User</div></td>
      <td class="grayTH"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
      <td class="grayTH"><div align="center">Title</div></td>
      <td class="grayTH"><div align="center">MS/MS data</div></td>
	  <td class="grayTH"><div align="center">Database</div></td>
	  <td class="grayTH"><div align="center">Engine</div></td>
    </tr>
<%
	if (conn != null)
	{
		Statement state = conn.createStatement();
		String sql = "select count(*) from px_search_log";
		ResultSet rs = null;
		try {
			rs = state.executeQuery(sql);
		} catch (Exception e) {
			rs = state.executeQuery(sql);
		}
		int recordSize = 0;
		if (rs.next()){
			recordSize = rs.getInt(1);
			totalPage = (recordSize - 1) / pageSize + 1;
		}
		rs.close();
		sql = "select l.title, l.date, l.msfile, l.db, l.result, l.engine, a.name, l.id from px_search_log l, px_account a where l.user_id=a.id order by l.date desc, l.id desc limit " + (curPage * pageSize) + ", " + pageSize + ";";
		rs = state.executeQuery(sql);
		int logIndex = recordSize - curPage * pageSize;
		while (rs.next())
		{
			String msFile = "";
			String dbFile = "";
			Statement stateForName = conn.createStatement();
			ResultSet rsForName = stateForName.executeQuery("select name from px_data where id=" + rs.getInt(3) + ";");
			if (rsForName.next())
				msFile = rsForName.getString(1);
			rsForName.close();
			rsForName = stateForName.executeQuery("select name from px_data where id=" + rs.getInt(4) + ";");
			if (rsForName.next())
				dbFile = rsForName.getString(1);
			rsForName.close();
			stateForName.close();
			
			String title = rs.getString(1);
			if (title == null || title.length() == 0)
				title = "&nbsp;";
%>
	<tr onMouseOver="this.style.backgroundColor='#fafad2'" onMouseOut="this.style.backgroundColor='#ffffff'">
	  <!--td class="grayTD"><div align="center"><a href="/modi/result.jsp?file=<%=rs.getInt(5)%>" target="_blank"><%=rs.getInt(8)%><a/></div></td-->
	  <td class="grayTD"><div align="center"><a href="/modplus/result.jsp?file=<%=rs.getInt(5)%>" target="_blank"><%=(logIndex--)%><a/></div></td>
	  <td class="grayTD"><div align="center"><%=rs.getString(7)%></div></td>
      <td class="grayTD"><div align="center"><%=rs.getString(2)%></div></td>
      <td class="grayTD"><div align="center"><%=title%></div></td>
	  <td class="grayTD"><div align="center"><%=msFile%></div></td>
      <td class="grayTD"><div align="center"><%=dbFile%></div></td>
      <td class="grayTD"><div align="center"><%=rs.getString(6)%></div></td>
	</tr> 
<%		
		}
		rs.close();
		state.close();
		conn.close();
	}
%>
	<!--tr>
	  <td class="grayTD"><div align="center"><a href="/">1<a/></div></td>
      <td class="grayTD"><div align="center">2009-12-15</div></td>
      <td class="grayTD"><div align="center">Lens</div></td>
	  <td class="grayTD"><div align="center">lens.mgf</div></td>
      <td class="grayTD"><div align="center">swissprot</div></td>
	  <td class="grayTD"><div align="center">swissprot</div></td>
	  <td class="grayTD"><div align="center">swissprot</div></td>
	</tr> 
	<tr>
	  <td class="grayTD"><div align="center"><a href="/">2<a/></div></td>
      <td class="grayTD"><div align="center">2009-12-17</div></td>
      <td class="grayTD"><div align="center">Test</div></td>
	  <td class="grayTD"><div align="center">xxx.mgf</div></td>
      <td class="grayTD"><div align="center">xxx.fasta</div></td>
	  <td class="grayTD"><div align="center">swissprot</div></td>
	  <td class="grayTD"><div align="center">swissprot</div></td>
	</tr--> 
</table></td>
  </tr>
  <tr>
    <td><br/><div align="center">
<%
	int i = curPage / 20 * 20;
	int size = i + 20;
	if (i > 0) {
%>
      <a href="searchlog.jsp?p=<%=i - 1%>">&lt;</a>&nbsp;
<%
	}
	for (; i < size; i++) {
		if (i >= totalPage)
			break;
		if (i != curPage) {
%>
      <a href="searchlog.jsp?p=<%=i%>"><%=i + 1%></a>&nbsp;
<%
		} else {
%>
      <%=i + 1%>&nbsp;
<%
		}
	}
	if (i < totalPage) {
%>
      <a href="searchlog.jsp?p=<%=i%>">&gt;</a>&nbsp;
<%
	}
%>
    </div></td>
  </tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
