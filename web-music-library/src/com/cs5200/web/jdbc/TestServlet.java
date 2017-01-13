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
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//Define connection pool for Resource Injection
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
				toLoginPage(request,response); 
			//route to the appropriate method
			switch(theCommand){
				case "Log-Out" :
					System.out.println("log-out");
					toLoginPage(request, response);
					break;
				default: toLoginPage(request,response); 			
			}						
		}
		catch(Exception exc){
			exc.printStackTrace();
			
		}
		
	}
	
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String theCommand = request.getParameter("command");
		try{
			if(theCommand == null)
				toLoginPage(request,response); 
			//route to the appropriate method
			switch(theCommand){
			case "isUser" :
				System.out.println("User log in");
				checkUser(request, response);
				break;
			case "isAdmin" :
				System.out.println("Admin log in");
				checkAdmin(request, response);
				break;
			case "REGIST" :
			System.out.println("create user account");
			createAccount(request, response);
			break;
			default: toLoginPage(request,response); 

			}			
		}
		catch(Exception exc){
			exc.printStackTrace();
			
		}
	}

	private void createAccount(HttpServletRequest request, HttpServletResponse response)
		throws Exception{

		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		
		musicDbUtil.addUser(username,password);
		musicDbUtil.addLib(username,password);
		
		toLoginPage(request,response);
		
		
		
		
	}

	private void checkUser(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		
		//read username &password from page
		String theUsername = request.getParameter("Username");
		String thePassword = request.getParameter("Password");
		System.out.println(theUsername +" and "+ thePassword);
		
		//check admin identity from db
		User theUser = musicDbUtil.isUser(theUsername, thePassword);
		if(theUser != null){
			
			request.setAttribute("theLoginUser", theUser);
			toSearchPage(request, response);
		}
		else 
			response.sendRedirect(request.getContextPath() + 
	    		"/TestServlet?command=Log-Out");

	}

	private void checkAdmin(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		
		//read username &password from page 
		String theUsername = request.getParameter("Username");
		String thePassword = request.getParameter("Password");
		System.out.println(theUsername +" and "+ thePassword);
		
		//check admin identity from db
		if(musicDbUtil.isAdmin(theUsername, thePassword)){
			
			
			toResourcesPage(request, response);
		}
		else 
			response.sendRedirect(request.getContextPath() + 
	    		"/TestServlet?command=Log-Out");
		
	}

	private void toSearchPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{

			System.out.println("search page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/search-page.jsp");
			dispatcher.forward(request, response);
		
	}

	private void toLoginPage(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	private void toResourcesPage(HttpServletRequest request, HttpServletResponse response) 
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
