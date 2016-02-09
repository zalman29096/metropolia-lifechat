/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
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
@Path("/users")
@Singleton
public class UsersResource {

    // public static String username;
    public boolean statusOfRooms = false;
    public ChatsCollection chats;

    @Context
    private HttpServletRequest request;

    private final Users users;

    public UsersResource() {
        this.users = Users.getInstance();
        this.chats = ChatsCollection.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<User> getUsers() {
        return this.users.getUsers(this.checkSession());
    }

    /**
     * Checks the session
     *
     * @return
     */
    @Path("/auth")
    @GET
    public String checkSession() {
        HttpSession session = request.getSession(false);
        String username = null;
        if (session != null) {
            username = session.getAttribute("username").toString();
        }
        return username;
    }

    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User getUser() {
        String username = this.checkSession();
        return this.users.getUser(username);
    }

    /**
     * Log out and invalidate the session
     */
    @Path("/auth")
    @POST
    public void logOut() {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

    /**
     * Add a new user to users collection
     *
     * @param user - XML document from client, representing user object
     * @param password
     * @return
     */
    @POST
    @Path("/auth/signUp/{password}")
    @Consumes(MediaType.APPLICATION_XML)
    public boolean addUsers(User user, @PathParam("password") String password) {
        if (!this.statusOfRooms){
            this.createRoomChats(5);
        }
        user.Password(password);
        return this.users.addUser(user.getUsername(), user);
    }

    /**
     * Implements login functionality and session
     *
     * @param username
     * @param password
     * @return
     */
    @POST
    @Path("/auth/signIn/{username}/{password}")
    public boolean login(@PathParam("username") String username, @PathParam("password") String password) {
        if (this.users.signIn(username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            //this.username = username;
            return true;
        }
        return false;
    }

    private void createRoomChats(int amount) {
        for (int i = 0; i < amount; i++) {
            this.chats.addRoomChat(i);
        }
        this.statusOfRooms = true;
    }
}
