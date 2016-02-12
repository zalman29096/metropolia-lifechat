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
public class GlobalChat implements Chat {

    private final String role;
    private final ArrayList<HistoryEntry> history;
    private final ArrayList<String> users;
    private final int chatId;

    public GlobalChat(String role, int chatId) {
        this.role = role;
        this.history = new ArrayList<>();
        this.users = new ArrayList<>();
        this.chatId = chatId;
    }

    @Override
    public boolean hasUser(String username) {
        return this.users.contains(username);
    }

    @Override
    public void addUser(String username) {
        if (!this.hasUser(username)) {
            if (Users.getInstance().getUser(username).getRole().equals(this.role)) {
                this.users.add(username);
            }
        }
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
        return this.users;
    }
}
