/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author kirak
 */
@Path("/chats")
public class ChatsResource {
    
    @Context
    private HttpServletRequest request;

    private final ChatsCollection chats;
    private final Users users;

    public ChatsResource() {
        this.chats = ChatsCollection.getInstance();
        this.users = Users.getInstance();
    }

    /*@Path("/global")
    @GET
    public int getGlobalId() {
        int retval = 0;
        String role = this.users.getUser(this.checkSession()).getRole();
        if (role.equals("doctor")) {
            retval = this.chats.doctorChatId;
        } else if (role.equals("nurse")) {
            retval = this.chats.nurseChatId;
        }
        return retval;
    }*/
    
    @Path("/global")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<GlobalChat> getRoomsId(){
        Collection<GlobalChatt> retval = new ArrayList<>();
        retval.add(new GlobalChat("nurse", 34));
        retval.add(new GlobalChat("doctor", 65));
        return retval;
    }
    
    @Path("/rooms")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<RoomChat> getRooms(){
        return this.chats.getUserChats(this.checkSession(), RoomChat.class);
    }

    public void createRoomChats(int amount) {
        for (int i = 0; i < amount; i++) {
            this.chats.addRoomChat(Integer.toString(i));
        }
        Status.getInstance().setRoomsCreated();
    }

    public void createGlobalChats() {
        this.chats.addGlobalChat("doctor");
        this.chats.addGlobalChat("nurse");
        Status.getInstance().setGlobalCreated();
    }

    public void addUserToGlobalChat(String role, String username) {
        int chatId = 0;
        if (role.equals("doctor")) {
            chatId = this.chats.doctorChatId;
        } else if (role.equals("nurse")) {
            chatId = this.chats.nurseChatId;
        }
        this.chats.addUsertoChat(chatId, username);
    }

    public void addUserToRoomChat(ArrayList<String> rooms, String username) {
        if (rooms != null) {
            for (String room : rooms){
                this.chats.addUsertoChat(this.chats.roomChats.get(room), username);
            }
        }
    }

    private String checkSession() {
        HttpSession session = request.getSession(false);
        String username = null;
        if (session != null) {
            username = session.getAttribute("username").toString();
        }
        return username;
    }
}
