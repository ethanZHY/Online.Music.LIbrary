<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>List-Resources</title>
		
	</head>

	<body>
		<div id="wrapper">
			<div id="header">
				<h2>Music Library</h2>
			</div>
		</div>
	
		<div id="logoutLink">
			<c:url var="logoutLink" value="TestServlet">
					<c:param name="command" value="Log-Out" />
			</c:url>	
			<a href ="${logoutLink}">Log-out</a>
		</div>
		
		<div id="container">
			<div id="content">
			<!-- put new button: Add MusicResource -->	
			<input type="button" value="Add Resource" 
				   onclick="window.location.href='add-resource-form.jsp'; return false;"
				   class="add-student-button"/>
										
				<table>
					<tr>
						<th>Title</th>
						<th>Artist</th>
						<th>Album</th>
						<th>Genre</th>
						<th>Length</th>
						<th>releaseYear</th>
					</tr>
					
					<c:forEach var="tempResource" items="${RESOURCE_LIST}">
					
					<!-- set up a link for each resource -->
					<c:url var="updateLink" value="ControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="resourceId" value="${tempResource.id}" />
					</c:url>

					<!--  set up a link to delete a resource -->
					<c:url var="deleteLink" value="ControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="resourceId" value="${tempResource.id}" />
					</c:url>
																		
					<tr>

						<td> ${tempResource.title} </td>
						<td> ${tempResource.artist} </td>
						<td> ${tempResource.album} </td>
						<td> ${tempResource.genre} </td>
						<td> ${tempResource.length} </td>
						<td> ${tempResource.releaseYear} </td>
						
						<td> 
							<a href="${updateLink}">Update</a> 

							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this resource?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
					</c:forEach>
					
				</table>
			
			</div>
	
	</div>
	</body>
</html>