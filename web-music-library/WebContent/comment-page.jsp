<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Comment</title>
		
	</head>

	<body>
		<div id="wrapper">
			<div id="header">
				<h2>Comments toward ${commentedMusic.title}</h2>
			</div>
		</div>
		
			<form method = "GET" action= "UserServlet">	
				<input type="hidden" name="theUserId" value="${theLoginUser.id}" />
				<input type="hidden" name="theUserName" value="${theLoginUser.username}" />
				<input type="hidden" name="command" value = "toSearchPage"/>
				<input type="submit" value = "Back"/>
			</form>
		
		<div id="container">
			<div id="content">							
				<table>
					<tr>
						<th>Comment</th>
						<th>Commented By</th>					
					</tr>
					
					<c:forEach var="comment" items="${COMMENT}">
					
<%-- 					<c:url var="deletelist" value="UserServlet"> --%>
<%-- 						<c:param name="command" value="deleteFavorite" /> --%>
<%-- 						<c:param name="favoriteId" value="${favorite.id}" /> --%>
<%-- 						<c:param name="theUserId" value="${theLoginUser.id}" /> --%>
<%-- 						<c:param name="theUserName" value="${theLoginUser.username}" /> --%>
<%-- 					</c:url> --%>
																		
					<tr>

						<td> ${comment.content} </td>
						<td> ${theLoginUser.username}</td>
						
					</tr>
					</c:forEach>	

				</table>	

			</div>
			<form method = "POST" action= "UserServlet">	
				<input type="hidden" name="command" value = "addCOMMENT"/>
				<input type="hidden" name="theUserId" value="${theLoginUser.id}" />
				<input type="hidden" name="commentedMusicId" value="${commentedMusic.id}" />
				<input type="hidden" name="commentedMusicTitle" value="${commentedMusic.title}" />
				<input type="hidden" name="theUserName" value="${theLoginUser.username}" />
				<input type="text" name="theComment" />
				<input type="submit" value = "Add Comment"/>
			</form>
			

	</div>

	</body>
</html>