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
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MusicDbUtil musicDbUtil;
	
	//Define Resource Injection in the connection pool
	@Resource(name="jdbc/web_music_library")
	private DataSource dataSource;
	

	//initialize ControllerServlet
	@Override
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
			
		//read command
		String theCommand = request.getParameter("command");
		try{
			if(theCommand == null)
				theCommand = "LIST";
			
			//route to the appropriate method
			switch(theCommand){
			case "List" :
				//list item in MVC fashion
				listResources(request, response);
				break;
			case "ADD" :
				addResources(request, response);
				break;
			case "DELETE" :
				deleteResources(request, response);
				break;
			case "UPDATE" :
				updateResource(request, response);
				break;
			case "LOAD" :
				loadResource(request, response);
				break;
			default: listResources(request,response); 
				
			
			}
			
		}
		catch(Exception exc){
			throw new ServletException(exc);
			
		}
		
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try{
		String theCommand = request.getParameter("command");
		
		switch(theCommand){
		case "ADD" : 
			addResources(request, response);
			break;
		case "TEST" : 
			System.out.println("test submit");
			break;
		
		default : listResources(request, response);
		}
		}catch(Exception exc){
			throw new ServletException();
			
		}
	}





	private void loadResource(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		
		//read id from form
		String theResourceId = request.getParameter("resourceId");
		
		//get resource from db
		MResource theResource = musicDbUtil.getResource(theResourceId);
		
		//pass resource to request
		request.setAttribute("THE_RESOURCE", theResource);
		
		
		// send to update-resource page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-resource-form.jsp");
		dispatcher.forward(request, response);
		
	}



	private void updateResource(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		//read resource info from request
		int id = Integer.parseInt(request.getParameter("resourceId"));
		String title = request.getParameter("title");
		String artist = request.getParameter("artist");
		String album = request.getParameter("album");
		Genre genre = Genre.valueOf( request.getParameter("genre"));  
		double length = Double.parseDouble(request.getParameter("length"));
		int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
		
		//create MResource object
		MResource mResource = new MResource(id, title, artist, album, genre, length, releaseYear); 
		
		//perform update on db
		musicDbUtil.updateMResource(mResource);
		
		//send back to list-mResource
		listResources(request, response);
		
		
	}



	private void deleteResources(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		//read resource id from db
		String theResourceId = request.getParameter("resourceId");
		
		//delete resource from db
		musicDbUtil.deleteMResource(theResourceId);
		
		
		//send them back to list-mResource
		listResources(request, response);
		
		
	}



	private void addResources(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		
		// create resource object
		String title = request.getParameter("title");
		String artist = request.getParameter("artist");
		String album = request.getParameter("album");
		Genre genre = Genre.valueOf( request.getParameter("genre"));  
		double length = Double.parseDouble(request.getParameter("length"));
		int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
		
		MResource mResource = new MResource(title, artist, album, genre, length, releaseYear); // id in auto generated
		System.out.println("hahah"); 
		// add object to database
		musicDbUtil.addMResource(mResource);
		
		// send back to list-mResource
		// SEND AS REDIRECT to avoid multiple-browser reload issue
	    response.sendRedirect(request.getContextPath() + 
	    		"/ControllerServlet?command=LIST");
		
		
	}



	private void listResources(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get resource from dbUtil
		List<MResource> mResources = musicDbUtil.getMResources();
				
		// pass resource to the request
		System.out.println("Is RESOURCE_LIST empty?  "+mResources.isEmpty());
		request.setAttribute("RESOURCE_LIST", mResources);
						
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-mResource.jsp");
		dispatcher.forward(request, response);
	}



}
