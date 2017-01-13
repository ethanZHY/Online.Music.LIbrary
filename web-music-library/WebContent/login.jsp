<%@ page import="java.util.*,com.cs5200.web.jdbc.*" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Log-in</title>	
	</head>
	

	<body>
		<h2>Music Library</h2>
		<h3>Log-in</h3>
		
		<form action="TestServlet" method="POST">
		
<!-- 			<input type="hidden" name="command" value="LogIn" /> -->
			
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
						<td><label>User:</label></td>
						<td><input type="radio" name="command" value="isUser" /></td>
						<td><label>Admin:</label></td>
						<td><input type="radio" name="command" value="isAdmin" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="log-in" class="save" /></td>
					</tr>

					
				</tbody>
			</table>
		</form>
		
			<form method = "POST" action= "register.jsp">	
				<input type="submit" value = "Register"/>
			</form>


	</body>
</html>