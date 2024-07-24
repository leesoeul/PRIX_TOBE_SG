<%-- html? ?? ???~ ??? --%>

<jsp:include page="/inc/livesearch.jsp" flush="true" />
<!-- header ��-->

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%

	// User ID
	final String anony = "4";
	String id = (String)session.getAttribute("id");
	if (id == null){
		session.setAttribute("id", anony);
		id = (String)session.getAttribute("id");
	}
	
	// DB Connection
	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	Statement state = null;
	if (conn != null)
	{
		state = conn.createStatement();
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
		location.href="http://prix.hanyang.ac.kr/ACTG/search.jsp";
}

function process() {
	var form = document.search;
	// Exception Inspector

	// User name, and Title
	if( !validateText(form.user.value, form.title.value ) ){
		alert("Sorry! Character ' | ' and ' & ' cannot be used in UserName or Search Title field.");
		return;
	}


	// Peptide list
	if( form.peptideFile.value == '' ){
		alert("Please upload a peptide list.");
		return;
	}


	var size = document.getElementById("filePeptide").files[0].size;
	if( size > 1024*100){
		alert("Peptide list is limited by 100 KB");
		return;
	}

	// Is mutation checked?
	if( $("#divMutation").css("display") != "none"){
		if( $("#fileMutation").val().length == 0){
			alert("Please upload a vcf.");
			return;
		}

		size = document.getElementById("fileMutation").files[0].size;
		if( size > 20*1024*1024 ){
			alert("VCF file is limited by 20 MB");
			return;
		}
	}
	
	
	form.submit();
}

function validateText(user, title) {
	if( user.indexOf("|") > -1 || title.indexOf("|") > -1 ) return false;
	if( user.indexOf("&") > -1 || title.indexOf("&") > -1 ) return false;
	return true;
}

function validateDataFormat(data, format) {	
	var tmp = data.substring(data.lastIndexOf(".")+1).toLowerCase();

	if( tmp != format )
		return false;

	return true;
}


// When click the mutation check box
function mutationCheck(){
	if($("#divMutation").css("display") == "none"){
		$("#divMutation").css("display", "block");
	}else{
		$("#divMutation").css("display", "none");
		$("#fileMutation").val("");
	}
}

function changeMethod(){
	var method = $("#method").val();

	switch (method)	{
	case "PV": 
		showID("methodProteinDatabase");
		showID("methodVariantSpliceGraph");
		hideID("methodSixFrameTranslation");
		break;
	case "PS": 
		showID("methodProteinDatabase");
		showID("methodSixFrameTranslation");
		hideID("methodVariantSpliceGraph");
		break;
	case "VO": 
		showID("methodVariantSpliceGraph");
		hideID("methodProteinDatabase");
		hideID("methodSixFrameTranslation");
		break;
	case "SO":
		showID("methodSixFrameTranslation");
		hideID("methodProteinDatabase");
		hideID("methodVariantSpliceGraph");	
		break;
	}
}

function showID(ID){
	$("#"+ID).css("display", "table-row-group");
}

function hideID(ID){
	$("#"+ID).css("display", "none");
}

</script>

<table width="990" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="190" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		<!-- left menu -->
	<jsp:include page="/inc/livesearch_menu.jsp" flush="true" />
	</td>
    <td height="10" valign="top"><table border="0" cellspacing="0" cellpadding="0" id="TitTable">
      <tr>
        <td style="padding-left:15px;"><font color="#0033FF" size="3"><b>ACTG (Amino aCids To Genome)</b>&nbsp;&nbsp;&nbsp;</font>peptide mapping ( <font color="#000000">version 1.10</font> )</td>
        <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
<%
	if (id != null && id.compareTo("4") != 0)
	{
%>
          <tr>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="history.jsp" class="menu">History</a> &nbsp; &nbsp;</td>
            <td><img src="/images/top_icon_01.gif" width="2" height="9"> &nbsp;<a href="/login.jsp?action=logout" class="menu">Logout</a> &nbsp; &nbsp;</td>
          </tr>
<%
	}
%>
        </table></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
<form name="search" method="post" action="process.jsp" enctype="multipart/form-data">
<input type="hidden" name="engine" value="modeye">
    <td valign="top" style="padding:10px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">


<%
	// Get User Name
	String userName = "";
	if (state != null)
	{
		ResultSet rs = state.executeQuery("select name from px_account where id=" + id + ";");
		if (rs.next())
			userName = rs.getString(1);
	}
