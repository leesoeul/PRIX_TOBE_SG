<%-- html로 전환중 참고용도 --%>

<html xmlns="http://www.w3.org/1999/xhtml">

<%@ page import="prix.PrixDataWriter" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.PrintStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.io.FileReader"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileWriter"%>
<%@ page import="java.io.BufferedWriter"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.util.Enumeration"%>

<%	
String id = (String)session.getAttribute("id");

if (id == null)
	response.sendRedirect("../login.jsp?url=ACTG/search.jsp");


//MultipartRequest multi = new MultipartRequest(request);
String user = "";
//user = multi.getParameter("user");
//out.println(user);
String title = request.getParameter("title");
out.println("title:"+title);
// Environment
String method = "";
String peptideFile = "";
String IL = "";

// Protein DB
String proteinDB = "";
String SAV = "";

// Variant Splice Graph DB
String variantSpliceGraphDB = "";
String mutation = "";
String mutationFile = "";
String exonSkipping = "";
String altAD = "";
String intron = "";

// Six-frame translation
String referenceGenome = "";

Enumeration params = request.getAttributeNames();
while(params.hasMoreElements()){
String name = (String) params.nextElement();
out.println(name+": "+request.getParameter(name)+"\t");
}
out.println(request);


String processName = request.getParameter("process");
out.println("\nprocessname"+processName);

String line = "";
String rate = "0%";
boolean finished = false;
boolean failed = false;
String output = "";

final String logDir = "/home/PRIX/ACTG_log/";
final String dbDir = "/home/PRIX/ACTG_db/";

