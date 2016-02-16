/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;

/**
 *
 * @author kirak
 */
public class PrivateChat extends Chat{

    private final String username1;
    private final String username2;

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

    @Override
    public ArrayList<String> getUsers() {
        ArrayList<String> retval = new ArrayList<>();
        retval.add(this.username1);
        retval.add(this.username2);
        return retval;
    }
}
