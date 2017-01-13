<%@ page import="java.util.*,com.cs5200.web.jdbc.*" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Update-Resources</title>	
	</head>
	

	<body>
		<h2>Music Library</h2>
		<h3>Update-Resources </h3>
		
		<form action="ControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="resourceId" value="${THE_RESOURCE.id}" />

			
			<table>
				<tbody>
					<tr>
						<td><label>Title:</label></td>
						<td><input type="text" name="title" 
						value = "${THE_RESOURCE.title}"/></td>
					</tr>
					
					<tr>
						<td><label>Artist:</label></td>
						<td><input type="text" name="artist" 
						value = "${THE_RESOURCE.artist}"/></td>
					</tr>
					
					<tr>
						<td><label>Album:</label></td>
						<td><input type="text" name="album" 
						value = "${THE_RESOURCE.album}"/></td>
					</tr>
					
					<tr>
						<td><label>Genre:</label></td>
						<td><input type="text" name="genre" 
						value = "${THE_RESOURCE.genre}"/></td>
					</tr>
					
					<tr>
						<td><label>Length:</label></td>
						<td><input type="text" name="length" 
						value = "${THE_RESOURCE.length}"/></td>
					</tr>
					
					<tr>
						<td><label>ReleaseYear:</label></td>
						<td><input type="text" name="releaseYear" 
						value = "${THE_RESOURCE.releaseYear}"/></td>
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