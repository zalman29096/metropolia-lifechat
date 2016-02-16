/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kirak
 */

@XmlTransient
@XmlSeeAlso({GlobalChat.class, RoomChat.class})
public abstract class Chat {

    private ArrayList<HistoryEntry> history;
    private ArrayList<String> users;
    private int chatId;

    public Chat() {
    }

    public Chat(int chatId) {
        this.history = new ArrayList<>();
        this.users = new ArrayList<>();
        this.chatId = chatId;
    }

    public boolean hasUser(String username) {
        return this.users.contains(username);
    }

    public void addMessage(String username, String message) {
        if (this.hasUser(username)) {
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            this.history.add(new HistoryEntry(username, message, timestamp));
        }
    }

    public ArrayList<HistoryEntry> getHistory(String username) {
        if (!this.hasUser(username)) {
            return null;
        }
        return this.history;
    }

    public void addUser(String username){
        this.users.add(username);
    }

    @XmlElement
    public Collection<String> getUsers() {
        return this.users;
    }

    @XmlElement
    public int getChatId() {
        return chatId;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    /**
     * @param chatId the chatId to set
     */
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }


}
