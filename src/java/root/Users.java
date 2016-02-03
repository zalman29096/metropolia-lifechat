/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.HashMap;
import javax.ws.rs.client.Client;

/**
 *
 * @author kirak
 */
public class Users {
    private final HashMap<String, User> users;
    private final HashMap<String, Client> clients;
    
    private Users() {
        this.users = new HashMap();
        this.clients = new HashMap();
    }
    
    public static Users getInstance() {
        return UsersHolder.INSTANCE;
    }
    
    private static class UsersHolder {

        private static final Users INSTANCE = new Users();
    }
    
    public boolean addUser(String username, User user){
        if (!this.users.containsKey(username)){
            this.users.put(username, user);
            return true;
        }else
            return false;
    }
    
    public void addUserClient(String username, Client client){
        if (!this.users.containsKey(username)){
            this.clients.put(username, client);
        }
    }
    
    public boolean signIn(String username, String password){
        if (this.users.containsKey(username)){
            if(this.users.get(username).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
