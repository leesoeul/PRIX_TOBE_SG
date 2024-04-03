<jsp:include page="./inc/misc.jsp" flush="true" />
<!-- header 끝-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<table width="95%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="10" valign="top">
	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">REGISTRATION</td>
        <td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
      </tr>
    </table>
      </td>
  </tr>
  <%--  ㅈㅍㅈㅅ
	<form name="reg" method="post" action="registration.jsp"> --%>


	<form:form method="post" modelAttribute="newAccountDto">


  <%-- ㅈㅍㅈㅅ
	<input type="hidden" name="url" value="<%=url%>"/> --%>
  <tr>
    <td align="center" valign="top" style="padding:10px;"><img src="/images/tit_join.gif">
<%-- ㅈㅍㅈㅅ
<%
	if (!success)
	{
%>
	<!--br/><font color="red">Email address "<%=email%>" already exists.</font-->
	<br/><font color="red">The ID "<%=email%>" already exists.</font>
<%
	}
%> --%>
      <div id="container" style="background: #fff; width:400px"> <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
          <div class="box">
            <table width="80%" border="0" cellpadding="0" cellspacing="0" id="gray" style="BORDER-BOTTOM: #d9dddc 1px solid; BORDER-TOP: #d9dddc 2px solid; ">
              <!--tr>
                <th>Name</th>
                <td><input name="name" type="text" size="30"></td>
              </tr>
              <tr>
                <th>Affiliation</th>
                <td><input name="job" type="text" size="30"></td>
              </tr-->
              <tr>
                <th>User ID</th>
                <%-- ㅈㅅ
								<td><input name="email" type="text" size="30"> --%>
                <td><form:input type="text" size="30" path="email" required="required"/>
				</td>
              </tr>
              <tr>
                <th>Password</th>
                <%-- ㅈㅅ
								<td><input name="pw1" type="password" size="30"></td> --%>
                <td><form:input type="password" size="30" path="password" required="required" /></td>
              </tr>
              <tr>
                <th>Password (confirm)</th>
                <%-- ㅈㅅ
								<td><input name="pw2" type="password" size="30"></td> --%>
                <td><form:input type="password" size="30" path="confirmedPassword" require="required" /></td>
                                <form:input type="hidden" size="30" path="level" />
              </tr>
            </table>
            <br>
            <%-- ㅈㅍㅈㅅ
						<input name="Submit2223" type="button" class="btn" value="Cancel" onClick="window.location='login.jsp?url=<%=url%>'">
            <input name="Submit22222" type="button" class="btn" value="Regist" onClick="doSubmit()"> --%>

						<input name="Submit2223" type="button" class="btn" value="Cancel" onClick="window.location='login'" />
            <input name="Submit22222" type="submit" class="btn" value="Regist" />
          </div>
    <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div></td>
  </tr>
  </form:form>
</table>

<jsp:include page="./inc/footer.jsp" flush="true" />
