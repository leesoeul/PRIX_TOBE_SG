<jsp:include page="/inc/misc.jsp" flush="true" />
<!-- header ��-->

<!--html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>PRIX - PRoteome InformatiX</title>
<link href="/css/home.css" rel="stylesheet" type="text/css">
</head>

<body-->


<%@ page import="prix.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.io.File" %>

<%
// comparators
class PepMatchComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			if (diff == 0)
				diff = r.getPeptideLines().length - l.getPeptideLines().length;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class PSMMatchComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			diff = r.getPeptideLines().length - l.getPeptideLines().length;
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class SeqCovComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			boolean[] hits = l.getCoverageCode();
			int lc = 0, rc = 0;
			for (int i = 0; i < hits.length; i++)
				if (hits[i])
					lc++;
			int ll = hits.length;
			hits = r.getCoverageCode();
			for (int i = 0; i < hits.length; i++)
				if (hits[i])
					rc++;
			int rl = hits.length;
			diff = rc * ll - lc * rl;
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			//return diff;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class IDComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (r == null)
			return 1;
		else if (l == null)
			return -1;
		else
		{
			diff = l.getName().compareTo(r.getName());
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class PeptideComparator implements Comparator<PeptideLine> {
	public int compare(PeptideLine l, PeptideLine r) {
		int diff = l.getStart() - r.getStart();
		if (diff == 0)
		{
			diff = l.getEnd() - r.getEnd();
			if (diff == 0)
				diff = l.getIndex() - r.getIndex();
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
		
	}
}

class PeptideDecoyComparator implements Comparator<PeptideLine> {
	public int compare(PeptideLine l, PeptideLine r) {
		int diff = (int)((r.getScore() - l.getScore()) * 10000);
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

	response.setCharacterEncoding("UTF-8");

	final String anony = "4";
	String id = (String)session.getAttribute("id");
	if (id == null){
		session.setAttribute("id", anony);
		id = (String)session.getAttribute("id");
	}

	String fileName = request.getParameter("file");
	JobProcess jobState = new JobProcess(fileName, id);

	ProteinSummary summary = new ProteinSummary();
	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	String databasePath = "";
	if (conn != null)
	{
		Statement state = conn.createStatement();
		ResultSet rs = null;
		
		// verification
		Integer level = (Integer)session.getAttribute("level");
		if (level == null) {
			try {
				rs = state.executeQuery("select level from px_account where id=" + id);
			} catch (Exception e) {
				rs = state.executeQuery("select level from px_account where id=" + id);
			}
			if (rs.next())
			{
				level = rs.getInt(1);
				session.setAttribute("level", level);
			}
			rs.close();
		}
		if (level == null) level = 0;

		try {
			rs = state.executeQuery("select user_id from px_search_log where user_id=" + jobState.getUser() + " and result=" + jobState.getIndex() + ";");
		} catch (Exception e) {
			rs = state.executeQuery("select user_id from px_search_log where user_id=" + jobState.getUser() + " and result=" + jobState.getIndex() + ";");
		}

		if( !jobState.isNumJob() && jobState.isCorrectJob() && !rs.next() && !jobState.checkJobCompleted() )
		{
			rs.close();
			state.close();
			conn.close();
		%>
			<table border="0" width="100%">
			<tr>
			<td width="5">&nbsp;</td>
			<td></td>
			</tr>
			<tr>
			<td>&nbsp;</td>
			<td>

			<% if( jobState.getProgress() < 0 ) { %>	
				<p><font class="blue"><b>NOTICE:</b> </font><font class="red"><%=jobState.getMessage()%></font><br> 
				We are sorry for this inconvenience. Please re-submit your job.<br>
				If you have the same problem again, please contact us.<br>
				Thank you for your cooperation and patience.</p>	
			<% } else { %>
				<p>
				<font class="blue">Search in progress......</font> ( <font class="red"><%=jobState.getProgress()%>% complete</font> )<br>
				<!--- To stop, click <a href="cancel.jsp?job=<%=fileName%>">here</a> -->


				<font class="blue"><br><br>Job Information:<br></font>
				<%=jobState.getDescription()%>
				</p>
			<%
				response.setHeader("Refresh", "10; URL=result.jsp?file="+fileName);
			}
			return;
		}

	    else if( !jobState.isCorrectJob() || ( jobState.isNumJob() && level <= 1 && ( id.compareTo("4") == 0 || !rs.next() ) ) )
		{
			rs.close();
			state.close();
			conn.close();
		%>
		<table border="0" width="100%">
		<tr>
		<td width="5">&nbsp;</td>
		<td></td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td>
		<% if( !jobState.isCorrectJob() ) { %>	
			<p>Your link is NOT valid. </p>	
		<% } else { %>
			<p>You are NOT authorized to see this result. </p>
		<% }
			return;
		}
		rs.close();
		
		
		fileName = jobState.getIndex();
		
		// read prix result file
		String pwd = "";
		rs = state.executeQuery("select content from px_data where id=" + fileName + ";");
		if (rs.next())
		{
			pwd = rs.getString(1);
			
			File file = new File(pwd);
			InputStream is = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(is);
			summary.read(reader);
		}
		rs.close();

		// read search database file
		rs = state.executeQuery("select content from px_data where id=" + summary.getDatabasePath() + ";");
		if (rs.next())
		{	
			pwd= rs.getString(1);
			File file = new File(pwd);
			InputStream is = new FileInputStream(file);
			summary.readProtein(is);			
			databasePath = rs.getString(1);
		}
		rs.close();


		state.close();
		conn.close();
	}
	boolean isDBond = false;
	if (summary.getEngineName() != null && summary.getEngineName().compareToIgnoreCase("DBOND") == 0)
		isDBond = true;

	String minScore = request.getParameter("minscore");
	String targetDecoy = request.getParameter("targetdecoy");
	String minFDR = request.getParameter("minfdr");
	String maxHit = request.getParameter("maxhit");
	if (minScore == null)
		minScore = "0.1";
	if (minFDR == null)
		minFDR = "1";
	if (maxHit == null)
		maxHit = "All";
	if (targetDecoy == null && summary.isTargetDecoyed())
		targetDecoy = "O";
	
	int maxProteins = 0;
	if (maxHit.compareTo("All") != 0)
		maxProteins = Integer.parseInt(maxHit);
	double minPeptideScore = Double.parseDouble(minScore);
	boolean useTargetDecoy = (targetDecoy != null);
	double minFDRate = Double.parseDouble(minFDR) / 100;
	
	Modification[] mods = summary.getModifications();
	ProteinInfo[] proteins = summary.getProteins();

	if (useTargetDecoy)
	{
		
		double[] scores = summary.getDecoyScores();
		int fdr = Integer.parseInt(minFDR) - 1;
		if (fdr >= scores.length)
			fdr = scores.length - 1;
		minPeptideScore = scores[fdr];
		
		minScore = Double.toString(minPeptideScore);
	}
	if (proteins != null)
	{
		for (int i = 0; i < proteins.length; i++)
		{
			if (proteins[i] != null)
			{
				proteins[i].makePeptideLines(summary, mods, minPeptideScore, isDBond, i, true);
			}
		}
	}
		
	// sort here
	String sort = request.getParameter("sort");
	if (sort == null)
		sort = "pm";
	Comparator<ProteinInfo> by = new PepMatchComparator();
	if (sort.compareTo("pi") == 0)
		by = new IDComparator();
	else if (sort.compareTo("sc") == 0)
		by = new SeqCovComparator();
	else if (sort.compareTo("psm") == 0)
		by = new PSMMatchComparator();
	if (proteins != null)
		Arrays.sort(proteins, by);
%>

<script type="text/javascript">
function checkAll(obj, flag) {
	if (obj != null)
	{
		if (obj.length > 0)
		{
			for (i = 0; i < obj.length; i++)
				obj[i].checked = flag;
		}
		else
			obj.checked = flag;
	}
}

var checkAllClickProtein = false;
function checkAllProtein() {
	checkAllClickProtein = !checkAllClickProtein;
	checkAll(document.result.protein_list, checkAllClickProtein);
	checkAll(document.result.peptide_list, checkAllClickProtein);
	return true;
}
function uncheckAllProtein(obj) {
	if (checkAllClickProtein && !obj.checked)
	{
		checkAllClickProtein = false;
		document.result.check_all.checked = false;
	}
}

function checkMutual(group, obj) {
	var target;
	if (group == 'protein')
		target = document.result.peptide_list;
	else
		target = document.result.protein_list;
	
	if (target.length > 0)
	{
		for (var i = 0; i < target.length; i++)
		{
			if (target[i].value == obj.value)
				target[i].checked = obj.checked;
		}
	}
	else
		target.checked = obj.checked;
	uncheckAllProtein(obj);
}

function getChecked(form) {
	var obj = form.protein_list;
	var list = '';
	for (i = 0; i < obj.length; i++)
	{
		if (obj[i].checked)
			list += obj[i].value + '|';
	}
	return list;
}

function checkSubmit() {
	var form = document.view_as;
	//if (!confirm("want to refresh?"))
	//	return;
<%	
	if (!isDBond)
	{
%>
		if ( form.minscore.value < 0 || form.minscore.value > 1 )
		{
			alert("PSM min score should be between 0.0 and 1.0.");
			return;
		}
<%	
	}
%>
	
<%
	if (summary.isTargetDecoyed())
	{
%>
	if (form.minfdr.value < 0 || form.minfdr.value > 100)
	{
		alert("min FDR should be between 0.0 and 100.0.");
		return;
	}
<%
	}
%>
	if (!(form.maxhit.value == 'All' || form.maxhit.value > 0))
	{
		alert("Max No. of protein hits should be positive number or 'All'");
		return;
	}
	form.action = "result.jsp";
	form.submit();
}

function doSearch() {
	var form = document.result;
	form.method = "post";
	form.action = "dbond_search.jsp";
	
	var count = 0;
	if (form.protein_list.length > 0)
	{		
		for (var i = 0; i < form.protein_list.length; i++)
			if (form.protein_list[i].checked)
				count++;
	}
	else
	{
		if (form.protein_list.checked)
			count++;
	}
	if (count == 0)
	{
		alert("Please select proteins.");
		return;
	}
	form.submit();
}
</script>

<table border="0" width="100%">
	<tr>
		<td width="5">&nbsp;</td>
		<td></td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td>
			<p><h2><font size="5">PRIX Search Results</font></h2></p>

<br>
<table border="0">
	<tr>
		<td><b>Search Engine</b></td>
		<!--td>: <%=summary.getEngineName()%> ( version <%=summary.getVersion()%> )</td-->
		<td>: <%=("dbond".compareTo(summary.getEngineName())==0)? "dbond" : "modplus" %> ( version <%=("dbond".compareTo(summary.getEngineName())==0)? summary.getVersion() : "1.01" %> )</td>
	</tr>
	<tr>
		<td><b>Date</b></td>
		<td>: <%=summary.getDate()%></td>
	</tr>
	<tr>
		<td><b>User</b></td>
		<td>: <%=summary.getUserName()%></td>
	</tr>
	<tr>
		<td><b>Search title</b></td>
		<td>: <%=summary.getTitle()%></td>
	</tr>
	<tr>
		<td><b>MS/MS data</b></td>
		<td>: <%=summary.getFileName()%></td>
	</tr>
	<tr>
		<td><b>Database</b></td>
		<td>: <%=summary.getDatabaseName()%> ( <%=summary.getNumberOfUserProteins()%> proteins / <%=summary.getNumberOfUserResidues()%> residues )</td>
	</tr>

</table>
<br>
<br>

<p><font size="4"><b>Search Parameters</b></font></p>
<table border="0">
	<tr>
		<td><b>Enzyme</b></td>
		<td>: <%=summary.getEnzymeName()%></td>
	</tr>
	<tr>
		<td><b>Max Missed Cleavages</b></td>
		<td>: <%=summary.getMaxMissedCleavages()%></td>
	</tr>
	<tr>
		<td><b>No. of enzyme termini</b></td>
		<td>: <%=summary.getMinTerminiNumber()%></td>
	</tr>
	<tr>
		<td><b>Variable Modifications</b></td>
		<td>: <%
				if (mods.length >= 10)
				{
			%>
			<a href="modification.jsp?file=<%=fileName%>&type=0" target="_blank"><%=mods.length%></a>
			<%
				}
				else
				{
					for (int i = 0; i < mods.length; i++)
					{
						if (i > 0)
						{
			%>
			, 
			<%
						}
			%>
			<%=mods[i].getType()%> (<%=mods[i].getPosition()%>)
			<%
					}
				}
			%>
		</td>
	</tr>
	<%
	if (isDBond)
	{
	%>
	<tr>
		<td>&nbsp;</td>
		<td>:<font color="red"> * indicates disulfide bond.</font></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td><b>Fixed Modifications</b></td>
		<td>: <%
				mods = summary.getFixedModifications();
				if (mods.length >= 10)
				{
			%>
			<a href="modification.jsp?file=<%=fileName%>&type=1" target="_blank"><%=mods.length%></a>
			<%
				}
				else
				{
					for (int i = 0; i < mods.length; i++)
					{
						if (i > 0)
						{
			%>
			, 
			<%
						}
			%>
			<%=mods[i].getType()%> (<%=mods[i].getPosition()%>)
			<%
					}
				}
			%>
		</td>
	</tr>
<%
	if (!isDBond)
	{
%>
	<tr>
		<td><b>Modified Mass Range</b></td>
		<td>: <%=summary.getProteinMassMin()%> ~ <% double max = summary.getProteinMassMax(); if (max < 0) out.print("Unrestricted"); else out.print("+" + max); %> Da</td>
	</tr>
<%
	}
%>
	<tr>
		<td><b>Peptide Mass Tolerance</b></td>
		<td>: &#177; <%=summary.getPeptideTolerance()%> <%=summary.getPTUnit()%></td>
	</tr>
	<tr>
		<td><b>Fragment Mass Tolerance</b></td>
		<td>: &#177; <%=summary.getFragmentTolerance()%> <%=summary.getFTUnit()%></td>
	</tr>
	<tr>
		<td><b>Instrument type</b></td>
		<td>: <%=summary.getInstrumentName()%> <%if(!isDBond && summary.getMSResolution()!= null ){%> ( Resolution:  <%=summary.getMSResolution()%>-MS / <%=summary.getMSMSResolution()%>-MS/MS )<%}%></td>
	</tr>
	<tr>
		<td><b>Number of queries</b></td>
		<td>: <%=summary.getNumberOfQueries()%></td>
	</tr>
<%
	if (summary.isMODMapRun())
	{
%>
	<tr>
		<td><b>MODmap</b></td>
		<td>: <a href="#PTMAP">Checked</a></td>
	</tr>
<%
	}
%>
<%
	if (summary.isMultiStage())
	{
%>
	<tr>
		<td><b>MultiStages search</b></td>
		<td>: Checked</td>
	</tr>
<%
	}
%>
	<tr>
		<td><b>Decoy search</b></td>
		<td>: <%
	if (summary.isTargetDecoyed())
	{
		out.print("Checked ( " + summary.getDecoyFilePath() + " )");
	}
	else
		out.print("No");
		%></td>
	</tr>
<%
	if (summary.isTargetDecoyed())
	{
		int[] hits = summary.getDecoyHits();
%>
	<tr>
		<td>&nbsp;</td>
		<td>: 
			<font color="red">&nbsp;No. of identifications at FDR estimated by Target-Decoy</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;( <a href="/help.jsp#TARDEC" class="under">about FDR</a> )</font>
			<table border="0" align="left" cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid;">
				<tr>
					<td class="grayTD" align="right" bgcolor="#f3f3f3">FDR (%)</td>
	<%
			for (int i = 0; i < hits.length; i++)
			{
	%>
					<td class="grayTD" align="center">&nbsp;&nbsp;<%=i + 1%>&nbsp;&nbsp;</td>
	<%
			}
	%>
				</tr>
				<tr>
					<td class="grayTD" bgcolor="#f3f3f3">No. of identifications</td>
	<%
			for (int i = 0; i < hits.length; i++)
			{
	%>
					<td class="grayTD" align="center">&nbsp;<%=hits[i]%>&nbsp;</td>
	<%
			}
	%>
				</tr>
			</table>
		</td>
	</tr>
<%
	}
%>
</table>

<br>
<br>

<hr/>

<p><font size="4"><b>Search Summary</b></font></p>

<table border="0" bgcolor="#D2D2D2" width="90%">
<!--table border="0" width="90%" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid;"-->
<form name="view_as" action="javascript:checkSubmit();" method="post">
	<input type="hidden" name="file" value="<%=fileName%>"/>
	<tr>
		<td width="100" valign="top"><input type="submit" class="btnSmall" value="View As"/></td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;PSM min score <input type="text" name="minscore" value="<%=minScore%>"/>&nbsp;&nbsp;&nbsp;<font class="red">PSMs &gt; specified score would be summarized in result.</font><br/>
<%
	if (summary.isTargetDecoyed())
	{
%>
			<input type="checkbox" class="CHECK" name="targetdecoy" value="yes" <%=useTargetDecoy ? "checked" : ""%>/><b>By Target-decoy</b>
			&nbsp;&nbsp;&nbsp;&nbsp;min FDR (%) &nbsp; <input type="text" name="minfdr" value="<%=minFDR%>"/><br/><br/>
<%
	}
%>
			&nbsp;&nbsp;&nbsp;&nbsp;Max No. of protein hits <input type="text" name="maxhit" value="<%=maxHit%>"/> &nbsp;&nbsp;&nbsp;&nbsp; Sort by
			<select name="sort">
				<option value="pm" <%if (sort.compareTo("pm") == 0) out.print("selected");%>>Peptides matched</option>
				<option value="pi" <%if (sort.compareTo("pi") == 0) out.print("selected");%>>Protein ID</option>
				<option value="sc" <%if (sort.compareTo("sc") == 0) out.print("selected");%>>Sequence coverage</option>
				<option value="psm" <%if (sort.compareTo("psm") == 0) out.print("selected");%>>PSMs matched</option>
			</select>
		</td>
	</tr>
</form>
<form name="extract_as">
<script type="text/javascript">
function download()
{
	var url = '/downloader?path=<%=java.net.URLEncoder.encode(fileName)%>';
	if (document.extract_as.extract[0].checked)
		url = url + '&name=prix.xml&type=xml';
	else
		url = url + '&name=prix.csv&type=csv';
	window.open(url, 'Download');
	//alert(url);
}
</script>
	<tr>
		<td><input type="button" class="btnSmall" value="Extract As" onclick="download()"/></td>
		<td>&nbsp;&nbsp;<input type="radio" class="CHECK" name="extract" value="pepxml" checked/>XML (for viewer)&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" class="CHECK" name="extract" value="excel"/>CSV</td>
	</tr>
</form>
</table>

<br/><br/>
<table border="0" width="100%">
	<tr>
		<td>
			<font size="3"><b>Protein hits</b></font>
		</td>
	</tr>
<%
	if (!isDBond)
	{
%>
	<tr>
		<td>
			<div><a href="#in_depth">Check for in-depth search</a></div>
		</td>
	</tr>
<%
	}
%>
</table>
<!--form name="protein_hits"-->
<form name="result">
<table border="0">
	<tr>
		<th align="left"><input type="checkbox" class="CHECK" name="check_all" id="pr_check_all" onclick="checkAllProtein();"/></th>
		<th align="left"><font size="2">Protein ID</font></th>
		<!--th align="left"><font size="2">Description</font></th-->
	</tr>
<%
	mods = summary.getModifications();

	int num = 0;
	if (proteins != null)
	{
		for (int i = 0; i < proteins.length; i++)
		{
			if (maxProteins > 0 && num >= maxProteins)
				break;

			ProteinInfo info = proteins[i];
			if (info == null)
				continue;
			num++;

			PeptideLine[] peptides = info.getPeptideLines();
			if (peptides == null || peptides.length == 0)
				continue;
%>
	<tr>
		<td><input type="checkbox" class="CHECK" name="protein_list" value="<%=info.getName()%>" onclick="checkMutual('protein', this);"/></td>
		<td><a href="#protein<%=i%>"><%=info.getName()%></a></td>
		<!--td>&nbsp;<%=info.getDescription()%></td-->
	</tr>
	<tr>
		<td></td>
		<td><%=info.getDescription()%></td>
		<!--td>&nbsp;<%=info.getDescription()%></td-->
	</tr>
<%
		}
	}
%>
</table>
<!--/form-->
<br/>
<br/>
<table border="0" width="100%">
	<tr>
		<td>
			<font size="3"><b>Peptide hits</b></font>
		</td>
	</tr>
<%
	if (!isDBond)
	{
%>
	<tr>
		<td>
			<div><a href="#in_depth">Check for in-depth search</a></div>
		</td>
	</tr>
<%
	}
%>
</table>
<!--form name="peptide_hits"-->
<table border="0">
<%
	num = 0;
	if (proteins != null)
	{
		for (int i = 0; i < proteins.length; i++)
		{
			if (maxProteins > 0 && num >= maxProteins)
				break;

			ProteinInfo info = proteins[i];
			if (info == null)
				continue;

			PeptideLine[] peptides = info.getPeptideLines();
			//PeptideLine[] secondPeptides = info.getSecondPeptideLines();
			if (peptides == null || peptides.length == 0)
				continue;
			num++;

			Comparator<PeptideLine> byPL = new PeptideComparator();
			Arrays.sort(peptides, byPL);
			//if (isDBond)
			//	Arrays.sort(secondPeptides, byPL);

			boolean[] code = info.getCoverageCode();
			int coverage = 0;
			for (int j = 0; j < code.length; j++)
				if (code[j])
					coverage++;
%>
	<tr>
		<td><a name="protein<%=i%>"><input type="checkbox" class="CHECK" name="peptide_list" value="<%=info.getName()%>" onclick="checkMutual('peptide', this);"/></a>&nbsp;</td>
		<td><%=num%>.&nbsp;&nbsp;</td>
		<td colspan="3"><a href="protein.jsp?file=<%=fileName%>&name=<%=info.getName()%>&ms=<%=minPeptideScore%>&db=<%=isDBond%>" target="_blank"><%=info.getName()%></a></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td><%=info.getDescription()%></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td>&nbsp;&nbsp;<font color="red">Peptides matched:</font> <%=info.getNumberOfMatchedPeptides()%>&nbsp;&nbsp;
			&nbsp;&nbsp;<font color="red">PSMs matched:</font> <%=peptides.length%>&nbsp;&nbsp;
			&nbsp;&nbsp;<font color="red">Sequence coverage:</font> <%=code.length == 0 ? 0 : String.format("%.1f", (double)coverage * 100 / code.length) %>%</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td colspan="3">&nbsp;
			<table border="0">
				<tr>
					<th><font size="2">&nbsp;Index&nbsp;</font></th>
					<th><font size="2">&nbsp;Observed&nbsp;</font></th>
					<th><font size="2">&nbsp;Calc MW&nbsp;</font></th>
					<th><font size="2">&nbsp;DeltaM (Dalton)&nbsp;</font></th>
					<th><font size="2">&nbsp;
					<%
						if(isDBond)
						{
					%>	
						Score
					<%
						}else{
					%>  
						Probability
					<%
						}
					%>					
					&nbsp;</font></th>
					<th><font size="2">&nbsp;Start-End&nbsp;</font></th>
					<th align="left"><font size="2">Peptide&nbsp;</font></th>
					<th align="left"><font size="2">Modified</font></th>
				</tr>
<%
			for (int j = 0; j < peptides.length; j++)
			{
				PeptideLine peptide = peptides[j];
				SpectrumInfo spectrum = summary.getSpectrum(peptide.getIndex());
%>
				<tr>
					<!--td align="center">&nbsp;<% if (!isDBond) { %><a href="spectrum.jsp?pt=<%=summary.getPeptideTolerance()%>&ft=<%=summary.getFragmentTolerance()%>&si=<%=peptide.getIndex()%>&path=<%=fileName%>" target="_blank"><% } %><%=spectrum.getId()%>&nbsp;</a></td-->
					<td align="center">&nbsp;<%=spectrum.getId()%>&nbsp;</td>
					<td align="right">&nbsp;<%=peptide.getMass()%>(<%=peptide.getCharge()%>+)&nbsp;</td>
					<td align="center">&nbsp;<%=peptide.getMWCalc()%>&nbsp;</td>
					<td align="center">&nbsp;<%=peptide.getMWDelta()%>&nbsp;</td>
					<td align="center">&nbsp;<%=peptide.getScore()%></td>
					<td align="center">&nbsp;<%=peptide.getStart()%>-<%=peptide.getEnd()%>&nbsp;</td>
					<td align="left"><%=peptide.getPeptide()%>&nbsp;</td>
					<td><%=peptide.getModification()%></td>
				</tr>
<%
				if (isDBond && peptide.getSecond() != null)
				{
					PeptideLine secondPeptide = peptide.getSecond();
%>
				<tr>
					<td align="center">&nbsp;&nbsp;</td>
					<td align="center">&nbsp;&nbsp;</td>
					<td align="center">&nbsp;&nbsp;</td>
					<td align="center">&nbsp;&nbsp;</td>
					<td align="center">&nbsp;&nbsp;</td>
					<td align="center">&nbsp;<%=secondPeptide.getStart()%>-<%=secondPeptide.getEnd()%>&nbsp;</td>
					<td align="left"><%=secondPeptide.getPeptide()%>&nbsp;</td>
					<td><%=secondPeptide.getModification()%></td>
				</tr>
<%
				}
			}
%>
			</table><br>
		</td>
	</tr>
<%
		}
	}
%>
</table>
<!--/form-->

<%
	if (summary.isMODMapRun())
	{
%>
<table border="0" width="100%">
	<tr>
		<td>
			<font size="3"><b><a name="PTMAP">MODmap</a></b></font><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;PTM Frequency Matrix</font>&nbsp;&nbsp;&nbsp;&nbsp;( <a href="/help.jsp#MODMAP" class="under">about matrix</a> )
		</td>
	</tr>
	<tr>
		<td>
			<a href="modmap.jsp?path=<%=java.net.URLEncoder.encode(fileName)%>&name=<%=summary.getMODMapPath()%>">Download results</a>
		</td>
	</tr>
	<!--tr>
		<td>
			<div>Click to see modification frequency matrix.</div>
		</td>
	</tr-->
</table>
<table style="BORDER-BOTTOM: #d9dddc 2px solid; BORDER-TOP: #d9dddc 2px solid;">
	<tr>
		<th class="grayTH"><font size="2">Delta&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">Nterm&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">A&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">C&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">D&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">E&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">F&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">G&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">H&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">I&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">K&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">L&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">M&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">N&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">P&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">Q&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">R&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">S&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">T&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">V&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">W&nbsp;&nbsp;</font></th>
		<th class="grayTH"><font size="2">Y&nbsp;&nbsp;</font></th>
	</tr>
<%
		int count = summary.getModMapCount();
		for (int i = 0; i < count; i++)
		{
%>
	<tr>
<%
			int[] modMap = summary.getModMap(i);
			for (int j = 0; j < modMap.length; j++)
			{
%>
			<td align="center">
			<%	if( j == 0 )
				{
			%>
				<b><%=modMap[j]%></b>
			<%
				}
				else if( modMap[j] > 1 )
				{
			%>
					<font color="red"><b><%=modMap[j]%></b></font>
			<%	
				}
				else
				{
			%>		
					<%=modMap[j]%>
			<% 
				}
			%>
			</td>
<%
			}
%>
	</tr>
<%
		}
%>
</table>
<%
	}
%>

<br>
<%
	if (!isDBond)
	{
%>
<br>
<a name="in_depth">&nbsp;</a>
<hr/>

<input type="hidden" name="ms" value="<%=summary.getFilePath()%>"/>
<input type="hidden" name="db" value="<%=summary.getDatabasePath()%>"/>
<input type="hidden" name="msfile" value="<%=summary.getFileName()%>"/>
<input type="hidden" name="mstype" value="<%=summary.getFileType()%>"/>
<input type="hidden" name="dbfile" value="<%=summary.getDatabaseName()%>"/>
<input type="hidden" name="inst" value="<%=summary.getInstrumentName()%>"/>
<table border="0" width="100%">
	<tr>
		<td>
			<font size="4"><b>In-depth Search</b></font>
		</td>
	</tr>
	<tr>
		<td>
			<div>First, you have to select proteins.<br>
			This search will be conducted only for selected proteins</div>
		</td>
	</tr>
</table>
<table border="0" bgcolor="#D2D2D2" width="90%">
	<tr>
		<td width="100" valign="top"><input type="button" class="btnSmall" value="DBond Search" onclick="doSearch();"></td>
		<td>
			<!--input type="radio" class="CHECK" name="search" value="novel"/--> <font class="red">&nbsp;&nbsp;Disulfide bond analysis</font><br/>
			<!--input type="radio" class="CHECK" name="search" value="disulfide" checked="true"/> Disulfide analysis-->
		</td>
	</tr>
</table>
<%
	}
%>
		</td>
	</tr>
</table>
</form>

<jsp:include page="/inc/footer.jsp" flush="true" />
