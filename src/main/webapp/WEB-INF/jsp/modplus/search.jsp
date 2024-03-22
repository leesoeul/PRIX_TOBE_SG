<jsp:include page="../inc/livesearch.jsp" flush="true" />
<!-- header ��-->

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>                                                       
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%--  졸프주석처리
<%
	final String anony = "4";
	String id = (String)session.getAttribute("id");
	if (id == null){
	//	response.sendRedirect("../login.jsp?url=modi/search.jsp");
		session.setAttribute("id", anony);
		id = (String)session.getAttribute("id");
	}
	
	String version = "1.01";
	String enzyme = "";
	String missedCleavage = "1";
	String minNumEnzTerm = "2";
	String pTolerance = "10";
	String minChar = "2";
	String pUnit = "ppm";
	String fTolerance = "0.05";
	String minIE = "0";
	String maxIE = "1";
	String minMM = "-150";
	String maxMM = "+250";
	String dataFormat = "";
	String instrument = "";
	String msResolution = "";
	String msmsResolution = "";
	
	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	Statement state = null;
	if (conn != null)
	{
		state = conn.createStatement();
		if (id.compareTo(anony) != 0)
		{
			String sql = "select * from px_user_setting where user_id=" + id + " and engine='modeye';";
			ResultSet rs = null;
			try {
				rs = state.executeQuery(sql);
			} catch (Exception e) {
				rs = state.executeQuery(sql);
			}
			if (rs.next())
			{
				enzyme = "" + rs.getInt(2);
				missedCleavage = "" + rs.getInt(3);
				minNumEnzTerm = "" + rs.getInt(4);
				pTolerance = "" + rs.getDouble(5);
				pUnit = rs.getString(6);
				fTolerance = "" + rs.getDouble(7);
				minMM = "" + rs.getDouble(8);
				maxMM = "" + rs.getDouble(9);
				dataFormat = rs.getString(11);
				instrument = rs.getString(12);
				msResolution = rs.getString(13);
				if (msResolution == null)
					msResolution = "";
				msmsResolution = rs.getString(14);
				if (msmsResolution == null)
					msmsResolution = "";
			}
			rs.close();
		}
		else if (request.getParameter("entry") == null)
		{
			// delete modification data for the anonymous user
			String sql = "delete from px_user_modification where user_id=" + anony;
			try {
				state.executeUpdate(sql);
			} catch (Exception e) {
				state.executeUpdate(sql);
			}
		}
		else if (request.getParameter("act") != null)
		{
			String re_id=request.getParameter("act");
			String sql = "delete from px_user_modification where variable=0 and user_id=" + re_id;
			try {
				state.executeUpdate(sql);
			} catch (Exception e) {
				state.executeUpdate(sql);
			}
		}
	}
%>
<script language="javascript">
function adjustIFrameSize(iframeWindow) {
    var iframeElement = document.getElementById("IFRM_Result");
    var wid = 0;
    var hei = 0;
    if (iframeWindow.document.height) {
        hei = iframeWindow.document.height;
    } else if (document.all) { // ie
        if (iframeWindow.document.compatMode && iframeWindow.document.compatMode != 'BackCompat') {
            hei = iframeWindow.document.documentElement.scrollHeight;
        } else {
            hei = iframeWindow.document.body.scrollHeight;
        }
    }
    iframeElement.style.height = hei + 'px';
    document.getElementById("RIGHT").style.height = hei + 'px';
}
function reset() {
		alert("reset.");
	//document.search.act.value = uid;
	//document.search.action = 'search.jsp';
	//document.search.submit();
	//return true;


}
function resett() {

	alert("reset.");


}

