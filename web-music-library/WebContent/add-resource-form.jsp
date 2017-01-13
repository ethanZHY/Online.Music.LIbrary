<%@ page import="java.util.*,com.cs5200.web.jdbc.*" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Add-Resources</title>	
	</head>
	

	<body>
		<h2>Music Library</h2>
		<h3>Add-Resources </h3>
		
		<form action="ControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Title:</label></td>
						<td><input type="text" name="title" /></td>
					</tr>
					
					<tr>
						<td><label>Artist:</label></td>
						<td><input type="text" name="artist" 
						/></td>
					</tr>
					
					<tr>
						<td><label>Album:</label></td>
						<td><input type="text" name="album" 
						/></td>
					</tr>
					
					<tr>
						<td><label>Genre:</label></td>
						<td><input type="text" name="genre" 
						/></td>
					</tr>
					
					<tr>
						<td><label>Length:</label></td>
						<td><input type="text" name="length" 
						/></td>
					</tr>
					
					<tr>
						<td><label>ReleaseYear:</label></td>
						<td><input type="text" name="releaseYear" 
						/></td>
					</tr>


					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<p>
			<a href="ControllerServlet">Back</a>
		</p>

	</body>
</html>