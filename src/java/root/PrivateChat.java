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
public class PrivateChat implements Chat {

    private final ArrayList<HistoryEntry> history;
    private final String username1;
    private final String username2;
    private final int chatId;

    public PrivateChat(String username1, String username2, int chatId) {
        this.history = new ArrayList<>();
        this.username1 = username1;
        this.username2 = username2;
        this.chatId = chatId;
    }

    @Override
    public boolean hasUser(String username) {
        return this.username1.equals(username) || this.username2.equals(username);
    }

    @Override
    public void addUser(String username) {
    }

    @Override
    public void addMessage(String username, String message, String timestamp) {
        if (this.hasUser(username)) {
            this.history.add(new HistoryEntry(username, message, timestamp));
        }
    }

    @Override
    public ArrayList<HistoryEntry> getHistory() {
        return this.history;
    }

    @Override
    public ArrayList<String> getUsers() {
        ArrayList<String> retval = new ArrayList<>();
        retval.add(this.username1);
        retval.add(this.username2);
        return retval;
    }
}
