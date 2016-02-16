/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kirak
 */
public class ChatsCollection {

    private final Map<Integer, Chat> chats;
    private int chatCounter;
    public int doctorChatId;
    public int nurseChatId;
    public HashMap<String, Integer> roomChats;

    private ChatsCollection() {
        this.chatCounter = 0;
        this.chats = Collections.synchronizedMap(new HashMap<Integer, Chat>());
        this.roomChats = new HashMap<>();
    }

    public static ChatsCollection getInstance() {
        return ChatsCollectionHolder.INSTANCE;
    }

    private static class ChatsCollectionHolder {

        private static final ChatsCollection INSTANCE = new ChatsCollection();
    }

    public void addMessage(int chatId, String username, String message) {
        if (this.chats.containsKey(chatId)) {
            this.chats.get(chatId).addMessage(username, message);
        }
    }

    public void addRoomChat(String roomNumber) {
        this.addChat(new RoomChat(roomNumber, this.chatCounter));
        this.roomChats.put(roomNumber, this.chatCounter);
    }

    public void addGlobalChat(String role) {
        this.addChat(new GlobalChat(role, this.chatCounter));
        if (role.equals("doctor")) {
            this.doctorChatId = this.chatCounter;
        } else if (role.equals("nurse")) {
            this.nurseChatId = this.chatCounter;
        }
    }

    public void addPrivateChat(String username1, String username2) {
        this.addChat(new PrivateChat(username1, username2, this.chatCounter));
    }

    public void addPrivateGroupChat() {
        this.addChat(new PrivateGroupChat(this.chatCounter));
    }

    public void addUsertoChat(int chatId, String username) {
        this.chats.get(chatId).addUser(username);
    }

    private void addChat(Chat chat) {
        this.chats.put(chat.getChatId(), chat);
        this.chatCounter++;
    }
    
    /*public ArrayList<Integer> getRoomsId(String username){
        ArrayList<Integer> retval = new ArrayList<>();
        ArrayList<RoomChat> roomChats = this.getUserChats(username, RoomChat.class);
        for (RoomChat chat: roomChats){
            retval.add(chat.getChatId());
        }
        return retval;
    } */

    /*public ArrayList<Chat> getAllUserChats(String username) {
        ArrayList<Chat> retval = new ArrayList<>();
        for (Chat chat : this.chats.values()) {
            if (chat.hasUser(username)) {
                retval.add(chat);
            }
        }
        return retval;
    }*/
    
    
    public <T extends Chat> ArrayList<T> getUserChats(String username, Class<T> type) {
        ArrayList<T> retval = new ArrayList<>();
        for (Chat chat : this.chats.values()) {
            if (chat.hasUser(username) && chat.getClass() == type) {
                retval.add((T) chat);
            }
        }
        return retval;
    }
    
    /*public ArrayList<HistoryEntry> getChatHistory(int chatId, String username){
        return this.chats.get(chatId).getHistory(username);
    }*/
}
