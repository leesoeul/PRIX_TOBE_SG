<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- wt
 <%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Date" %>
<%
	response.setCharacterEncoding("UTF-8");
	
	String id = (String)session.getAttribute("id");
	Integer level = (Integer)session.getAttribute("level");
	if (id == null) response.sendRedirect("adlogin.jsp");	
	else if (level == null || level <= 1) response.sendRedirect("index.jsp");

	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
%> --%>

<jsp:include page="../inc/misc.jsp" flush="true" />
<!-- header ��-->

<%-- wt
<script language="javascript">
function doSoftware()
{
	var form = document.software;
	if (form.sftw_name.value == ''){
		alert("Please select name.");
		return;
	}
	if (form.sftw_version.value == ''){
		alert("Please input version.");
		return;
	}
	if (form.sftw_zip.value == ''){
		alert("Please select file.");
		return;
	}
	if ( !checkDate(form.sftw_date.value) ){
		alert("Please check date format.ex)YYYY-MM-DD");
		return;
	}
	if ( !checkConsistency(form.sftw_name.value, form.sftw_zip.value) ){
		alert("*.zip file should be allowed.");
		return;
	}
	form.submit();
}
function checkConsistency(iName, iFile) {	
	if( iFile.lastIndexOf(".zip") == iFile.length-4 ) return true;
	return false;
}
function checkDate(iDate) {
	var target= iDate.split("-");
	if( target.length != 3 ) return false;
	var vDate = new Date(target[0], target[1], target[2]);
	if( vDate.getFullYear() != target[0] || vDate.getMonth() != target[1] || vDate.getDate() != target[2] )
		return false;
	return true;
}
</script> --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		&nbsp;
	</td>

    <td>
	
		<table border="0" cellspacing="2" cellpadding="2" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">ADMINISTRATION&nbsp;&nbsp;</font>
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<u>Configuration</u>&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="searchlog" class="menu">Search Log</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="users" class="menu">Users</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="requestlog" class="menu">Request Log</a>
			</td>
			<td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
		  </tr>
		  <tr align="left">
            <td><font color="blue"> &gt; Configuration</td>
          </tr>
		</table>
		<table width="800" border="0" align="left" cellpadding="1">			
			
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Database<br/></td>
			</tr>
			<tr>
				<td align="left">				
				<table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
					<tr>    
					  <td class="grayTH" >Name</td>	
					  <td class="grayTH" >File</td>
					  <td class="grayTH"><div align="right">&nbsp;</div></td>
					</tr>
<%-- wt
<%
	if (conn != null) {
		Statement state = conn.createStatement();
		String sql = "select id, name, file from px_database;";
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
%>
					<tr>
					  <form method="post" action="manage.jsp">
						<input name="db_index" type="hidden" value="<%=rs.getInt(1)%>"/>
					  <td class="grayTD"><input name="db_name" type="text" size="40" value="<%=rs.getString(2)%>" /></td>
					  <td class="grayTD" ><%=rs.getString(3)%></td>
					  <td class="grayTD"><div align="right"><input name="modify_db" type="submit" class="btnSmall" value=" edit " />&nbsp;
						<input name="delete_db" type="button" class="btnSmall" value=" unlink " onclick="document.location='manage.jsp?delete_db=<%=rs.getInt(1)%>';" /></div></td>
					  </form>
					</tr>
<%
		}
		rs.close();
		state.close();
	}
%> --%>

<%-- 졸프용 추가한 코드 --%>
<c:if test="${not empty listDatabaseResponseDto}">
<c:forEach var="item" items="${listDatabaseResponseDto}">
    <tr>
        <form method="post" action="manage.jsp">
            <input name="db_index" type="hidden" value="${item.id}"/>
            <td class="grayTD">
                <input name="db_name" type="text" size="40" value="${item.name}" />
            </td>
            <td class="grayTD">${item.file}</td>
            <td class="grayTD">
                <div align="right">
                    <input name="modify_db" type="submit" class="btnSmall" value=" edit " />
                </div>
            </td>
						<input name="delete_db" type="button" class="btnSmall" value=" unlink " onclick="document.location='manage.jsp?delete_db=${item.id}';" /></div></td> 
        </form>
    </tr>
