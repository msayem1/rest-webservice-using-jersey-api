package com.msayem.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msayem.entity.User;
import com.msayem.database.DatabaseConnection;

/**
 * Handles RESTful web service requests.
 * 
 * @author MSAYEM
 * 
 */
@Path("/")
public class JerseyRestWebservice {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@GET
	@Path("read")
	@Produces(MediaType.TEXT_HTML)
	public String readAllUser() {

		Connection con = null;		
		String sql = "SELECT * FROM user";
		String details = "";

		try {
			con = DatabaseConnection.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);          

			details = "<html><body>" 
				+  "<table border=1>"
				+    "<tr>"
				+	    "<td><Strong>Id </Strong></td>" 
				+     	"<td><Strong>Username </Strong></td>" 
				+ 		"<td><Strong>Password </Strong></td>" 
				+ 		"<td><Strong>Email </Strong></td>" 
				+ 		"<td><Strong>Action </Strong></td>" 
				+ 		"<td><Strong>Action </Strong></td>" 
				+    "</tr>";
			while (rs.next()) {
				details = details 
					+ 	 "<tr>"
					+ 		"<td>" + rs.getInt("userid") + "</td>"
					+       "<td>" + rs.getString("username") + "</td>"
					+       "<td>" + rs.getString("password") + "</td>"
					+       "<td>" + rs.getString("email") + "</td>"
					+       "<td>" + "<a href='http://localhost:8080/rest-webservice-using-jersey-api/rest/update/" + rs.getInt("userid") + "' >Edit</a>" + "</td>"
					+       "<td>" + "<a href='http://localhost:8080/rest-webservice-using-jersey-api/rest/delete/" + rs.getInt("userid") + "' >Delete</a>" + "</td>"
					+ 	 "</tr>";
			}
			details = details
				+  "</table>"
				+  "<br><br>"
				+  "<a href='http://localhost:8080/rest-webservice-using-jersey-api/' >Click here</a>"
				+  " to go back to Home page."
				+ "</body></html>"; 
		}
		catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {

			DatabaseConnection.close(con);
		}
		
		logger.debug("rest-webservice-using-jersey-api: readAllUser()");

		return details;
	}

	@GET 
	@Path("read/{userid}")
	@Produces({ MediaType.TEXT_HTML })
	public String readUserByd(@PathParam("userid") int userid) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "SELECT * FROM user WHERE userid = ?";
		String details = "";

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userid);

			ResultSet rs = ps.executeQuery();
			
			details = "<html><body>" 
				+  "<table border=1>"
				+    "<tr>"
				+	    "<td><Strong>Id </Strong></td>" 
				+     	"<td><Strong>Username </Strong></td>" 
				+ 		"<td><Strong>Password </Strong></td>" 
				+ 		"<td><Strong>Email </Strong></td>"
				+    "</tr>";			
			if (rs.next()){
				details = details 
					+ 	 "<tr>"
					+ 		"<td>" + rs.getInt("userid") + "</td>" 
					+       "<td>" + rs.getString("username") + "</td>" 
					+       "<td>" + rs.getString("password") + "</td>" 
					+       "<td>" + rs.getString("email") + "</td>" 
					+ 	 "</tr>";
			}
			details = details
				+  "</table>"  
				+  "<br><br>"
				+  "<a href='http://localhost:8080/rest-webservice-using-jersey-api/' >Click here</a>"
				+  " to go back to Home page."
				+ "</body></html>"; 
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally {
			
			DatabaseConnection.close(con);
		}

		return details;
	}
	
	@POST
	@Path("create")
	@Produces(MediaType.TEXT_HTML)
	public String createUser(
		@FormParam("userid") int userid,
		@FormParam("username") String username,
		@FormParam("password") String password,
		@FormParam("email") String email) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "insert into user"+"(userid, username, password, email) VALUES"+"(?, ?, ?, ?)";

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userid);
			ps.setString(2,username);
			ps.setString(3, password);
			ps.setString(4, email);

			ps.executeUpdate();     
		} 
		catch (Exception e) {

			System.out.println(e.getMessage());
		} 
		finally {

			DatabaseConnection.close(con);
		}

		logger.debug("rest-webservice-using-jersey-api: Creating User");

		return "<html>" 
			+   "<title>" 
			+ 		"REST Web Service in Jersy with JSP" 
			+   "</title>" 
			+   "<body>" 
			+ 		"<h3>Record Created Successfully</h3>" 
			+		"<br><br>"
			+		"<a href='http://localhost:8080/rest-webservice-using-jersey-api/' >Click here</a>"
			+		" to go back to Home page."
			+   "</body>" 
			+ "</html> ";
	}

	@PUT
	@Path("update/{userid}")
	@Consumes({ MediaType.APPLICATION_XML})
	@Produces({ MediaType.TEXT_HTML })
	public Response updateUser(
		@FormParam("userid") int userid,
		@FormParam("username") String username,
		@FormParam("password") String password,
		@FormParam("email") String email,
		User user) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE user SET username=?, password=?, email=? WHERE userid=?";

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, user.getUserid());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());

			return Response.ok(new Viewable("/index")).build();
		} 
		catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally {
			
			DatabaseConnection.close(con);
		}
	}

	@GET
	@Path("delete/{userid}")
	@Consumes({ MediaType.APPLICATION_XML})
	@Produces({ MediaType.TEXT_HTML })  
	public String deleteUser(@PathParam("userid") int userid) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "DELETE FROM user WHERE userid = ?";

		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userid);

			ps.executeUpdate();
		} 
		catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		finally {

			DatabaseConnection.close(con);
		}

		logger.debug("rest-webservice-using-jersey-api: Deleting User");
		return "<html> " 
			+   "<title>" 
			+ 		"REST Web Service in Jersy with JSP" 
			+   "</title>" 
			+   "<body>" 
			+ 		"<h3>Record Deleted Successfully</h3>" 
			+		"<br><br>"
			+		"<a href='http://localhost:8080/rest-webservice-using-jersey-api/' >Click here</a>"
			+		" to go back to Home page."
			+   "</body>" 
			+ "</html> ";
	}
}