function process() {
	var form = document.search;
	//if(form.fixmod.value != ''){
	//	alert(form.fixmod.value);
	//	return;
	//}



	if( !validateText(form.user.value, form.title.value ) ){
		alert("Sorry! Character ' | ' and ' & ' cannot be used in UserName or Search Title field.");
		return;
	}
	
	if( form.ms_format.value == '' ){
		alert("Please select Data format.");
		return;
	}

	if( form.ms_instrument.value == '' ){
		alert("Please select Instrument type.");
		return;
	}

	if( form.ms_file.value == '' ){
		alert("Please select MS/MS Data file.");
		return;
	}

	if( form.database.value == '' && form.fasta.value == '' ){
		alert("Please specify Database or upload protein database file(*.fasta).");
		return;
	}
/*	if( !validateDBFormat(form.fasta.value) ){
		alert("Please specify Database or upload protein database file(*.fasta).");
		return;
	}
*/
	if( !validateDataFormat(form.ms_file.value, form.ms_format.value) ){
		alert("Specified Data Format and MS/MS Data are inconsistent. Please check it.");
		return;
	}
	
	if( !validateTolerance(form.pept_tolerance.value, form.frag_tolerance.value) ){
		alert("Peptide Tol. and Fragment Tol. should be real number.");
		return;
	}

	if( !validateTolerance(form.min_modified_mass.value, form.max_modified_mass.value) ){
		alert("Min/Max Modified Mass should be real number.");
		return;
	}

	if( !validateModifiedMass(form.min_modified_mass.value, form.max_modified_mass.value) ){
		alert("Min Modified Mass cannot be larger than Max Modified Mass.");
		return;
	}

	form.submit();
}

function validateText(user, title) {
	if( user.indexOf("|") > -1 || title.indexOf("|") > -1 ) return false;
	if( user.indexOf("&") > -1 || title.indexOf("&") > -1 ) return false;
	return true;
}

function validateTolerance(pept, frag) {
	if( pept == '' || frag == '' )
		return false;
	var ex = /^[+-]?\d*\.*\d*$/;
	if( !ex.test(pept) || !ex.test(frag) )
//	if( isNaN( parseFloat(pept) ) || isNaN( parseFloat(frag) ) )
		return false;
	return true;
}

function validateModifiedMass(min, max) {	
	if( parseFloat(min) > parseFloat(max) )
		return false;
	return true;
}

function validateDataFormat(data, format) {	
	var tmp = data.substring(data.lastIndexOf(".")+1).toLowerCase();
	if( tmp == 'mgf' || tmp == 'pkl' || tmp == 'dta' ){
		if( tmp != format )
			return false;
	}
	return true;
}
/*function validateDBFormat(data) {	
	var tmp = data.substring(data.lastIndexOf(".")+1).toLowerCase();
	if( tmp != 'fasta' ){
		return false;
	}
	else{
		return true;
	}
	
}
*/
function changeDB(){
	var form = document.search;
	if( form.database.value != '' )
		form.fasta.disabled = true;
	else form.fasta.disabled = false;
}

var xmlhttp;
var fixed = <%
	if (state != null)
	{
		ResultSet rs = state.executeQuery("select count(1) from px_enzyme where user_id=0;");
		if (rs.next())
		{
			out.print("" + rs.getInt(1));
		}
		else
			out.print("0");
		rs.close();
	}
	else
		out.print("0");
%>;
function enzymeList() {
	if (xmlhttp == null)
	{
		if (window.XMLHttpRequest)
			xmlhttp = new XMLHttpRequest();
		else
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp == null)
		return;
	
	//xmlhttp.onreadystatechange = stateChanged;
	xmlhttp.open("GET", "user_enzyme.jsp", false);
	xmlhttp.send(null);
	stateChanged();
}

function stateChanged() {
	var obj = document.search.enzyme;
	if (xmlhttp.readyState == 4)
	{
		var length = obj.length;
		var text = xmlhttp.responseText;
		var pos = -1;
		for (var i = 0; i < text.length; i++)
		{
			if (text.charAt(i) != ' ' && text.charAt(i) != '\n' && text.charAt(i) != '\r' && text.charAt(i) != '\t')
			{
				pos = i;
				break;
			}
		}
		if (pos >= 0)
			text = text.substr(pos);
		else
			text = '';
		var nodes = text.split(',');
		for (var i = 0; i < nodes.length; i++)
		{
			var values = nodes[i].split(':');
			if (values[0] != null && values[0] != '')
			{
				if (i + fixed < length)
				{
					if (values[1] != obj.options[i + fixed].value)
						obj.options[i + fixed] = new Option(values[0], values[1]);
				}
				else
					obj.options.add(new Option(values[0], values[1]));
			}
		}
		if (nodes.length > 0)
		{
			for (var i = fixed + nodes.length - 1; i < length; i++)
				obj.options.remove(i);
		}
		//for (var i = fixed; i < length; i++)
		//	obj.options.remove(i);
		//obj.options.add(new Option("added", "0"));
	}
}
</script> --%>