</c:forEach>
</c:if>

					<!--tr>
					  <td class="grayTD"><input name="user" type="text" size="40" value="Swissprot_Human_57.12" /></td>		
					  <td class="grayTD" >E:\Swissprot_Human_57.12.fasta</td>
					  <td class="grayTD"><div align="right"><input name="eei0" type="button" class="btnSmall" value=" edit " />&nbsp;
					  <input name="dei0" type="button" class="btnSmall" value=" delete " /></div></td>
					</tr> 
					<tr>
					  <td class="grayTD"><input name="user" type="text" size="40" value="Swissprot_Mouse_57.12" /></td>
					  <td class="grayTD" >E:\cccccccccc2.fasta</td>
					  <td class="grayTD"><div align="right"><input name="eei1" type="button" class="btnSmall" value=" edit " />&nbsp;
					  <input name="dei1" type="button" class="btnSmall" value=" delete " /></div></td>
					</tr>
					<tr>
					  <td class="grayTD"><input name="user" type="text" size="40" value="Swissprot_Rattus_57.12" /></td>	
					  <td class="grayTD" >E:\xxxxx.fasta</td>
					  <td class="grayTD"><div align="right"><input name="eei2" type="button" class="btnSmall" value=" edit " />&nbsp;
					  <input name="dei2" type="button" class="btnSmall" value=" delete " /></div></td>
					</tr-->
					<tr>
					  <form method="post" action="manage_file.jsp" enctype="multipart/form-data">
					  <td class="grayTD"><input name="db_name" type="text" size="40" /></td>
					  <td class="grayTD"><input name="db_file" type="file" size="40" /></td>
					  <td class="grayTD"><div align="right"><input name="add_db" type="submit" class="btnSmall" value=" upload " /></td>
					  </form>
					</tr>
				</table>
				<br>
			  </td>
			</tr>

			<tr>
				<td><br>&nbsp;<br></td>
			</tr>

			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Enzymes<br/></td>
			</tr>
			<tr>
				<td align="left">				
				<table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
					<tr>    
					  <td class="grayTH" >Name</td>
					  <td class="grayTH">At Nterm</div></td>
					  <td class="grayTH">At Cterm</div></td>
					  <td class="grayTH"><div align="right">&nbsp;</div></td>
					</tr>
<%-- wt
<%
	if (conn != null) {
		Statement state = conn.createStatement();
		String sql = "select name, nt_cleave, ct_cleave, id from px_enzyme where user_id=0;";
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
%>
					<tr>
					  <form method="post" action="manage.jsp">
						<input name="enzyme_id" type="hidden" value="<%=rs.getInt(4)%>"/>
					  <td class="grayTD"><input name="enzyme_name" type="text" value="<%=rs.getString(1)%>"/></td>
					  <td class="grayTD"><input name="enzyme_nt_cut" type="text" value="<%=rs.getString(2)%>"/></td>
					  <td class="grayTD"><input name="enzyme_ct_cut" type="text" value="<%=rs.getString(3)%>"/></td>
					  <td class="grayTD"><div align="right"><input name="modify_enzyme" type="submit" class="btnSmall" value=" edit " />&nbsp;<input name="delete_enzyme" type="button" class="btnSmall" value=" delete " onclick="document.location='manage.jsp?delete_enzyme=<%=rs.getInt(4)%>';" /></div></td>
			 		</form>
					</tr> 
<%
		}
		rs.close();
		state.close();
	}
%> --%>

<%-- 졸프용 추가한 코드 --%>
<c:if test="${not empty listEnzymeResponseDto}">
<c:forEach var="item" items="${listEnzymeResponseDto}">
    <tr>
        <form method="post" action="manage.jsp">
            <input name="enzyme_id" type="hidden" value="${item.id}"/>
            <td class="grayTD">
                <input name="enzyme_name" type="text" size="40" value="${item.name}" />
            </td>
						<td class="grayTD">
                <input name="enzyme_name" type="text" size="40" value="${item.nt_cleave}" />
            </td>
						<td class="grayTD">
                <input name="enzyme_name" type="text" size="40" value="${item.ct_cleave}" />
            </td>
            <td class="grayTD">
                <div align="right">
                    <input name="modify_enzyme" type="submit" class="btnSmall" value=" edit " />
										&nbsp;<input name="delete_enzyme" type="button" class="btnSmall" value=" delete " onclick="document.location='manage.jsp?delete_enzyme=${item.id}';" />
                </div>
            </td>
						
        </form>
    </tr>
