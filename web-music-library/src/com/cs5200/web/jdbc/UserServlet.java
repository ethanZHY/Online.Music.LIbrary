package com.cs5200.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/web_music_library")
	private DataSource dataSource;	
	
	private MusicDbUtil musicDbUtil;
	
	
	public void init() throws ServletException {
		super.init();
		
		try{
			//create resource dbUtil...and pass in connection pool
			musicDbUtil = new MusicDbUtil(dataSource);
			
		}catch(Exception exc){
			throw new ServletException(exc);
		}
		
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String theCommand = request.getParameter("command");
		try{
			if(theCommand == null)
				theCommand = "toSearchPage";
			//route to the appropriate method
			switch(theCommand){
				case "toPlaylist" :
					System.out.println("toPlaylist");
					getPlayList(request, response);
					break;
				case "toSearchPage" :
					System.out.println("back to search page");
					searchPage(request, response);
					break;
				case "doSEARCH" :
					System.out.println("doSearch");
					doSearch(request, response);
					break;
				case "deleteFavorite" :
					System.out.println("doSearch");
					deleteFavorite(request, response);
					break;
				case "toCOMMENT" :
					System.out.println("toComment");
					commentPage(request, response);
					break;
				case "FAVORITE" :
					System.out.println("add favorite");
					addFavorite(request, response);
					break;

				default: searchPage(request, response);			
			}						
		}
		catch(Exception exc){
			exc.printStackTrace();
			
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		String theCommand = request.getParameter("command");
		try{
//			if(theCommand == null)
//				theCommand = "toSearchPage";
			//route to the appropriate method
			switch(theCommand){
				case "addCOMMENT" :
					System.out.println("add comment");
					addComments(request, response);
					break;
		
			}						
		}
		catch(Exception exc){
			exc.printStackTrace();
			
		}
	}

	
	private void addComments(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		
			//get information from web
			int theUserId = Integer.parseInt(request.getParameter("theUserId"));
			String theUserName = request.getParameter("theUserName");
			String theMusicTitle = request.getParameter("commentedMusicTitle");
			int theMusicId = Integer.parseInt(request.getParameter("commentedMusicId"));
			String theContent = request.getParameter("theComment");
			
			//add comments to db
			musicDbUtil.addComments(theContent,theUserId,theMusicId);
		
			//refresh commentPage
			User theUser = new User(theUserId,theUserName);
			MResource theMusic = new MResource(theMusicId, theMusicTitle);
			request.setAttribute("theLoginUser", theUser);
			request.setAttribute("commentedMusic", theMusic);
			System.out.println("commentPage");
			
			//set request params
			List<Comments> mComments = musicDbUtil.getComments(theMusicId,theUserId);
			request.setAttribute("COMMENT", mComments);
			System.out.println("commentList");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("comment-page.jsp");
			dispatcher.forward(request, response);
			
	}


	private void commentPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		int theUserId = Integer.parseInt(request.getParameter("theUserId"));
		String theUserName = request.getParameter("theUserName");
		int theMusicId = Integer.parseInt(request.getParameter("seachedMusicId"));
		String theMusicTittle = request.getParameter("seachedMusicTitle");
		User theUser = new User(theUserId,theUserName);
		MResource theMusic = new MResource(theMusicId, theMusicTittle);
		request.setAttribute("theLoginUser", theUser);
		request.setAttribute("commentedMusic", theMusic);
		System.out.println("commentPage");
		
		//set request params
		List<Comments> mComments = musicDbUtil.getComments(theMusicId,theUserId);
		request.setAttribute("COMMENT", mComments);
		System.out.println("commentList");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("comment-page.jsp");
		dispatcher.forward(request, response);
		
	}


	private void addFavorite(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
			
			//get music information from web
			int theMusicId = Integer.parseInt(request.getParameter("seachedMusicId"));
			int theUserId = Integer.parseInt(request.getParameter("theUserId"));
			
			//add favorite music into db
			musicDbUtil.addFavorite(theUserId,theMusicId);
			
			//back
			getPlayList(request, response);
			

		
	}


	private void deleteFavorite(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		
			//read music id from web
			String theMusicId = request.getParameter("favoriteId");
			String theUserId = request.getParameter("theUserId");

			//delete resource from db
			musicDbUtil.deleteFavorite(theUserId,theMusicId);
	
			//send them back to list-mResource
			getPlayList(request, response);
	}


	private void doSearch(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		
			// read information from web
			String searchArea = request.getParameter("searchArea");
			String searchBy = request.getParameter("searchBy");				
			int theuserid = Integer.parseInt(request.getParameter("theUserId"));
			System.out.println(request.getParameter("theUserId"));
			String username = request.getParameter("theUserName");
			System.out.println("read music information from web");
			

			// get music from db
			List<MResource> mResources = null;
			switch(searchBy){
				case "title":
					mResources = musicDbUtil.searchByTitle(searchArea);	
				break;
				
				case "genre":
					mResources = musicDbUtil.searchByGenre(searchArea);
				break;
				
				case "artist":
					mResources = musicDbUtil.searchByArtist(searchArea);
				break;
				
				case "album":
					mResources = musicDbUtil.searchByAlbum(searchArea);
				break;
			}
			
			User theUser = new User(theuserid,username);
			System.out.println("get music from db");
			
			// set params for request
			request.setAttribute("theLoginUser", theUser);
			request.setAttribute("SEARCHED_MUSIC", mResources);
			System.out.println("set params for request");
			
			// redirect to search page
			RequestDispatcher dispatcher = request.getRequestDispatcher("search-page.jsp");
			dispatcher.forward(request, response);
			
	}

	private void getPlayList(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		//transform user information
		int theuserid = Integer.parseInt(request.getParameter("theUserId"));
		String username = request.getParameter("theUserName");
		User theUser = new User(theuserid,username);
		request.setAttribute("theLoginUser", theUser);
		
		//get playlist
		List<MResource> myFavorites = musicDbUtil.getPlaylist(theuserid);
		request.setAttribute("FAVORITE_MUSIC", myFavorites);
		
		//send them back to playlist-page
		RequestDispatcher dispatcher = request.getRequestDispatcher("playlist-page.jsp");
		dispatcher.forward(request, response);
		
		
	}
	
	private void searchPage(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		//get user id from page
		int theuserid = Integer.parseInt(request.getParameter("theUserId"));
		String username = request.getParameter("theUserName");
		//create user object
		User theUser = new User(theuserid,username);
		// set request attribute
		request.setAttribute("theLoginUser", theUser);
		//send them back to playlist-page
		RequestDispatcher dispatcher = request.getRequestDispatcher("search-page.jsp");
		dispatcher.forward(request, response);
		
	}
}
