/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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

    @POST
    @Path("/signUp/{username}")
    @Consumes(MediaType.APPLICATION_XML)
    public void addUsers(@PathParam("username") String username, User user) {
        this.users.addUser(username, user);
    }

    @POST
    @Path("/signIn/{username}/{password}")
    public boolean login(@PathParam("username") String username, @PathParam("password") String password) {
        if (this.users.signIn(username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            return true;
        }
        return false;
    }
}