<table width="990" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="190" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		<!-- left menu -->
	<jsp:include page="../inc/livesearch_menu.jsp" flush="true" />
	</td>
    <td height="10" valign="top"><table border="0" cellspacing="0" cellpadding="0" id="TitTable">
      <tr>
        <td style="padding-left:15px;"><font color="#0033FF" size="3"><b>MODPlus</b>&nbsp;&nbsp;&nbsp;</font>ms/ms search ( <font color="#000000">version 
				<%-- 졸프 주석 <%=version%> --%>
				</font> )</td>
        <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
<%-- 졸프 주석처리
<%
	if (id != null && id.compareTo("4") != 0)
	{
%> --%>
          <tr>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="history.jsp" class="menu">History</a> &nbsp; &nbsp;</td>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="/login.jsp?action=logout" class="menu">Logout</a> &nbsp; &nbsp;</td>
          </tr>
<%-- 졸프 주석처리<%
	}
%> --%>
        </table></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
<form name="search" method="post" action="process.jsp" enctype="multipart/form-data">
<!--form name="search" method="get" action="javascript:checkSubmit();" enctype="multipart/form-data"-->
<input type="hidden" name="engine" value="modeye"/>
    <td valign="top" style="padding:10px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
<%-- 
졸프 주석처리 <%
	String userName = "";
	if (state != null)
	{
		ResultSet rs = state.executeQuery("select name from px_account where id=" + id + ";");
		if (rs.next())
			userName = rs.getString(1);
	}
%> --%>
	  <tr>
        <th><div align="right"><a href="/help.jsp#NAME" class="thmenu">User Name</a> </div></th>
        <%-- 졸프주석 <td colspan="3"><input name="user" type="text" size="60" value="<%=userName%>" --%>
				  /></td>
      </tr>
      <tr>
        <th align="right"><div align="right"><a href="/help.jsp#TIT" class="thmenu">Search  Title</a> </th>
        <td colspan="3"><input name="title" type="text" size="60" /></td>
      </tr>
      <tr>
	    <th><div align="right"><a href="/help.jsp#DATA_FORMAT" class="thmenu">Data Format</a> </div></th>
			<td><select name="ms_format">
				<option value="">Select -------------</option>
				<%-- 졸프주석 
				<option value="mgf" <% if (dataFormat.compareTo("mgf") == 0) out.print("selected"); %>>Mascot (*.mgf)</option>
				<option value="dta" <% if (dataFormat.compareTo("dta") == 0) out.print("selected"); %>>Sequest (*.dta)</option>
				<option value="pkl" <% if (dataFormat.compareTo("pkl") == 0) out.print("selected"); %>>Micromass (*.pkl)</option>  --%>
			</select></td>
        <th><div align="right"><a href="/help.jsp#INST" class="thmenu">MS Resolution</a> </div></th>
        <td><select name="ms_resolution">
			<!--option value="">Select -</option-->
			<%-- ㅈㅍㅈㅅ <!--option value="low" <% if (msResolution.compareTo("low") == 0) out.print("selected"); %>>Low</option--> --%>
			<option value="high">High</option>
			<%-- 졸프 주석 <option value="low" <% if (msResolution.compareTo("low") == 0) out.print("selected"); %>>Low</option> --%>
          </select></td>
      </tr>
	  
	  <tr>
        <th><div align="right"><a href="/help.jsp#INST" class="thmenu">MS/MS Instrument</a> </div></th>
        <td><select name="ms_instrument">
			<option value="">Select ---</option>
			<%-- 졸프주석  <option value="ESI-ION-TRAP" <% if (instrument.compareTo("ESI-ION-TRAP") == 0) out.print("selected"); %>>ESI-TRAP</option> --%>
			<%-- 졸프주석 <option value="QTOF" <% if (instrument.compareTo("QTOF") == 0) out.print("selected"); %>>HCD/QTOF</option> --%>
          </select>
        </td>
		<th><div align="right"><a href="/help.jsp#INST" class="thmenu">MS/MS Resolution</a> </div></th>
        <td><select name="msms_resolution">
			<!--option value="">Select -</option-->
			<%-- ㅈㅍㅈㅅ <!--option value="low" <% if (msmsResolution.compareTo("low") == 0) out.print("selected"); %>>Low</option--> --%>
			<option value="high">High</option>
			<%-- ㅈㅍㅈㅅ<option value="low" <% if (msmsResolution.compareTo("low") == 0) out.print("selected"); %>>Low</option>			 --%>
          </select></td>
      </tr>
     
	  <tr>
        <th><div align="right"><a href="/help.jsp#MSMS" class="thmenu">MS/MS Data</a> </div></th>
        <td colspan="3"><input name="ms_file" type="file" size="45" /> &nbsp; File size limit : 500MB</td>            
      </tr>
      <tr>
        <th><div align="right"><a href="/help.jsp#DB" class="thmenu">Database</a> </div></th>
        <td colspan="3"><select name="database" onchange="changeDB()">
            <option value="">Select ------------------------</option>
