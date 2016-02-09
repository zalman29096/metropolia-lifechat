/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kirak
 */
public class PrivateChats {

    private final HashMap<String, ArrayList<String>> privateChats;

    private PrivateChats() {
        this.privateChats = new HashMap<>();
    }

    public static PrivateChats getInstance() {
        return PrivateChatsHolder.INSTANCE;
    }

    private static class PrivateChatsHolder {

        private static final PrivateChats INSTANCE = new PrivateChats();
    }

    private boolean addChat(String username, String chatUsername) {
        if (this.privateChats.containsKey(username)) {
            if (this.privateChats.get(username).contains(chatUsername)) {
                return false;
            }else {
                this.privateChats.
            }
            
        }
    }
}