%>

	  <tbody><tr>
        <th><div align="right"><a href="help.jsp#NAME" class="thmenu">User Name</a> </div></th>
        <td colspan="3"><input name="user" type="text" size="60" value="<%=userName%>"></td>
      </tr>
      <tr>
        <th align="right"><div align="right"><a href="help.jsp#TIT" class="thmenu">Mapping Title</a> </div></th>
        <td colspan="3"><input name="title" type="text" size="60"></td>
      </tr>

	  <!-- MAPPING ENVIRONMENT -->
	  <tr>
	    <th colspan="4"><div align="center" style="margin: auto; width: 400px;" ><b class="thmenu" style="cursor:default">Mapping Environment</b></div></th>
      </tr>
	  <tr>
        <th><div align="right"><a href="help.jsp#PEP" class="thmenu">Peptide List</a> </div></th>
        <td colspan="3"><input id="filePeptide" name="peptideFile" type="file" size="45"> &nbsp; File size limit : 100KB</td>            
      </tr>
	  <tr>
        <th><div align="right"><a href="help.jsp#METHODS" class="thmenu">Mapping Method</a> <br>
		PV: Protein DB filtering / Variant splice graph mapping<br>
		PS: Protein DB filtering / Six-frame translation mapping<br>
		VO: Mapping variant splice graph only<br>
		SO: Mapping six-frame translation only
		</div></th>
        <td colspan="3"><select id="method" name="method" onchange="changeMethod()">
            <option value="PV">PV</option>
			<option value="PS">PS</option>
			<option value="VO">VO</option>
			<option value="SO">SO</option>
        </select></td>    
      </tr>
	  <tr>	  
        <th><div align="right"><a href="help.jsp#IL" class="thmenu">Isoleucine is equivalent to leucine</a></div></th>
		<td colspan="3"><input id="IL" name="IL" type="checkbox" class="CHECK" value="yes"><font size="2" color="blue"> Apply</font></td>
	  </tr>
	  </tbody>
	  
	  <!-- PROTEIN DATABASE -->
	  <tbody id="methodProteinDatabase">
	  <tr>
	    <th colspan="4"><div align="center" style="margin: auto; width: 400px;" ><b class="thmenu" style="cursor:default">Protein DB Filtering</b></div></th>
      </tr>
	  <tr>
        <th><div align="right"><a href="help.jsp#PROTEIN" class="thmenu">Protein DB</a> </div></th>
        <td colspan="3"><select name="proteinDB" onchange="changeDB()">
			<option value="swissprot_2016_07.fasta">Swissprot 2016-07</option>
			<option value="uniprot_2016_07.fasta">Uniprot 2016-07</option>
        </select></td>    
      </tr>
	  <tr>
        <th><div align="right"><a href="help.jsp#SAV" class="thmenu">Filter out single AA variations</a></div></th>
		<td colspan="3"><input id="SAV" name="SAV" type="checkbox" class="CHECK" value="yes"><font size="2" color="blue"> Apply</font></td>
	  </tr>
	  </tbody>

	  <!-- VARIANT SPLICE GRAPH -->
	  <tbody id="methodVariantSpliceGraph">
	  <tr>
	    <th colspan="4"><div align="center" style="margin: auto; width: 400px;" ><b class="thmenu" style="cursor:default">Variant Splice Graph Mapping</b></div></th>
      </tr>
      <tr>
        <th><div align="right"><a href="help.jsp#VSG" class="thmenu">Variant Splice Graph</a> </div></th>
        <td colspan="3"><select name="variantSpliceGraphDB">
			<option value="GRCh38.Ensembl.83.ser">GRCh38, Ensembl Release 83</option>
			<option value="GRCh37.Ensembl.78.ser">GRCh37, Ensembl Release 78</option>
			<option value="GRCh37.Ensembl.75.ser">GRCh37, Ensembl Release 75</option>
			<option value="GRCh37.Ensembl.71.ser">GRCh37, Ensembl Release 71</option>
        </select></td>    
      </tr>
	   <tr>	  
        <th><div align="right"><a href="help.jsp#VCF" class="thmenu">Mutations (VCF) </a></div></th>
		<td colspan="3">
		<div><input name="mutation" type="checkbox" class="CHECK" value="yes" onclick="mutationCheck()"><font size="2" color="blue"> Apply </font></div>
		<div id="divMutation" style="display:none;"><input id="fileMutation" name="mutationFile" type="file" size="45"> File size limit : 20MB</div></td>
	  </tr> 
	  <tr>	  
        <th><div align="right"><a href="help.jsp#ES" class="thmenu">Exon Skipping </a></div></th>
		<td colspan="3"><input id="exonSkipping" name="exonSkipping" type="checkbox" class="CHECK" value="yes"><font size="2" color="blue"> Apply</font></td>
	  </tr> 
    	  <tr>	  
        <th><div align="right"><a href="help.jsp#AAD" class="thmenu">Alternative Acceptor & Donor </a></div></th>
		<td colspan="3"><input id="altAD" name="altAD" type="checkbox" class="CHECK" value="yes"><font size="2" color="blue"> Apply</font></td>
	  </tr> 
	  	  
	  </tr> 
	  </tr>
	  	  
	  <tr>	  
        <th><div align="right"><a href="help.jsp#INTRON" class="thmenu">Intron retention</a></div></th>
		<td colspan="3"><input id="intron" name="intron" type="checkbox" class="CHECK" value="yes"><font size="2" color="blue"> Apply</font> (it takes about 10 minutes)</td>
	  </tr>
	  </tbody>
	  <!-- SIX-FRAME TRANSLATION -->
	  <tbody id="methodSixFrameTranslation">
	  <tr>
	    <th colspan="4"><div align="center" style="margin: auto; width: 400px;" ><b class="thmenu" style="cursor:default">Six-frame Translation Mapping</b></div></th>
      </tr>
	  <tr>
        <th><div align="right"><a href="help.jsp#SIX" class="thmenu">Reference genome</a> </div></th>
        <td colspan="3"><select name="referenceGenome" onchange="changeDB()">
			<option value="GRCh38">GRCh38</option>
			<option value="GRCh37">GRCh37</option>
        </select></td>    
      </tr>
    </tbody></table>
</form>
      <br>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tbody><tr>


		<td align="left"><a class="under" href="ACTG_test_data.zip"> Download sample data </a>

          </a></td><td align="right"> 
			  <input name="Reset" type="reset" class="btn" onclick="javascript:reset()" value="Reset">
		      <input name="Submit" type="button" class="btn" onclick="process()" value="Submit"></td>
        </tr>
      </tbody></table></td>
  </tr>
</tbody></table>

<%
	if (conn != null)
	{
		state.close();
		conn.close();
	}
%>

<script>

(function init(){
	changeMethod();
})();

</script>

<jsp:include page="/inc/footer.jsp" flush="true" />

