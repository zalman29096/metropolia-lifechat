/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.HashMap;

/**
 *
 * @author kirak
 */
public class Users {
    private final HashMap<String, User> users;
    
    private Users() {
        this.users = new HashMap();
    }
    
    public static Users getInstance() {
        return UsersHolder.INSTANCE;
    }
    
    private static class UsersHolder {

        private static final Users INSTANCE = new Users();
    }
    
    public void addUser(String username, User user){
        if (!this.users.containsKey(username)){
            this.users.put(username, user);
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