<%-- 졸프 주석처리 
<%
	if (state != null) {
		ResultSet rs = state.executeQuery("select id, name from px_database");
		while (rs.next()) {
%>
			<option value="<%=rs.getInt(1)%>"><%=rs.getString(2)%></option>
<%
		}
		rs.close();
	}
%> --%>
			<!--option value="Swissprot_Human_57.12">Swissprot_Human_57.12</option>
			<option value="Swissprot_Mouse_57.12">Swissprot_Mouse_57.12</option>
			<option value="Swissprot_Rattus_57.12">Swissprot_Rattus_57.12</option-->
        </select>&nbsp;&nbsp;<!--input name="decoy" type="checkbox" class="CHECK" value="O" /><font size="2" color="blue"> Search with decoy<font--></td>    
      </tr>
      <tr>	  
        <th rowspan="1"><div align="right"><a href="/help.jsp#DB" class="thmenu">Local  DB</a> </div></th>
		<td colspan="3"><input name="fasta" type="file" size="45" /> &nbsp; File size limit : 100MB&nbsp;&nbsp;<!--input name="fasta_decoy" type="checkbox" class="CHECK" value="O" /><font size="2" color="blue"> Search with decoy<font--></td>
	  </tr>	  
	  <!--tr>
		  <td colspan="3"><input name="user_decoy" type="file" size="45" />&nbsp;&nbsp;<font size="2" color="blue">or upload user's decoy</font></td>
      </tr-->
	  <tr>	  
        <th><div align="right"><a href="/help.jsp#TARDEC" class="thmenu">Target-Decoy </div></th>
		<td colspan="3"><input name="decoy" type="checkbox" class="CHECK" value="O" /><font size="2" color="blue"> Search with decoy proteins<font></td>
	  </tr> 
      <tr>
        <th><div align="right"><a href="/help.jsp#ENZYME" class="thmenu">Enzyme</a> </div></th>
        <%--ㅈㅍㅈㅅ <td colspan="3"><select name="enzyme" onclick="enzymeList();"> --%>
<%-- 
졸프 주석처리
<%
	if (state != null)
	{
		ResultSet rs = state.executeQuery("select id, name from px_enzyme where user_id=0;");
		while (rs.next())
		{
%>
			<option value="<%=rs.getInt(1)%>" <% if (enzyme.compareTo("" + rs.getInt(1)) == 0) out.print("selected"); %>><%=rs.getString(2)%></option>
<%
		}
		rs.close();
		rs = state.executeQuery("select id, name from px_enzyme where user_id=" + id + ";");
		while (rs.next())
		{
%>
			<option value="<%=rs.getInt(1)%>" <% if (enzyme.compareTo("" + rs.getInt(1)) == 0) out.print("selected"); %>><%=rs.getString(2)%> *</option>
<%
		}
		rs.close();
	}
