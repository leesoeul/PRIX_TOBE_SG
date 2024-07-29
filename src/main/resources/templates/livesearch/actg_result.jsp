<%-- html? ??? ??? 2024 --%>
<jsp:include page="/inc/livesearch.jsp" flush="true" />
<!-- header 끝-->

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
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@page import="java.io.*"%>
<%@page import="java.util.zip.*"%>
<%@page import="javax.servlet.ServletException" %>
<%@page import="javax.servlet.http.HttpServlet" %>
<%@page import="javax.servlet.http.HttpServletRequest" %>
<%@page import="javax.servlet.http.HttpServletResponse" %>
<%@page import="javax.servlet.annotation.WebServlet" %>

<%

	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	if (conn == null)
		response.sendRedirect("../login.jsp?url=ACTG/result.jsp");


	final String logDir = "/home/PRIX/ACTG_log/";
	final String logDB = "/home/PRIX/ACTG_db/";
	response.setCharacterEncoding("UTF-8");

	final String anony = "4";
	String id = (String)session.getAttribute("id");
	if (id == null){
		session.setAttribute("id", anony);
		id = (String)session.getAttribute("id");
	}

	Statement state = conn.createStatement();
	ResultSet rs = null;

	String userName = null;
	String title = null;

	String index = request.getParameter("index");
	String key = id+"_"+index;
	String xmlPath = "param_"+ key + ".xml";
	String SAVOutput = "SAV_peptide_" + key + ".txt";
	String NOROutput = "NOR_peptide_" + key + ".txt";
	String VSGGFFOutput = "VSG_peptide_" + key + ".gff";
	String VSGFlatOutput = "VSG_peptide_" + key + ".flat";
	String VSGLogOutput = "VSG_peptide_" + key + ".log";
	String SFTFlatOutput = "SFT_peptide_" + key + ".flat";
	String SFTGFFOutput = "SFT_peptide_" + key + ".gff";
	
	String[] files = new String[7]; 
	files[0] = SAVOutput;
	files[1] = NOROutput;
	files[2] = VSGGFFOutput;
	files[3] = VSGFlatOutput;
	files[4] = VSGLogOutput;
	files[5] = SFTFlatOutput;
	files[6] = SFTGFFOutput;


	Date date = null;

	rs = state.executeQuery("select title, user_id, date from px_search_log where actg=" + index + ";");
	if (rs.next())
	{
		title = rs.getString(1);
		userName = rs.getString(2);
		date = rs.getDate(3);
	}

	rs = state.executeQuery("select name from px_account where id= "+userName+";");
	if (rs.next())
	{
		userName = rs.getString(1);
	}



	rs.close();
	state.close();
	conn.close();
	
	String method = null;
	String IL = null;
	String proteinDB = null;
	String SAV = null;
	String variantSpliceGraphDB = null;
	String altAD = null;
	String exonSkipping = null;
	String intron = null;
	String mutation = null;
	String referenceGenome = null;
	String proteinOption = "";
	String VSGOption = "";

	try{
		FileReader FR = new FileReader(logDir+xmlPath);
		BufferedReader BR = new BufferedReader(FR);
		String line = null;
		while((line=BR.readLine())!=null){
			if(line.contains("MappingMethod")){
				method = line.split("@")[1];
			}else if(line.contains("ILSame")){
				IL = line.split("@")[1];
				if(IL.equalsIgnoreCase("yes")){
					IL = "Isoleucine is equivalent to leucine";
				}else{
					IL = "";
				}
			}else if(line.contains("graphFile")){
				variantSpliceGraphDB = line.split("@")[1];
				variantSpliceGraphDB = variantSpliceGraphDB.replace(logDB,"");
			}else if(line.contains("type=\"proteinDB\"")){
				proteinDB = line.split("@")[1];
				proteinDB = proteinDB.replace(logDB,"");
			}else if(line.contains("JunctionVariation")){
				altAD = line.split("@")[1];
				if(altAD.equalsIgnoreCase("yes")){
					altAD = "junction variation";
				}else{
					altAD = "";
				}

				if(altAD.length() != 0){

					if(VSGOption.length() != 0){
						VSGOption = VSGOption+", "+altAD;
					}else{
						VSGOption = altAD;
					}

				}
			}else if(line.contains("ExonSkipping")){
				exonSkipping = line.split("@")[1];
				if(exonSkipping.equalsIgnoreCase("yes")){
					exonSkipping = "exon skipping";
				}else{
					exonSkipping = "";
				}

				if(exonSkipping.length() != 0){

					if(VSGOption.length() != 0){
						VSGOption = VSGOption+", "+exonSkipping;
					}else{
						VSGOption = exonSkipping;
					}

				}
			}else if(line.contains("IntronMapping")){
				intron = line.split("@")[1];
				if(intron.equalsIgnoreCase("yes")){
					intron = "intron mapping";
				}else{
					intron = "";
				}

				if(intron.length() != 0){

					if(VSGOption.length() != 0){
						VSGOption = VSGOption+", "+intron;
					}else{
						VSGOption = intron;
					}

				}
			}else if(line.contains("<Mutation>")){
				mutation = line.split("@")[1];
				mutation = mutation.replace(logDir,"");

				if(mutation.length() != 0){
					mutation = "mutation";
					if(VSGOption.length() != 0){
						VSGOption = VSGOption+", "+mutation;
					}else{
						VSGOption = mutation;
					}

				}
			}else if(line.contains("referenceGenome")){
				referenceGenome = line.split("@")[1];
				referenceGenome = referenceGenome.replace(logDB,"");
			}else if(line.contains("SAV")){
				SAV = line.split("@")[1];
				if(SAV.equalsIgnoreCase("yes")){
					SAV = "single amino-acid variation";
				}else{
					SAV = "";
				}

				proteinOption = SAV;
			}
		}

		BR.close(); FR.close();

	}catch(IOException e){

	}
	
	// Make Zip file
	try{
		byte[] buf = new byte[1024];
		String zip = logDir+index+".zip";
		
		File zipFile = new File(zip);
		if(!zipFile.exists()){
			ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zip));
			for(int i=0; i<files.length; i++){
				File file = new File(logDir+files[i]);
				if(!file.exists()){
					continue;
			}

			FileInputStream in = new FileInputStream(file);
			outZip.putNextEntry(new ZipEntry(files[i]));

			int len;
			while((len = in.read(buf)) > 0){
				outZip.write(buf, 0, len);
			}

			outZip.closeEntry();
			in.close();
		}

		outZip.close();
		}

		
	}catch(Exception e){

	}