</c:forEach>
</c:if>


					<tr>
					  <form method="post" action="manage.jsp">
					  <td class="grayTD"><input name="enzyme_name" type="text"/></td>
					  <td class="grayTD"><input name="enzyme_nt_cut" type="text"/></td>
					  <td class="grayTD"><input name="enzyme_ct_cut" type="text"/></td>
					  <td class="grayTD"><div align="right"><input name="add_enzyme" type="submit" class="btnSmall" value=" add " /></div></td>
					  </form>
					</tr>
				</table>
				<br>
			  </td>
			</tr>
			
			<tr>
				<td><br>&nbsp;<br></td>
			</tr>
			
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Modifications<br/></td>
			</tr>
			
			<tr>
				<td align="left">				
				<table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
					<tr>    
					  <td class="grayTH" >Date</td>	
					  <td class="grayTH" >Version</td>
					  <td class="grayTH" >File</td>
					  <td class="grayTH"><div align="right">&nbsp;</div></td>
					</tr>
<%-- wt
<%
	if (conn != null) {
		Statement state = conn.createStatement();
		String sql = "select date, version, file from px_modification_log;";
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
%>
					<tr>
						<td class="grayTD"><%=rs.getString(1)%></td>		
					  <td class="grayTD"><%=rs.getString(2)%></td>
					  <td class="grayTD"><%=rs.getString(3)%></td>
					  <td class="grayTD"><div align="right">&nbsp;</div></td>
					</tr> 
<%
		}
		rs.close();
		state.close();
	}
%> --%>

<%-- 졸프용 추가한 코드 --%>
<c:if test="${not empty listModificationLogResponseDto}">
<c:forEach var="item" items="${listModificationLogResponseDto}">
    <tr>
        <form method="post" action="manage.jsp">
            <td class="grayTD">
							${item.date}
            </td>
						<td class="grayTD">
							${item.version}
            </td>
						<td class="grayTD">
							${item.file}
            </td>
            <td class="grayTD">
                <div align="right">
										&nbsp;
                </div>
            </td>
						
        </form>
    </tr>
</c:forEach>
</c:if>

					<tr>
					  <form method="post" action="manage_file.jsp" enctype="multipart/form-data">
					  <td class="grayTD"><input name="ptm_date" type="date" size="20" /></td>
					  <td class="grayTD"><input name="ptm_version" type="text" size="20" /></td>
					  <td class="grayTD"><input name="ptm_xml" type="file" size="50" /></td>
					  <td class="grayTD"><div align="right"><input name="ptm_add" type="submit" class="btnSmall" value=" update " /></td>
					  </form>
					</tr>
				</table>
				<br>
			  </td>
			</tr>
			<tr>
				<td><br>&nbsp;<br></td>
			</tr>
			
			<tr>
			  <td align="left"><img src="/images/p_st1.GIF" width="4" height="9"><font class="drakBR"> Software<br/></td>
			</tr>
			
			<tr>
				<td align="left">				
				<table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
					<tr>    
					  <td class="grayTH">Name</td>	
					  <td class="grayTH"><div align="left">Release Date</div></td>
					  <td class="grayTH"><div align="left">Version</div></td>
					  <td class="grayTH">File</td>
					  <td class="grayTH"><div align="right">&nbsp;</div></td>
					</tr>
<%-- wt
<%
	if (conn != null) {
		Statement state = conn.createStatement();
		String sql = "select name, date, version, file from px_software_log order by date, id;";
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
%>
					<tr>
						<td class="grayTD"><%=rs.getString(1)%></td>		
					  <td class="grayTD"><div align="left"><%=rs.getString(2)%></div></td>
					  <td class="grayTD"><div align="left"><%=rs.getString(3)%></div></td>
					  <td class="grayTD"><%=rs.getString(4)%></td>
					  <td class="grayTD"><div align="right">&nbsp;</div></td>
					</tr> 
<%
		}
		rs.close();
		state.close();
	}
%> --%>

<%-- 졸프용 추가한 코드 --%>
<c:if test="${not empty listSoftwareLogResponseDto}">
<c:forEach var="softwareLog" items="${listSoftwareLogResponseDto}">
    <tr>
        <td class="grayTD">${softwareLog.name}</td>
        <td class="grayTD">${softwareLog.date}</td>
        <td class="grayTD">${softwareLog.version}</td>
        <td class="grayTD">${softwareLog.file}</td>
        <td class="grayTD"><div align="right">&nbsp;</div></td>
    </tr>
