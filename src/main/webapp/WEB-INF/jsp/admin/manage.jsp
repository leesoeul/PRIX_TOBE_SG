<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%
	response.setCharacterEncoding("UTF-8");
	
	String id = (String)session.getAttribute("id");
	if (id == null)
		response.sendRedirect("adlogin.jsp");

	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	if (conn != null) {
		Statement state = conn.createStatement();
		
		if (request.getParameter("delete_enzyme") != null) {
			state.executeUpdate("delete from px_enzyme where id=" + request.getParameter("delete_enzyme") + ";");
		}
		else if (request.getParameter("enzyme_name") != null) {
			String name = request.getParameter("enzyme_name");
			String cut = request.getParameter("enzyme_nt_cut");
			String term = request.getParameter("enzyme_ct_cut");
			String index = request.getParameter("enzyme_id");
			if (index == null) {
				state.executeUpdate("insert into px_enzyme (name, nt_cleave, ct_cleave) values ('" + name + "', '" + cut + "', '" + term + "');");
			} else {
				state.executeUpdate("update px_enzyme set name='" + name + "', nt_cleave='" + cut + "', ct_cleave='" + term + "' where id=" + index + ";");
			}
		}
		else if (request.getParameter("delete_db") != null) {
			state.executeUpdate("delete from px_database where id=" + request.getParameter("delete_db") + ";");
		}
		else if (request.getParameter("db_name") != null) {
			String name = request.getParameter("db_name");
			String index = request.getParameter("db_index");
			if (index != null) {
				state.executeUpdate("update px_database set name='" + name + "' where id=" + index + ";");
			}
		}
		else if (request.getParameter("modify_swmsg") != null) {
			state.executeUpdate("update px_software_msg set message='" + request.getParameter("modamsg").replace("'", "\\'") + "' where id='moda';");
			state.executeUpdate("update px_software_msg set message='" + request.getParameter("dbondmsg").replace("'", "\\'") + "' where id='dbond';");
			state.executeUpdate("update px_software_msg set message='" + request.getParameter("signature").replace("'", "\\'") + "' where id='signature';");
		}

		state.close();
		conn.close();
	}
	
	response.sendRedirect("configuration.jsp");
%>
