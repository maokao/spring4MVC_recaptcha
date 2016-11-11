<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page language="java" import="java.sql.*" %>
<html>
<body>
<div id="login-box">

		<h3>Insert a User</h3>


		<form name='insertForm'
			action="insertpage" method='POST'>

			<table>
				<tr>
					<td><img src="resources/pic/red.jpg" alt="" width="11" height="11" /> UserName:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td><img src="resources/pic/red.jpg" alt="" width="11" height="11" /> Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td><img src="resources/pic/red.jpg" alt="" width="11" height="11" /> Email:</td>
					<td><input type='text' name='email' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>

</body>
</html>