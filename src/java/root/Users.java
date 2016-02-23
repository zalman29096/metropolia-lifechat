/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kirak
 */
public class Users {

    private final Map<String, User> users;

    private Users() {
        this.users = Collections.synchronizedMap(new HashMap<String, User>());
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
        ArrayList<User> retval = new ArrayList<>();
        for (User user : this.users.values()) {
            if (!user.getUsername().equals(currentUser) && !user.getUsername().equals("admin")) {
                retval.add(user);
            }
        }
        return retval;
    }

    public ArrayList<String> getOrderlies() {
        ArrayList<String> retval = new ArrayList<>();
        Collection<User> users = Users.getInstance().getUsers();
        for (User user : users) {
            if (user.getRole().equals("orderly")) {
                retval.add(user.getUsername());
            }
        }
        return retval;
    }

    public boolean hasUser(String username) {
        boolean retval = false;
        if (this.users.containsKey(username)) {
            retval = true;
        }
        return retval;
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void addAdmin() {
        User admin = new User("", "", "", "admin");
        admin.setUsername("admin");
        this.addUser("admin", admin);
    }
}
