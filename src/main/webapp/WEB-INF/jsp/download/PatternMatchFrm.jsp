<%--
	/*
	 * Filename : ToolboxMain.jsp
	 * Function	: Tool Box Main Page
	 * Location : ToolboxMain
	 * Comment :
	 * History	: 2004-07-06, Victor, v1.0, created
	 * Version	: 1.0
	 * Author	: Copyright ⓒ 2004 Javanuri Corp. All rights reserved.
	 */
--%>
<jsp:include page="/inc/download.jsp" flush="true" />
<%@page contentType="text/html; charset=euc-kr" %>
<%-- 공통 헤더 파일 포함 --%>
<%@ page import = "java.sql.*" %>    
<%
	String id = null;
	
%>




<table width="1090" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="190" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		<!-- left menu -->
	<jsp:include page="/inc/download_menu.jsp" flush="true" />
	</td>
    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">Pattern Match</font> 
            <td align="right" valign="bottom" style="padding-right:5px;"><table border="0" cellspacing="0" cellpadding="0">
		</tr>
        </table></td>
      </tr>
</table>

<form action="PatternMatch.jsp" method="post" name="swissprot" target="_blank">
<table  width="300" align="left" >
        <tr></tr>
        <tr>
        <td>
		<%

Connection conn = null;                                        // null로 초기화 한다.
PreparedStatement pstmt = null;
ResultSet rs = null;
try{
String url = "jdbc:mysql://localhost:3306/update_day";        // 사용하려는 데이터베이스명을 포함한 URL 기술
String idd = "root";                                                    // 사용자 계정
String pw = "isa4986";                                                // 사용자 계정의 패스워드

Class.forName("com.mysql.jdbc.Driver");                       // 데이터베이스와 연동하기 위해 DriverManager에 등록한다.
conn=DriverManager.getConnection(url,idd,pw);              // DriverManager 객체로부터 Connection 객체를 얻어온다.

String sql = "select * from update_table where dbname='swiss_prot';";                        // sql 쿼리
pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.


rs = pstmt.executeQuery();                                        // 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.
String sd="";
String sv="";
while(rs.next()){                                                        // 결과를 한 행씩 돌아가면서 가져온다.

sd = rs.getString("date");
sv = rs.getString("version");
}
sql = "select * from update_table where dbname='genbank';";                        // sql 쿼리
pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.


rs = pstmt.executeQuery();                                        // 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.
String gd="";
String gv="";
while(rs.next()){                                                        // 결과를 한 행씩 돌아가면서 가져온다.

gd = rs.getString("date");
gv = rs.getString("version");
}

%>
                <font style="font-size:11pt;"><li>Select DATABASE</li></font><br>
                &nbsp;&nbsp;<input type="radio" name="db_type" value="1" checked="on"> SWISS-PROT <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Release <%=sv%> (<%=sd%>)<br>
                &nbsp;&nbsp;<input type="radio" name="db_type" value="0"> GENBANK <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Release <%=gv%> (<%=gd%>)<p>

<%


}catch(Exception e){                                                    // 예외가 발생하면 예외 상황을 처리한다.
e.printStackTrace();
out.println("member 테이블 호출에 실패했습니다.");
}finally{                                                            // 쿼리가 성공 또는 실패에 상관없이 사용한 자원을 해제 한다.  (순서중요)
if(rs != null) try{rs.close();}catch(SQLException sqle){}            // Resultset 객체 해제
if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제
if(conn != null) try{conn.close();}catch(SQLException sqle){}   // Connection 해제
}
%>
                <font style="font-size:11pt;"><li>Pattern Syntax</li></font><br>
                &nbsp;&nbsp;<input type="radio" name="format_type" value="1" checked="on"> PROSITE regular expression<br>
                &nbsp;&nbsp;<input type="radio" name="format_type" value="0"> PYTHON regular expression<p>

                <font style="font-size:11pt;"><li>Input Pattern</li></font><br>
                &nbsp;&nbsp;<input type="text" name="pattern1" size="20">&nbsp;AND<br>
                &nbsp;&nbsp;<input type="text" name="pattern2" size="20">&nbsp;AND<br>
                &nbsp;&nbsp;<input type="text" name="pattern3" size="20">&nbsp;AND<br>
                &nbsp;&nbsp;<input type="text" name="pattern4" size="20">&nbsp;AND<br>
                &nbsp;&nbsp;<input type="text" name="pattern5" size="20"><br>

                &nbsp;&nbsp;<input type="hidden" name="db_id" value=<%=id%>><p>

                <font style="font-size:11pt;"><li>Search Option</li></font><br>
                &nbsp;&nbsp;<input type="checkbox" name="check_species" value="1"> Match To Species<br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <select name="species">
                                <option>Homo sapiens
                                <option>Mus musculus
                                <option>Rattus norvegicus
                                <option>Caenorhabditis elegans
                                <option>Drosophila melanogaster
                                <option>Saccharomyces cerevisiae
                                <option>Escherichia coli
                                <option>Xenopus laevis
                        </select><br>

                &nbsp;&nbsp;<input type="checkbox" name="check_except" value="1"> Without Sequence Data<br>
                &nbsp;&nbsp;<input type="checkbox" name="check_order" value="1"> Keep Pattern Order<p>
        </td>
        </tr>

        <tr align="center">
        <td >
                <input type="submit" value="&nbsp;&nbsp;RUN&nbsp;&nbsp;" class="btn" >&nbsp;
                <input type="reset" value="CANCEL" class="btn">
        </td>
        </tr>
</table>
</form>
<table  align="left">
	<tr>
		<td class="title_02">			
			<li>How to search</li>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;&nbsp;<img src="/images/icon_10.gif">&nbsp;‘x’, ’X’, ’.’ means randomly selected character.<br>
			&nbsp;&nbsp;&nbsp;&nbsp;If you input mxa, mXa or m.a, you can search from maa to mza.<br>
			<br>
			&nbsp;&nbsp;<img src="/images/icon_10.gif">&nbsp;X(number) Number means X was duplicated how many times<br>
			&nbsp;&nbsp;&nbsp;&nbsp;ex) ma(2) means maa. ma(3) means maaa.<br>
			<br>
			&nbsp;&nbsp;<img src="/images/icon_10.gif">&nbsp;By using ‘&lt;’ or ‘^’, you can search from N-term location.<br>
			&nbsp;&nbsp;&nbsp;&nbsp;ex) &lt;ma or ^ma <br>
			<br>
			&nbsp;&nbsp;<img src="/images/icon_10.gif">&nbsp;By using ‘&gt;’ or ‘$’, you can search from C-term location.<br>
			&nbsp;&nbsp;&nbsp;&nbsp;ex) ma&gt; or ma$<br>

			<br>
			&nbsp;&nbsp;<img src="/images/icon_10.gif">&nbsp;This search program ignores '-' and ' '.
		</td>
		</tr>
</table>