</c:forEach>
</c:if>


					<tr>
					  <form name="software" method="post" action="manage_file.jsp" enctype="multipart/form-data">
					  <td class="grayTD"><select name="sftw_name">
							<option value="">Select --</option>
							<option value="MODa">MODa</option>
							<option value="DBond">DBond</option>
							<option value="NextSearch">NextSearch</option>
						</select>
					  </td>
					  <td class="grayTD"><div align="left"><input name="sftw_date" type="date" size="15" /></div></td>
					  <td class="grayTD"><div align="left"><input name="sftw_version" type="text" size="10" /></div></td>
					  <td class="grayTD"><div align="left"><input name="sftw_zip" type="file" size="50"/></div></td>
					  <td class="grayTD"><div align="right"><input name="sftw_add" type="button" class="btnSmall" value=" update " onClick="doSoftware()"/></td>
					  </form>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			
			
			<tr>
				<td align="left">				
				<table width="95%" border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; "><form name="swmg1234" method="post" action="manage.jsp?modify_swmsg=1">
				
						<td class="grayTH" width="70"><div align="right"><input name="sftw_msg" type="submit" class="btnOrgSmall" value="  update  "/></div></td>
						<td class="grayTH"><div align="left">Email message to a recipient</div></td>
					</tr>
					<tr>
						<td class="grayTD" valign="top" width="70"><div align="right">MODa</div></td>
					    <td class="grayTD">
						<%-- wt
						<%
						if ( conn != null ) {
							Statement state = conn.createStatement();
							ResultSet rs = state.executeQuery("select message from px_software_msg where id='moda';");							
						%>
						<textarea name="modamsg" rows="8" style="width:100%"><%if( rs.next() ) out.print(rs.getString(1));%></textarea>
						<%
							rs.close();
							state.close();
						}
						%> --%>

							<%-- 졸프용 추가한 코드 --%>
							<c:if test="${not empty listSoftwareMsgMode}">
								<c:forEach var="msg" items="${listSoftwareMsgMode}">
									<textarea rows="8" style="width:100%"><c:out value="${msg.message}" /></textarea>
								</c:forEach>
							</c:if>

						</td>
					</tr>
					<tr>
						<td class="grayTD" valign="top" width="70"><div align="right">DBond</div></td>
					    <td class="grayTD">
						<%-- wt
						<%
						if ( conn != null ) {
							Statement state = conn.createStatement();
							ResultSet rs = state.executeQuery("select message from px_software_msg where id='dbond';");							
						%>
						<textarea name="dbondmsg" rows="8" style="width:100%"><%if( rs.next() ) out.print(rs.getString(1));%></textarea>
						<%
							rs.close();
							state.close();
						}
						%> --%>
							<%-- 졸프용 추가한 코드 --%>
							<c:if test="${not empty listSoftwareMsgDbond}">
								<c:forEach var="msg" items="${listSoftwareMsgDbond}">
									<textarea rows="8" style="width:100%"><c:out value="${msg.message}" /></textarea>
								</c:forEach>
							</c:if>

						</td>
					</tr>
					<tr>
						<td class="grayTD" valign="top" width="70"><div align="right">NextSearch</div></td>
					    <td class="grayTD">
						<%-- wt
						<%
						if ( conn != null ) {
							Statement state = conn.createStatement();
							ResultSet rs = state.executeQuery("select message from px_software_msg where id='nextsearch';");							
						%>
						<textarea name="nextmsg" rows="8" style="width:100%"><%if( rs.next() ) out.print(rs.getString(1));%></textarea>
						<%
							rs.close();
							state.close();
						}
						%> --%>

							<%-- 졸프용 추가한 코드 --%>
							<c:if test="${not empty listSoftwareMsgNextsearch}">
								<c:forEach var="msg" items="${listSoftwareMsgNextsearch}">
									<textarea rows="8" style="width:100%"><c:out value="${msg.message}" /></textarea>
								</c:forEach>
							</c:if>

						</td>
					</tr>
					<tr>
						<td class="grayTD" valign="top" width="70"><div align="right">Signature</div></td>
					    <td class="grayTD">
						<%-- wt
						<%
						if ( conn != null ) {
							Statement state = conn.createStatement();
							ResultSet rs = state.executeQuery("select message from px_software_msg where id='signature';");							
						%>
						<textarea name="signature" rows="8" style="width:100%"><%if( rs.next() ) out.print(rs.getString(1));%></textarea>
						<%
							rs.close();
							state.close();
						}
						%> --%>

							<%-- 졸프용 추가한 코드 --%>
							<c:if test="${not empty listSoftwareMsgSignature}">
								<c:forEach var="msg" items="${listSoftwareMsgSignature}">
									<textarea rows="8" style="width:100%"><c:out value="${msg.message}" /></textarea>
								</c:forEach>
							</c:if>


						</td>
					</tr>
				</form></table>
				</td>
			</tr>
			<tr>
				<td><br>&nbsp;<br></td>
			</tr>
		</table>	
	</td>
  </tr>

</table>

<jsp:include page="../inc/footer.jsp" flush="true" />

<%-- wt
<%
	if (conn != null)
		conn.close();
%> --%>
