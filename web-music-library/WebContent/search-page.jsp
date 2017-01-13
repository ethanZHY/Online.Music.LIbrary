<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Search-Page</title>
		
	</head>

	<body>
		<div id="wrapper">
			<div id="header">
				<h2>Welcome,${theLoginUser.username}!</h2>
			</div>
		</div>	
		
		<table id = "button">
			<tr>	
				<td><form method = "GET" action= "UserServlet">	
				<input type="hidden" name="theUserId" value="${theLoginUser.id}" />
				<input type="hidden" name="theUserName" value="${theLoginUser.username}" />
				<input type="hidden" name="command"value = "toPlaylist"/>
				<input type="submit" value = "My Playlist"/>
				</form></td>
				
				<td><form method = "GET" action= "TestServlet">					
				<input type="hidden" name="command"value = "Log-out"/>
				<input type="submit" value = "Log out"/>
				</form></td>
			</tr>
		</table>		
		
		<div id="container">
			<div id="content">
	
				<form action = "UserServlet" method = "GET" >						
					<table>	
					<tr>
						<td><input type="hidden" name="theUserName" value="${theLoginUser.username}"/></td>
						<td><input type = "hidden" name = "theUserId" value ="${theLoginUser.id}"/></td>
						<td><input type="hidden" name="command" value = "doSEARCH"/></td>
						<td>Search Music Here:   </td>
						<td><input type = "text" name = "searchArea"/></td>
						<td><input type = "submit" value = "Search"/></td>
					</tr>
					<tr>
						<td><label>
						Title:
						</label><input type = "radio" name = "searchBy" value = "title"></td>
						<td><label>
						Artist:</label><input type = "radio" name = "searchBy" value = "artist"></td>
						<td><label>
						Genre:</label><input type = "radio" name = "searchBy" value = "genre"></td>
						<td><label>
						Album:</label><input type = "radio" name = "searchBy" value = "album"></td>
					</tr>		
					</table>
				</form>
				
					
				<table>
					<tr>
						<th>Title</th>
						<th>Artist</th>
						<th>Album</th>
						<th>Genre</th>
						<th>Length</th>
						<th>releaseYear</th>
					</tr>
					
					<c:forEach var="searchedMusic" items="${SEARCHED_MUSIC}">
					
					<!-- set up a link for add music into playlist -->
					<c:url var="favoriteLink" value="UserServlet">
						<c:param name="command" value="FAVORITE" />
						<c:param name="seachedMusicId" value="${searchedMusic.id}" />
						<c:param name="theUserId" value="${theLoginUser.id}" />
						<c:param name="theUserName" value="${theLoginUser.username}"/>
					</c:url>
					
					<c:url var="commentLink" value="UserServlet">
						<c:param name="command" value="toCOMMENT" />
						<c:param name="seachedMusicId" value="${searchedMusic.id}" />
						<c:param name="seachedMusicTitle" value="${searchedMusic.title}" />
						<c:param name="theUserId" value="${theLoginUser.id}" />
						<c:param name="theUserName" value="${theLoginUser.username}"/>
					</c:url>

					<!--  set up a link to delete a resource -->

																		
					<tr>

						<td> ${searchedMusic.title} </td>
						<td> ${searchedMusic.artist} </td>
						<td> ${searchedMusic.album} </td>
						<td> ${searchedMusic.genre} </td>
						<td> ${searchedMusic.length} </td>
						<td> ${searchedMusic.releaseYear} </td>
						
						<td> 
							<a href="${favoriteLink}">Favorite</a> 	
						</td>
						<td> | 	
						</td>
						<td> 
							<a href="${commentLink}">Comment</a> 	
						</td>
					</tr>
				
					</c:forEach>
					
				</table>
			
			</div>
	
	</div>
	</body>
</html>