%>

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
		<td>: ACTG ( version 1.00 )</td>
	</tr>
	<tr>
		<td><b>Date</b></td>
		<td>: <%=date.toString()%></td>
	</tr>
	<tr>
		<td><b>User</b></td>
		<td>: <%=userName%></td>
	</tr>
	<tr>
		<td><b>Search title</b></td>
		<td>: <%=title%></td>
	</tr>
</table>
<br>
<br>

<div><font size="3"><b>Environment (for all mapping phases)</b></font></div>
<table border="0">
	<tr>
		<td><b>Mapping method</b></td>
		<td>: <%=method%></td>
	</tr>
	<%if(IL.length() != 0){%>
	<tr>
		<td><b>Mapping options</b></td>
		<td>: <%=IL%></td>
	</tr>
	<%} %>
</table>

	<%if(method.contains("P")){ %>
<br>
<div><font size="3"><b>Protein database mapping</b></font></div>
<table border="0">
	<tr>
		<td><b>Protein database</b></td>
		<td>: <%=proteinDB%></td>
	</tr>
	<%if(proteinOption.length() !=0){%>
	<tr>
		<td><b>Mapping options</b></td>
		<td>: <%=proteinOption%></td>
	</tr>
	<%} %>

</table>
	<%} %>

	<%if(method.contains("V")){ %>
<br>
<div><font size="3"><b>Variant splice graph mapping</b></font></div>
<table border="0">
	<tr>
		<td><b>Graph database</b></td>
		<td>: <%=variantSpliceGraphDB%></td>
	</tr>


	<%if(VSGOption.length() != 0){%>

	<tr>
		<td><b>Mapping options</b></td>
		<td>: <%=VSGOption%></td>
	</tr>
	<%} %>
</table>
	<%} %>

<br>
<br>
 
<hr>

<br>
<p><font size="4"><b>Search Summary</b></font></p>
<a href="download.jsp?index=<%=index%>" target="_blank">Download the result (zip file)</a>
<p><font size="4"><b>Notice</b></font></p>
Please open the results using an Excel because basic text application cannot decord well.
</table>

<jsp:include page="/inc/footer.jsp" flush="true" />
