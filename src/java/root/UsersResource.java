/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author kirak
 */
@Path("/auth")
public class UsersResource {

    @Context
    private HttpServletRequest request;

    private final Users users;
    
    public UsersResource() {
        this.users = Users.getInstance();
    }
    
    @GET
    public boolean checkSession() {
        HttpSession session = request.getSession(false);
        return session != null;
    }
    
    @POST
    public void logOut(){
        HttpSession session = request.getSession(false);
        session.invalidate();
    }
    /**
     * Add a new user to users collection
     * @param user - XML document from client, representing user object
     * @return 
     */
 
    @POST
    @Path("/signUp")
    @Consumes(MediaType.APPLICATION_XML)
    public boolean addUsers(User user) {
        return this.users.addUser(user.getUsername(), user);
    }

    /**
     * Implements login functionality and HTTPAuth
     * @param username 
     * @param password
     * @return 
     */
    @POST
    @Path("/signIn/{username}/{password}")
    public boolean login(@PathParam("username") String username, @PathParam("password") String password) {
        if (this.users.signIn(username, password)){
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            return true;
        }
        return false;
    }
    
    @PermitAll
    @GET
    @Path("/cabinet")
    @Produces(MediaType.TEXT_HTML)
    public String showCabinet(){
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }
}