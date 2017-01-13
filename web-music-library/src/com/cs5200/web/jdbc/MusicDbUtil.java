package com.cs5200.web.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class MusicDbUtil {
	
	private DataSource dataSource;

	public MusicDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<MResource> getMResources() throws Exception{
		
		List<MResource> mResources = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{		
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from resources");
			
			//Process result set
			while(myRs.next()){
				
				//retrieve data from result set
				
				int id = myRs.getInt("id");
				String title = myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");

				
				//creat resource object
				MResource tempMusic = new MResource(id, title, artist, album, 
						genre, length, releaseYear);
				System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
				
				//add to resources list
				mResources.add(tempMusic);
				
				
			}
			return mResources;
			
		}		
		finally{
			// close JDBC objects
			close(myConn, myStmt, myRs);		
		}
	}

	public void addMResource(MResource mResource) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try{
			// get connection to db
			myConn = dataSource.getConnection();
			
			// specified prepared statement
			myStmt = myConn.prepareStatement(
					"insert into resources"
					+ "(name, artist, album, genre, length, releaseYear)"
					+ "value(?,?,?,?,?,?)"
					);
			// set params for music
			myStmt.setString(1, mResource.getTitle());
			myStmt.setString(2, mResource.getArtist());
			myStmt.setString(3, mResource.getAlbum());
			myStmt.setString(4, mResource.getGenre().name());
			myStmt.setDouble(5, mResource.getLength());
			myStmt.setInt(6, mResource.getReleaseYear());
			
			// sql insert
			myStmt.executeUpdate();
	
		}
		finally{
			close(myConn, myStmt, null);
		}
		
	}
	
	public void deleteMResource(String theResourceId) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			//convert resource id to int
			int resourceId = Integer.parseInt(theResourceId);
			
			//get connection to bd
			myConn = dataSource.getConnection();
			
			//set& execute prepared statement
			myStmt = myConn.prepareStatement(
					"delete from resources where id = ?");
			myStmt.setInt(1, resourceId);
			myStmt.executeUpdate();
			
			
		}finally{
			close(myConn, myStmt, null);
		}
	}
	
	
	private void close(Connection myConn, Statement myStmt,ResultSet myRs) {

		try{	
			if(myConn !=null)
				myConn.close();
			
			if(myStmt !=null)
				myStmt.close();
			
			if(myRs !=null)
				myRs.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public MResource getResource(String theResourceId) 
		throws Exception{
		
		MResource mResource = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int resourceId;
		
		try{
			resourceId = Integer.parseInt(theResourceId);

			
			//get connection tob
			myConn = dataSource.getConnection();
			
			//prepared statement
			myStmt = myConn.prepareStatement(
					"select * from resources where id = ?"
					);
			myStmt.setInt(1, resourceId);
			
			//execute query
			myRs = myStmt.executeQuery();
			
			//retrieve data from myRs
			if(myRs.next()){
				
				String title= myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");
				
				mResource = new MResource(resourceId, title, artist, album, genre, 
						length, releaseYear);
			}
			else
				throw new Exception("could not found ResourceId: "+resourceId);
				
				
			
			return mResource;
		}
		finally{
			close(myConn, myStmt, myRs);
		}
	}

	public void updateMResource(MResource mResource) 
		throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			//get connection to db
			myConn = dataSource.getConnection();
			
			//prepared statement
			myStmt = myConn.prepareStatement(
					"update resources set name  = ?,"
					+ "artist = ?,"
					+ "album = ?,"
					+ "genre = ?,"
					+ "length = ?,"
					+ "releaseYear = ?"
					+ " where id = ?;"
					);
			
			//set params
			myStmt.setString(1, mResource.getTitle());
			myStmt.setString(2, mResource.getArtist());
			myStmt.setString(3, mResource.getAlbum());
			myStmt.setString(4, mResource.getGenre().name());
			myStmt.setDouble(5, mResource.getLength());
			myStmt.setInt(6, mResource.getReleaseYear());
			myStmt.setInt(7, mResource.getId());

			
			//excute update
			myStmt.execute();
		}
		finally{
			close(myConn, myStmt, null);
		}
		
	}

	public User isUser(String theUsername, String thePassword) 
		throws Exception{

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.prepareStatement("select id from Users where username = ? "
					+ "and password = ?");

			//set params
			myStmt.setString(1, theUsername);
			myStmt.setString(2, thePassword);
			
			//query
			myRs = myStmt.executeQuery();
			User theUser = null;
			
			if(myRs.next()){
				System.out.println("User is found");				
				int id = myRs.getInt("id");
				theUser = new User(id, theUsername, thePassword);
				
			}
			else {
				System.out.println("Could not find User");
			}
			return theUser;
			
		}finally{
			close(myConn, myStmt, myRs);
		}
		
	}

	public boolean isAdmin(String theUsername, String thePassword) 
			throws Exception{

			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			Connection myConn = null;
			
			try{
				//Get connection to database
				myConn = dataSource.getConnection();
				
				//Create Statement and Query
				myStmt = myConn.prepareStatement("select id from Admin where username = ? "
						+ "and password = ?");

				//set params
				myStmt.setString(1, theUsername);
				myStmt.setString(2, thePassword);
				
				//query
				myRs = myStmt.executeQuery();
				
				if(myRs.next()){
					System.out.println("Admin is found");
					return true;
				}
				else {
					System.out.println("Could not find Admin");
					return false;
				}
				
				
			}finally{
				close(myConn, myStmt, myRs);
		}
	}


	public List<MResource> searchByTitle(String searchArea) throws Exception{
		
			List<MResource> mResources = new ArrayList<>();
			
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			Connection myConn = null;
			
			try{		
				//Get connection to database
				myConn = dataSource.getConnection();
				
				//Create Statement and Query
				myStmt = myConn.prepareStatement("select* from resources "
						+ "where name =?");
				//set params
				myStmt.setString(1, searchArea);
				//execute query
				myRs = myStmt.executeQuery();
				
				//Process result set
				while(myRs.next()){
					
					//retrieve data from result set
					
					int id = myRs.getInt("id");
					String title = myRs.getString("name");
					String artist = myRs.getString("artist");
					String album = myRs.getString("album");
					Genre genre = Genre.valueOf( myRs.getString("genre"));  
					double length = myRs.getDouble("length");
					int releaseYear = myRs.getInt("releaseYear");
	
					
					//creat resource object
					MResource tempMusic = new MResource(id, title, artist, album, 
							genre, length, releaseYear);
					System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
					
					//add to resources list
					mResources.add(tempMusic);
					
					
				}
				return mResources;
				
			}		
			finally{
				// close JDBC objects
				close(myConn, myStmt, myRs);		
			}
	}

	public List<MResource> searchByAlbum(String searchArea) throws Exception{
		
		List<MResource> mResources = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{		
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.prepareStatement("select* from resources "
					+ "where album =?");
			//set params
			myStmt.setString(1, searchArea);
			//execute query
			myRs = myStmt.executeQuery();
			
			//Process result set
			while(myRs.next()){
				
				//retrieve data from result set
				
				int id = myRs.getInt("id");
				String title = myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");

				
				//creat resource object
				MResource tempMusic = new MResource(id, title, artist, album, 
						genre, length, releaseYear);
				System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
				
				//add to resources list
				mResources.add(tempMusic);
				
				
			}
			return mResources;
			
		}		
		finally{
			// close JDBC objects
			close(myConn, myStmt, myRs);		
		}
	}

	public List<MResource> searchByArtist(String searchArea) throws Exception{

		List<MResource> mResources = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{		
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.prepareStatement("select* from resources "
					+ "where artist =?");
			//set params
			myStmt.setString(1, searchArea);
			//execute query
			myRs = myStmt.executeQuery();
			
			//Process result set
			while(myRs.next()){
				
				//retrieve data from result set
				
				int id = myRs.getInt("id");
				String title = myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");

				
				//creat resource object
				MResource tempMusic = new MResource(id, title, artist, album, 
						genre, length, releaseYear);
				System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
				
				//add to resources list
				mResources.add(tempMusic);
				
				
			}
			return mResources;
			
		}		
		finally{
			// close JDBC objects
			close(myConn, myStmt, myRs);		
		}
	}

	public List<MResource> searchByGenre(String searchArea) throws Exception{
		
		List<MResource> mResources = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{		
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.prepareStatement("select* from resources "
					+ "where genre =?");
			//set params
			myStmt.setString(1, searchArea);
			//execute query
			myRs = myStmt.executeQuery();
			
			//Process result set
			while(myRs.next()){
				
				//retrieve data from result set
				
				int id = myRs.getInt("id");
				String title = myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");

				
				//creat resource object
				MResource tempMusic = new MResource(id, title, artist, album, 
						genre, length, releaseYear);
				System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
				
				//add to resources list
				mResources.add(tempMusic);
				
				
			}
			return mResources;
			
		}		
		finally{
			// close JDBC objects
			close(myConn, myStmt, myRs);		
		}
	}


	public List<MResource> getPlaylist(int theuserid) throws Exception{

		List<MResource> mResources = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Connection myConn = null;
		
		try{		
			//Get connection to database
			myConn = dataSource.getConnection();
			
			//Create Statement and Query
			myStmt = myConn.prepareStatement("select r.* "
					+ "from resources r, users u, playlist p, containplaylist c "
					+ "where u.id = ? "
					+ "and p.createdBy = u.id "
					+ "and c.listedIn = p.id "
					+ "and r.id = c.contains");
			//set params
			myStmt.setInt(1,theuserid);
			//execute query
			myRs = myStmt.executeQuery();
			
			//Process result set
			while(myRs.next()){
				
				//retrieve data from result set
				
				int id = myRs.getInt("id");
				String title = myRs.getString("name");
				String artist = myRs.getString("artist");
				String album = myRs.getString("album");
				Genre genre = Genre.valueOf( myRs.getString("genre"));  
				double length = myRs.getDouble("length");
				int releaseYear = myRs.getInt("releaseYear");

				
				//creat resource object
				MResource tempMusic = new MResource(id, title, artist, album, 
						genre, length, releaseYear);
				System.out.println("object tempResource:  "+tempMusic.getId()+tempMusic.getTitle());
				
				//add to resources list
				mResources.add(tempMusic);
				
				
			}
			return mResources;
			
		}		
		finally{
			// close JDBC objects
			close(myConn, myStmt, myRs);		
		}
	}


	public void deleteFavorite(String theUserId,String theMusicId) 
			throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			//convert resource id to int
			int resourceId = Integer.parseInt(theMusicId);
			int userId = Integer.parseInt(theUserId);
			
			//get connection to bd
			myConn = dataSource.getConnection();
			
			//set& execute prepared statement
			myStmt = myConn.prepareStatement(
					"delete from containplaylist where contains = ? and listedIn = ?"
					);
			myStmt.setInt(1, resourceId);
			myStmt.setInt(2, userId);
			myStmt.executeUpdate();
			
			
		}finally{
			close(myConn, myStmt, null);
		}
		
		
	}

	public void addFavorite(int theUserId, int theMusicId) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			// get connection to db
			myConn = dataSource.getConnection();
			
			// specified prepared statement
			myStmt = myConn.prepareStatement(
					"insert into containplaylist value(?,?)"
					);
			
			// set params for music
			myStmt.setInt(1, theMusicId);
			myStmt.setInt(2, theUserId);
			
			// sql insert
			myStmt.executeUpdate();
	
		}
		finally{
			close(myConn, myStmt, null);
		}

	}

	public List<Comments> getComments(int theMusicId, int theUserId) 
			throws Exception{

		List<Comments> mComments = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try{
			
			//get connection tob
			myConn = dataSource.getConnection();
			
			//prepared statement
			myStmt = myConn.prepareStatement(
					"select * from comments where comments = ? and commentedBy = ?"
					);
			myStmt.setInt(1, theMusicId);
			myStmt.setInt(2, theUserId);
			
			//execute query
			myRs = myStmt.executeQuery();
			
			//retrieve data from myRs
			while(myRs.next()){
				
				String content= myRs.getString("content");
				
				//creat comment object
				Comments tempComments = new Comments(content, theMusicId, theUserId);
				
				//add to comment list
				mComments.add(tempComments);				
			}			
			
			return mComments;
		}
		finally{
			close(myConn, myStmt, myRs);
		}
	}

	public void addComments(String theContent, int theUserId, int theMusicId) 
		throws Exception{

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			// get connection to db
			myConn = dataSource.getConnection();
			
			// specified prepared statement
			myStmt = myConn.prepareStatement(
					"insert into comments (content, comments, commentedBy) "
					+ "value(?, ? ,?)"
					);
			
			// set params for music
			myStmt.setString(1, theContent);
			myStmt.setInt(2, theMusicId);
			myStmt.setInt(3, theUserId);
			
			// sql insert
			myStmt.executeUpdate();
	
		}
		finally{
			close(myConn, myStmt, null);
		}
		
	}

	public void addUser(String username, String password) 
		throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			// get connection to db
			myConn = dataSource.getConnection();
			
			// specified prepared statement
			myStmt = myConn.prepareStatement(
					"insert into Users (username, password, assignedBy) "
					+ "value(?, ? ,1)"
					);
			
			// set params for music
			myStmt.setString(1, username);
			myStmt.setString(2, password);
			
			// sql insert
			myStmt.executeUpdate();
	
		}
		finally{
			close(myConn, myStmt, null);
		}
			
	}

	public void addLib(String username, String password) 
			throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt1 = null;
		PreparedStatement myStmt2 = null;
		ResultSet Rs = null;
		
		try{
			// get connection to db
			myConn = dataSource.getConnection();
			
			// specified prepared statement
			myStmt1 = myConn.prepareStatement(
					"select id from Users "
					+ "where username = ?"
					+ "and password = ?"
					);
			
			// set params 
			myStmt1.setString(1, username);
			myStmt1.setString(2, password);
			
			// sql query
			myStmt1.executeQuery();
			
			int id = Rs.getInt(1);
			
			myStmt2 = myConn.prepareStatement(
					"insert into playlist (name, createdBy) "
							+ "value('myplayst' ,?)"
					);
			myStmt2.executeUpdate();
	
		}
		finally{
			close(myConn, myStmt1, null);
			close(myConn, myStmt2, null);
		}
		
		
	}


	
	
}