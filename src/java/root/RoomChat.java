package root;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kirak
 */
public class RoomChat implements Chat {

    private final int roomNumber;
    private final HashMap<String, HistoryEntry> history;
    private final ArrayList<String> users;
    private final int id;

    public RoomChat(int roomNumber, int id) {
        this.users = new ArrayList<>();
        this.roomNumber = roomNumber;
        this.history = new HashMap<>();
        this.id = id;
    }

    @Override
    public boolean hasUser(String username) {
        return this.users.contains(username);
    }

    @Override
    public void addUser(String username) {
        if (!this.hasUser(username)) {
            this.users.add(username);
        }
    }

    @Override
    public void addMessage(String username, String message, String timestamp) {
        if (this.hasUser(username)) {
            this.history.put(username, new HistoryEntry(message, timestamp));
        }
    }

    @Override
    public HashMap<String, HistoryEntry> getHistory() {
        return this.history;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

}
