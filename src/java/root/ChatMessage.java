/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

/**
 *
 * @author kirak
 */
public class ChatMessage {
    private boolean flag; //true:system message; false:notSystem message
    private String chat;
    private String room;
    private String role;
    private String username;
    private String usernameTo;
    private String message;

    public ChatMessage() {
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the chat
     */
    public String getChat() {
        return chat;
    }

    /**
     * @param chat the chat to set
     */
    public void setChat(String chat) {
        this.chat = chat;
    }

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the usernameTo
     */
    public String getUsernameTo() {
        return usernameTo;
    }

    /**
     * @param usernameTo the usernameTo to set
     */
    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