%>             --%>
			<!--option selected="selected">Trypsin</option>
			<option>Pepsin</option>
			<option>Glu-C</option-->
        <%-- 졸프 주석처리
				</select>&nbsp;&nbsp;<input name="add_enzyme" type="button" class="btnBlueSmall" value=" + " <%if (id.compareTo(anony) != 0) {%>onclick="window.open('enzyme_list.jsp', '', 'resizable=0, width=500, height=500, scrollbars=yes')"<%}%> />&nbsp;&nbsp;<font size="2" color="blue"><%if (id.compareTo(anony) == 0) {%>Login<%} else {%>Click<%}%> to add a new enzyme rule</font></td> --%>
	  </tr>
	  <tr>
        <th><div align="right"><a href="/help.jsp#MISSCLEAVE" class="thmenu">Max No. of Missed Cleavages</a> </div></th>
        <td><select name="missed_cleavage">
            
			<%-- ㅈㅍㅈㅅ
			<option <% if (missedCleavage.compareTo("0") == 0) out.print("selected"); %>>0</option>
			<option <% if (missedCleavage.compareTo("1") == 0) out.print("selected"); %>>1</option>
			<option <% if (missedCleavage.compareTo("2") == 0) out.print("selected"); %>>2</option>
			<option <% if (missedCleavage.compareTo("3") == 0) out.print("selected"); %>>3</option>
			<option <% if (missedCleavage.compareTo("4") == 0) out.print("selected"); %>>4</option>
			<option <% if (missedCleavage.compareTo("5") == 0) out.print("selected"); %>>5</option>
        </select></td>
		<th><div align="right"><a href="/help.jsp#NTT" class="thmenu">Min No. of Enzyme Termini</a> </div></th>
        <td><select name="ntt">           
			<option <% if (minNumEnzTerm.compareTo("1") == 0) out.print("selected"); %>>1</option>
			<option <% if (minNumEnzTerm.compareTo("2") == 0) out.print("selected"); %>>2</option> --%>
        </select></td>
      </tr>
      <tr>
        <th><div align="right"><a href="/help.jsp#PEPTTOL" class="thmenu">Peptide  Tol.</a> </div></th>
        <%-- ㅈㅍㅈㅅ <td>&#177; <input name="pept_tolerance" type="text" size="10" value="<%=pTolerance%>" /> <select name="unit"> --%>
            <%-- ㅈㅍㅈㅅ <option <% if (pUnit.compareTo("Da") == 0) out.print("selected"); %>>Da</option> --%>
			<%-- ㅈㅍㅈㅅ <option <% if (pUnit.compareTo("ppm") == 0) out.print("selected"); %>>ppm</option>			 --%>
        </select></td>
		<th><div align="right"><a href="/help.jsp#ISOERR" class="thmenu">#<sup>13</sup>C (Isotope error)</a> </div></th>
        <td>&nbsp;&nbsp;Min	
		<select name="min_isotope">
		    <%-- ㅈㅍㅈㅅ <option <% if (minIE.compareTo("-1") == 0) out.print("selected"); %>>-1</option>
		    <option <% if (minIE.compareTo("0") == 0) out.print("selected"); %>>0</option>
			 --%>
		</select>
		&nbsp;&nbsp;Max
		<select name="max_isotope">		    
		    <%-- ㅈㅍㅈㅅ <option <% if (maxIE.compareTo("0") == 0) out.print("selected"); %>>0</option>
			<option <% if (maxIE.compareTo("1") == 0) out.print("selected"); %>>1</option>
			<option <% if (maxIE.compareTo("2") == 0) out.print("selected"); %>>2</option>
			<option <% if (maxIE.compareTo("3") == 0) out.print("selected"); %>>3</option> --%>
		</select>
		</td>
        <td></td>
        
      </tr>
	    <th><div align="right"><a href="/help.jsp#FRAGTOL" class="thmenu">Fragment Tol.</a> </div></th>
        <%-- ㅈㅍㅈㅅ <td colspan="3">&#177; <input name="frag_tolerance" type="text" size="10" value="<%=fTolerance%>" /> Da</td> --%>
	  <tr>
	  </tr>
      <tr>
        <th><div align="right"><a href="/help.jsp#MODRANGE" class="thmenu">Modified  Mass</a> </div></th>
        <%--ㅈㅍㅈㅅ 
				<td>Min&nbsp;&nbsp;&nbsp;<input name="min_modified_mass" type="text" size="10" value="<%=minMM%>" /> Da</td>
        <td colspan="2">Max&nbsp;&nbsp;&nbsp;<input name="max_modified_mass" type="text" size="10" value="<%=maxMM%>" /> Da</td> --%>
      </tr>
      <tr>
	  <!--tr>
        <th><div align="right"><a href="/help.jsp#ALKYLATION" class="thmenu">Cysteine alkylation</a> </div></th>
        <td><select name="cysteine alkylation">
            <option value="">Select -------------</option>
			<option value="Carbamidomethyl">Carbamidomethyl</option>
			<option value="Carboxymethyl">Carboxymethyl</option>
			<option value="Direct input">Direct input</option>
        </select></td>
		<td colspan="2">Name&nbsp;&nbsp;<input name="alkylation_name" type="text" size="10" />
		&nbsp;&nbsp;Mass&nbsp;&nbsp;<input name="alkylation_mass" type="text" size="10" /> Da </td>    
      </tr-->