String processPath = logDir + processName;
if (request.getParameter("execute") == null)
{
	
	Date date = new Date();
	String key = id+"_"+date.getTime();
	processPath = "process_" + key + ".proc";
	processName = processPath;
	String xmlPath = "param_"+ key + ".xml";
	String proteinDBPath = "";
	String peptideFilePath = "";
	String variantSpliceGraphDBPath = "";
	String mutationFilePath = "";
	String referenceGenomePath = "";
	String outputPath = logDir;

	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	List items = upload.parseRequest(request);
	Iterator iter = items.iterator();
	while (iter.hasNext())
	{
		FileItem item = (FileItem)iter.next();

		String name = item.getFieldName();
		if (true)
		{
			// USER NAME
			if (name.compareTo("user") == 0){
				user = item.getString();
			}
			// TITLE
			else if(name.compareTo("title") == 0){
				title = item.getString();
			}
			// ENVIRONMENT
			else if(name.compareTo("method") == 0){
				method = item.getString();
			}else if(name.compareTo("peptideFile") == 0){
				peptideFile = "peptide_"+key+".txt";
				peptideFilePath = logDir + peptideFile;

				if (item.getSize() > 1024*100)
				{
					failed = true;
					output = "The size of peptide list should not exceed 1KB.";
					break;
				}
				// Save the peptide file given by the user.
				if (item.getString().length() > 0)
				{
					FileOutputStream fos = new FileOutputStream(peptideFilePath);
					OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
					InputStream is = item.getInputStream();
					while (is.available() > 0)
						writer.write(is.read());
					writer.close();
					fos.close();
					is.close();
				}

			}else if(name.compareTo("IL") == 0){
				IL = item.getString();
			}
			// Protein DB
			else if(name.compareTo("proteinDB") == 0){
				proteinDB = item.getString();
				proteinDBPath = dbDir + proteinDB;

			}else if(name.compareTo("SAV") == 0){
				SAV = item.getString();
			}
			// Variant Splice Graph
			else if(name.compareTo("variantSpliceGraphDB") == 0){
				variantSpliceGraphDB = item.getString();
				variantSpliceGraphDBPath = dbDir + variantSpliceGraphDB;

			}else if(name.compareTo("mutation") == 0){
				mutation = item.getString();
			}else if(name.compareTo("mutationFile") == 0){
				mutationFile = "mutation_"+key+".txt";
				mutationFilePath = logDir + mutationFile;

				if (item.getSize() > 20971520)
				{
					failed = true;
					output = "The size of VCF should not exceed 20MB.";
					break;
				}

				if (item.getString().length() > 0){
					FileOutputStream fos = new FileOutputStream(mutationFilePath);
					OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
					InputStream is = item.getInputStream();
					while (is.available() > 0)
						writer.write(is.read());
					writer.close();
					fos.close();
					is.close();
				}

			}else if(name.compareTo("exonSkipping") == 0){
				exonSkipping = item.getString();
			}else if(name.compareTo("altAD") == 0){
				altAD = item.getString();
			}else if(name.compareTo("intron") == 0){
				intron = item.getString();
			}
			// Six-frame translation
			else if(name.compareTo("referenceGenome") ==0){
				referenceGenome = item.getString();
				referenceGenomePath = dbDir + referenceGenome;
			}


		}

		// XML maker
		try{
			FileReader FR = new FileReader(dbDir+"template.xml");
			BufferedReader BR = new BufferedReader(FR);
			
			FileWriter FW = new FileWriter(logDir+xmlPath);
			BufferedWriter BW = new BufferedWriter(FW);

			String line_ = null;
			StringBuilder SB = new StringBuilder();

			while((line_ = BR.readLine()) != null){
				if(line_.contains("[METHOD]")){
					line_ = line_.replace("[METHOD]",method);
				}else if(line_.contains("[IL]")){
					line_ = line_.replace("[IL]",IL);
				}else if(line_.contains("[PEPTIDE_FILE]")){
					line_ = line_.replace("[PEPTIDE_FILE]",peptideFilePath);
				}else if(line_.contains("[PROTEIN_DB]")){
					line_ = line_.replace("[PROTEIN_DB]",proteinDBPath);
				}else if(line_.contains("[SAV]")){
					line_ = line_.replace("[SAV]",SAV);
				}else if(line_.contains("[VARIANT_SPLICE_GRAPH_DB]")){
					line_ = line_.replace("[VARIANT_SPLICE_GRAPH_DB]",variantSpliceGraphDBPath);
				}else if(line_.contains("[ALT_AD]")){
					line_ = line_.replace("[ALT_AD]",altAD);
				}else if(line_.contains("[EXON_SKIPPING]")){
					line_ = line_.replace("[EXON_SKIPPING]",exonSkipping);
				}else if(line_.contains("[INTRON]")){
					line_ = line_.replace("[INTRON]",intron);
				}else if(line_.contains("[MUTATION]")){
					line_ = line_.replace("[MUTATION]",mutation);
				}else if(line_.contains("[MUTATION_FILE]")){
					line_ = line_.replace("[MUTATION_FILE]",mutationFilePath);
				}else if(line_.contains("[REFERENCE_GENOME]")){
					line_ = line_.replace("[REFERENCE_GENOME]",referenceGenomePath);
				}else if(line_.contains("[OUTPUT]")){
					line_ = line_.replace("[OUTPUT]", outputPath);
				}

				BW.append(line_);
				BW.newLine();
			}

			BR.close(); FR.close();
			BW.close(); FW.close();
		}catch(IOException e){
			output = e+"\n";
		}

	}

	if (!failed)
	{

		Runtime runtime = Runtime.getRuntime();

		String[] command = {"/bin/bash", "-c", "java -Xmx10G -jar /usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/ACTG/ACTG_Search.jar "+logDir+xmlPath+" "+logDir+processPath };
		Process process = runtime.exec(command);
	}
}
else
{
	FileInputStream fis = new FileInputStream(processPath);
	StringWriter writer = new StringWriter();
	StringWriter allWriter = new StringWriter();
	while (fis.available() > 0)
	{
		char c = (char)fis.read();
		if(c == '\n'){
			line = writer.toString();

			if (line.indexOf("ERROR") >= 0 || line.indexOf("Exception") >= 0)
			{
				failed = true;
			}
			else if (line.startsWith("Elapsed Time"))
			{
				finished = true;
			}

			if(line.contains(logDir)){ line = line.replace(logDir,""); }
			if(line.contains(dbDir)){ line = line.replace(dbDir,"");}


			if(line.contains("%")){
				rate = line;
			}else{
				allWriter.append(line+"\n");
			}
			writer = new StringWriter();
		}else{
			writer.append(c);
		}


	}

	if (writer.toString().length() > 0)
		line = writer.toString();

	fis.close();
	output = allWriter.toString();

	
	if (finished) {

		Context initContext = new InitialContext();
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
		Connection conn = ds.getConnection();
		if (conn == null)
			response.sendRedirect("../login.jsp?url=ACTG/search.jsp");

		String prixIndex = processPath.replace("process_"+id+"_", "");
		prixIndex = prixIndex.replace(logDir,"");
		prixIndex = prixIndex.replace(".proc","");

		PreparedStatement ps = conn.prepareStatement("insert into px_search_log (user_id, title, date, msfile, db, result, actg, engine) values (" + id + ", '" + title.replace("'", "\\\'") + "',now(), 0, 0, 0,'"+prixIndex+"' ,'ACTG')");
		ps.executeUpdate();
		ps.close();
		conn.close();

		response.sendRedirect("result.jsp?index=" + prixIndex);
	}
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>PRIX - PRoteome InformatiX</title>
<link href="/css/home.css" rel="stylesheet" type="text/css">

</head>

<body>
<table width="100%" height="119" border="0" cellpadding="0" cellspacing="0" style=" margin-bottom:10px">

<tr>
<td height="88" background="/images/menu_bg.gif">
<table width="990" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="203"><img src="/images/ci.gif" width="196" height="88"></td>
<td><table width="100%" height="88" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="right" valign="top" style="padding-top:3px"><table  border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="62" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
<td width="45" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
<td width="68" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
<td width="50" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
<td width="60" height="14" background="/images/top_dot_01.gif" style="background-repeat:no-repeat ">&nbsp;</td>
</tr>
<tr>
<td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;
<% if( session.getAttribute("id") == null || session.getAttribute("id").equals("4") ) { %>
<a href="/login.jsp" class="top_menu">LOG&nbsp;&nbsp;I&nbsp;N</a>
<% } else {%>
<a href="/login.jsp?action=logout" class="top_menu">LOGOUT</a>
<% } %>
</td>
<td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/help.jsp" class="top_menu">HELP</a></td>
<td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/contact.jsp" class="top_menu">CONTACT</a></td>
<td><img src="/images/top_icon_01.gif" width="2" height="9">&nbsp;&nbsp;<a href="/index.jsp" class="top_menu">ADMIN</a></td>
<td> &nbsp;</td>
</tr>
</table></td>
</tr>

<tr>
<td valign="bottom"><table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><a href="/"><img src="/images/menu02_01.gif" width="142" height="29" border="0"></a></td>
<td><a href="/livesearch.jsp"><img src="/images/menu02_02.gif" width="135" height="29" border="0"></a></td>
<td><a href="/download.jsp"><img src="/images/menu02_03.gif" width="133" height="29" border="0"></a></td>
<td><a href="/publications.jsp"><img src="/images/menu02_04.gif" width="157" height="29" border="0"></a></td>
</tr>
</table></td>
</tr>

</table></td>
</tr>
</table>
</td>
</tr>

<tr>
<td height="25" align="right" background="/images/menu_bg2.gif"><img src="/images/menu_bg3.gif" width="276" height="25"></td>
</tr>

<tr>
<td height="6" background="/images/menu_bg4.gif"></td>
</tr>

</table>

<table width="95%" border="0" align="center" cellpadding="1">
<tr>
<td><font size="3">Processing... <font class="blue"><em><%=rate%></em></font></font></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><font class="drak"><pre id="content-"><%=output%></pre></font></td>
</tr>
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
<script language="javascript">
var timerID = null;
function initTimer()
{
	timerID = self.setTimeout("timeOut()", 3000);
	
}
function timeOut()
{	
	var fail = "<%=failed%>";
	var finish = "<%=finished%>";

	if(fail == "true" || finish == "true"){
		return;
	}
	clearTimeout(timerID);
	window.location = "process.jsp?execute=no&process=<%=URLEncoder.encode(processName,"UTF-8")%>&title=<%=URLEncoder.encode(title,"UTF-8")%>";

}
initTimer();
</script>

