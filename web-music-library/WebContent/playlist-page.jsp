<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>PlayList</title>
		
	</head>

	<body>
		<div id="wrapper">
			<div id="header">
				<h2>${theLoginUser.username}'s PlayList</h2>
			</div>
		</div>
		
		<div id="container">
			<div id="content">							
				<table>
					<tr>
						<th>Title</th>
						<th>Artist</th>
						<th>Album</th>
						<th>Genre</th>
						<th>Length</th>
						<th>releaseYear</th>
					</tr>
					
					<c:forEach var="favorite" items="${FAVORITE_MUSIC}">
					
					<!--  set up a link to delete a list -->
					<c:url var="deletelist" value="UserServlet">
						<c:param name="command" value="deleteFavorite" />
						<c:param name="favoriteId" value="${favorite.id}" />
						<c:param name="theUserId" value="${theLoginUser.id}" />
						<c:param name="theUserName" value="${theLoginUser.username}" />
					</c:url>
																		
					<tr>

						<td> ${favorite.title} </td>
						<td> ${favorite.artist} </td>
						<td> ${favorite.album} </td>
						<td> ${favorite.genre} </td>
						<td> ${favorite.length} </td>
						
						<td> 
							<a href="${deletelist}"
							onclick="if (!(confirm('Are you sure you want to delete this playlist?'))) return false">
							Delete</a>	
						</td>
					</tr>
					</c:forEach>				
				</table>	

			</div>
			
			<form method = "GET" action= "UserServlet">	
				<input type="hidden" name="theUserId" value="${theLoginUser.id}" />
				<input type="hidden" name="theUserName" value="${theLoginUser.username}" />
				<input type="hidden" name="command" value = "toSearchPage"/>
				<input type="submit" value = "Back"/>
			</form>
	</div>

	</body>
</html>