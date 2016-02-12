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
    private final ArrayList<HistoryEntry> history;
    private final ArrayList<String> users;
    private final int chatId;

    public RoomChat(int roomNumber, int id) {
        this.users = new ArrayList<>();
        this.roomNumber = roomNumber;
        this.history = new ArrayList<>();
        this.chatId = id;
    }

    @Override
    public boolean hasUser(String username) {
        return this.users.contains(username);
    }

    @Override
    public void addUser(String username) {
        if (!this.hasUser(username)) {
            ArrayList<String> rooms = Users.getInstance().getUser(username).getRooms();
            if (rooms != null) {
                if (rooms.contains(this.roomNumber)) {
                    this.users.add(username);
                }
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

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public ArrayList<String> getUsers() {
        return this.users;
    }
}
