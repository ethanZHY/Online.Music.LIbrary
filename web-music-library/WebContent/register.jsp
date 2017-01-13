<%@ page import="java.util.*,com.cs5200.web.jdbc.*" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Register</title>	
	</head>
	

	<body>
		<h2>Welcome to Music Library</h2>
		<h3>Create User Account</h3>
		
		<form action="TestServlet" method="POST">
		
			<input type="hidden" name="command" value="REGIST" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Username:</label></td>
						<td><input type="text" name="Username" /></td>
					</tr>
					
					<tr>
						<td><label>Password:</label></td>
						<td><input type="text" name="Password" /></td>
					</tr>

					<tr>
						<td><input type="submit" value="Create User" class="save" /></td>
					</tr>

					
				</tbody>
			</table>
		</form>

	</body>
</html>