<%
/*	int varMods = 0;
	int fixedMods = 0;
	if (state != null)
	{
		ResultSet rs = state.executeQuery("select count(1) from px_user_modification where user_id=" + id + " and variable=1 and engine=0;");
		if (rs.next())
			varMods = rs.getInt(1);
		rs.close();
		rs = state.executeQuery("select count(1) from px_user_modification where user_id=" + id + " and variable=0 and engine=0;");
		if (rs.next())
			fixedMods = rs.getInt(1);
		rs.close();
	}*/
%>
		
        <th><div align="right"><a href="/help.jsp#FIXMOD" class="thmenu">Fixed Modifications</a> </div></th>
        <td  height="auto" style="word-break:break-all;"><iframe  frameborder="0" height="23" width="150" marginheight="0" marginwidth="0" src="fix_ptms.jsp?engine=0"></iframe>
		<!-- <div id="RIGHT">
			<iframe frameborder="0" height="auto" width="500" scrolling="no" marginheight="0" marginwidth="0" src="var_ptms_list.jsp?var=0&engine=0"></iframe>
		</div> -->
		</td>



        <th><div align="right"><a href="/help.jsp#VARMOD" class="thmenu">Variable Modifications</a> </div></th>
        <td><iframe frameborder="0" height="23" style="word-break:break-all;" width="150" scrolling="no" marginheight="0" marginwidth="0" src="var_ptms.jsp?engine=0"></iframe></td>
      </tr>
	  <!--tr>	  
        <th><div align="right"><a href="/help.jsp#MODMAP" class="thmenu">MODmap </div></th>
		<td colspan="3"><input name="modmap" type="checkbox" class="CHECK" value="O" /><font size="2" color="blue"> Prediction of unknown modifications<font></td>
	  </tr-->
	 
	  <!--tr>	  
        <th><div align="right"><a href="/help.jsp#MULTISTAGE" class="thmenu">Multi-Stages Search </div></th>
		<td colspan="3"><input name="multistage" type="checkbox" class="CHECK" value="O" /><font size="2" color="blue"> Two-stages ( protein identification -&gt; peptide identification )<font></td>
	  </tr-->

    </table>
      <br>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
<%-- 졸프 주석처리
<%
	if ( true ) // id.compareTo(anony) == 0)
	{
%>
		<td align="left"><a href="result.jsp?file=50l4Gh80mJ1Q4o5FgSMnPo647UD217oh4E35174ln46038SP2125">Run sample data<a/>&nbsp;&nbsp;(<a class="under" href="/download/test_data.zip"> Download sample data <a/>)
<%
	}
%> --%>

          <td align="right"> 
		      <%-- ㅈㅍㅈㅅ <!-- <input name="Reset" type="reset" class="btn" onClick="reset('<%=id%>');" value="Reset"> --> --%>
			  <%-- ㅈㅍㅈㅅ <input name="Reset" type="reset" class="btn" onClick="javascript:reset()" value="Reset">
		      <input name="Submit" type="button" class="btn" onClick="process()" value="Submit"></td> --%>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<%--  졸프 주석처리
<%
	if (conn != null)
	{
		state.close();
		conn.close();
	}
%> --%>

<jsp:include page="../inc/footer.jsp" flush="true" />

