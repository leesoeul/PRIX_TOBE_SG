<%@ page import="prix.PrixDataWriter" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%
	Context initContext = new InitialContext();
	Context envContext = (Context)initContext.lookup("java:/comp/env");
	DataSource ds = (DataSource)envContext.lookup("jdbc/PrixDB");
	Connection conn = ds.getConnection();
	
	final String root = "/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/config/";
	String dbName = "";
	String modDate = "", modVersion = "";
	String sftwName ="", sftwDate = "", sftwVersion = "";
	
	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	List items = upload.parseRequest(request);
	Iterator iter = items.iterator();
	while (iter.hasNext())
	{
		FileItem item = (FileItem)iter.next();
		String name = item.getFieldName();
		if ( item.isFormField() ) {
			if (name.compareTo("db_name") == 0)
				dbName = item.getString();
			else if (name.compareTo("ptm_date") == 0)
				modDate = item.getString();
			else if (name.compareTo("ptm_version") == 0)
				modVersion = item.getString();
			else if (name.compareTo("sftw_name") == 0)
				sftwName = item.getString();
			else if (name.compareTo("sftw_version") == 0)
				sftwVersion = item.getString();
			else if (name.compareTo("sftw_date") == 0)
				sftwDate = item.getString();
		} else {
			if (name.compareTo("db_file") == 0) {
				String dbFile = item.getName();
				if (dbFile.length() > 0) {
					dbFile = dbFile.replace('\\', '/');
				//	int index = PrixDataWriter.write("fasta", dbFile, item.getInputStream());

					String dbPath = dbFile.substring(dbFile.lastIndexOf('/') + 1, dbFile.length());
					int index = PrixDataWriter.write("fasta", dbPath, item.getInputStream());
					
					if (dbName == null || dbName.length() == 0) {
						int last = dbPath.lastIndexOf('.');
						if (last < 0)
							dbName = dbPath;
						else
							dbName = dbPath.substring(0, last);
					}
					String path = root + dbPath;
					FileOutputStream fos = new FileOutputStream(path);
					OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
					InputStream is = item.getInputStream();
					while (is.available() > 0)
						writer.write(is.read());
					writer.close();
					fos.close();
					is.close();
					
					if (conn != null) {
						Statement state = conn.createStatement();
						state.executeUpdate("insert into px_database (name, file, data_id) values ('" + dbName + "', '" + dbPath + "', " + index + ");");
						state.close();
					}
				//	int index = 232;
				//	PrixDataWriter.replace( index, item.getInputStream() );
				}
			}
			else if (name.compareTo("ptm_xml") == 0) {
				String modPath = item.getName();
				if (modPath.length() > 0 && conn != null) {
					modPath = modPath.replace('\\', '/');
					Statement state = conn.createStatement();
					ResultSet rs = null;
					
					try
					{
						String modFile = modPath.substring(modPath.lastIndexOf('/') + 1, modPath.length());
						String path = root + modFile;
						FileOutputStream fos = new FileOutputStream(path);
						OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
						InputStream is = item.getInputStream();
						while (is.available() > 0)
							writer.write(is.read());
						writer.close();
						fos.close();
						is.close();
						
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = docFactory.newDocumentBuilder();
						Document doc = builder.parse(item.getInputStream());
						
						HashMap<String, Integer> classMap = new HashMap<String, Integer>();
						NodeList list = doc.getElementsByTagName("classificationRow");
						for (int i = 0; i < list.getLength(); i++)
						{
							Node node = list.item(i);
							NamedNodeMap map = node.getAttributes();
							Node attr = map.getNamedItem("classification");
							String nodeName = attr.getNodeValue();
							rs = state.executeQuery("select id from px_classification where class='" + nodeName + "';");
							if (rs.next())
								classMap.put(nodeName, rs.getInt(1));
							else {
								rs.close();
								rs = state.executeQuery("select max(id) from px_classification;");
								int index = -1;
								if (rs.next()) {
									index = rs.getInt(1);
									state.executeUpdate("insert into px_classification (class) values ('" + nodeName + "');");
								}
								classMap.put(nodeName, index);
							}
							rs.close();
						}
						
						list = doc.getElementsByTagName("PTM");
						int size = list.getLength();
						String[] sqls = new String[size];
						for (int i = 0; i < size; i++)
						{
							String modName = "", fullName = "", classi = "", md = "", amd = "", residue = "", position = "";

							Node node = list.item(i);
							NodeList children = node.getChildNodes();
							for (int j = 0; j < children.getLength(); j++)
							{
								Node child = children.item(j);
								String nodeName = child.getNodeName();
								if (nodeName.equals("name"))
									modName = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("fullName"))
									fullName = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("classification"))
									classi = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("massDifference"))
									md = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("avgMassDifference"))
									amd = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("residue"))
									residue = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("position"))
									position = child.getFirstChild().getNodeValue();
							}

							sqls[i] = "insert into px_modification (name, fullname, class, mass_diff, avg_mass_diff, residue, position) values ('" + modName + "', '" + fullName + "', " + classMap.get(classi) + ", " + md + ", " + amd + ", '" + residue + "', '" + position + "');";
						}
						
						if (sqls.length > 0) {
							if (modDate == null || modDate.length() == 0)
								modDate = "now()";
							else
								modDate = "'" + modDate + "'";
							if (modVersion == null || modVersion.length() == 0)
								modVersion = "0.0";
							state.executeUpdate("insert into px_modification_log (date, version, file) values (" + modDate + ", '" + modVersion + "', '" + modFile.replace("'", "\\\'") + "');");
							
							conn.setAutoCommit(false);
							int min = -1;
							rs = state.executeQuery("select min(user_id) from px_modification;");
							if (rs.next())
								min = rs.getInt(1) - 1;
							rs.close();
							PreparedStatement ps = conn.prepareStatement("update px_modification set user_id=" + min + " where user_id=0;");
							ps.executeUpdate();
							
							for (int i = 0; i < sqls.length; i++)
							{
								ps = conn.prepareStatement(sqls[i]);
								ps.executeUpdate();
								//if (ps.getWarnings() != null)
								//	ps.getWarnings().printStackTrace();
							}
							conn.commit();
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					state.close();
				}
			}
			else if ( name.compareTo("sftw_zip") == 0 ) {
				String sftw_root = "/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/download/software_archive/";
				
				String modPath = item.getName();
				if ( modPath.length() > 0 && conn != null ) {
					modPath = modPath.replace('\\', '/');
				
					File prevFile = null;
					File protDir = new File(sftw_root + "release/");
					for( File file : protDir.listFiles() ) {
						if( file.getName().startsWith( sftwName.toLowerCase() ) ){
						//	prevFile = file;
							file.renameTo( new File( sftw_root + "deprecated/" + new Date().getTime() + "_" + file.getName() ) );
							break;
						}
					}

					try
					{
					//	String sftwFile = modPath.substring(modPath.lastIndexOf('/') + 1, modPath.length());
						String sftwFile = sftwName.toLowerCase() + "_v" + sftwVersion + ".zip";
						String path = sftw_root + "release/" + sftwFile;
						FileOutputStream fos = new FileOutputStream(path);
						InputStream is = item.getInputStream();
						byte[] b = new byte[4096];
						int size = 0;
						 while( (size = is.read(b)) > 0 ){
							fos.write(b,0,size);
						}
						fos.close();
						is.close();

						String sql = "insert into px_software_log (name, date, version, file) values ('" + sftwName + "', '" + sftwDate + "', '" + sftwVersion + "', '" + modPath.substring(modPath.lastIndexOf('/') + 1).replace("'", "\\\'") + "');";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.executeUpdate();
						ps.close();

					//	if( prevFile != null ) {
					//		prevFile.renameTo( new File( sftw_root + "deprecated/" + new Date().getTime() + "_" + prevFile.getName() ) );
					//	}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	if (conn != null)
		conn.close();
	
	response.sendRedirect("configuration.jsp");
%>
