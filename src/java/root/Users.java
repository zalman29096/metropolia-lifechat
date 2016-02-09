/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Collection;
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

    public boolean addUser(String username, User user) {
        if (!this.users.containsKey(username)) {
            this.users.put(username, user);
            return true;
        } else {
            return false;
        }
    }

    public boolean signIn(String username, String password) {
        if (this.users.containsKey(username)) {
            if (this.users.get(username).Password().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUsers(String currentUser) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : this.users.values()) {
            if (!user.getUsername().equals(currentUser)) {
                users.add(user);
            }
        }
        return users;
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }

    public User getUser(String username) {
        return this.users.get(username);
    }
}
