/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kirak
 */
@XmlRootElement
public class PrivateChat extends Chat{
    
    public PrivateChat() {
    } 

    public PrivateChat(int chatId) {
        super(chatId);
    }
    
    public boolean removeUser(String username){
        boolean retval = false;
        if (this.hasUser(username)) {
            this.getUsers().remove(username);
            retval = true;
        }
        return retval;
    }
    /*private String username1;
    private String username2;

    public PrivateChat() {
    }

    public PrivateChat(String username1, String username2, int chatId) {
        super(chatId);
        this.username1 = username1;
        this.username2 = username2;
    }

    @Override
    public boolean hasUser(String username) {
        return this.username1.equals(username) || this.username2.equals(username);
    }

    @Override
    public void addUser(String username) {
    }

    @XmlElement
    @Override
    public ArrayList<String> getUsers() {
        ArrayList<String> retval = new ArrayList<>();
        retval.add(this.username1);
        retval.add(this.username2);
        return retval;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrivateChat other = (PrivateChat) obj;
        if (!Objects.equals(this.username1, other.username1)) {
            return false;
        }
        if (!Objects.equals(this.username2, other.username2)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.username1);
        hash = 67 * hash + Objects.hashCode(this.username2);
        return hash;
    }*/

   
    
}
