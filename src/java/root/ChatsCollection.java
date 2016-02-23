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
public class ChatsCollection {

    private final Map<Integer, Chat> chats;
    private int chatCounter;

    private ChatsCollection() {
        this.chatCounter = 0;
        this.chats = Collections.synchronizedMap(new HashMap<Integer, Chat>());
    }

    public static ChatsCollection getInstance() {
        return ChatsCollectionHolder.INSTANCE;
    }

    private static class ChatsCollectionHolder {

        private static final ChatsCollection INSTANCE = new ChatsCollection();
    }

    public HistoryEntry addMessage(int chatId, String username, String message, int flag, String decsription) {
        HistoryEntry retval = null;
        if (this.chats.containsKey(chatId)) {
            retval = this.chats.get(chatId).addMessage(username, message, flag);
        }
        return retval;
    }

    public void addRoomChat(String roomNumber) {
        this.addChat(new RoomChat(roomNumber, this.chatCounter));
    }

    public void addGlobalChat(String role) {
        this.addChat(new GlobalChat(role, this.chatCounter));
    }

    public int addPrivateChat() {
        int chatId = this.chatCounter;
        this.addChat(new PrivateChat(chatId));
        return chatId;
    }

    public boolean leavePrivateChat(int chatId, String username) {
        boolean retval = false;
        try {
            PrivateChat chat = (PrivateChat) this.chats.get(chatId);
            retval = chat.removeUser(username);
        } catch (Exception e) {
        }
        return retval;
    }

    /*public int addPrivateChat(String username1, String username2) {
        int chatId = -1;
        if (Users.getInstance().hasUser(username1) && Users.getInstance().hasUser(username2)) {
            chatId = this.chatCounter;
            if (this.chats.containsValue(new PrivateChat(username1, username2, chatId))) {
                chatId = -1;
            } else {
                this.addChat(new PrivateChat(username1, username2, chatId));
            }
        }
        return chatId;
    }*/
    
    public boolean addUserToChat(int chatId, String username) {
        boolean retval = false;
        if (Users.getInstance().hasUser(username)) {
            System.out.println("addUserToChat");
            this.chats.get(chatId).addUser(username);
            retval = true;
        }
        return retval;
    }

    private synchronized void addChat(Chat chat) {
        this.chats.put(chat.getChatId(), chat);
        this.chatCounter++;
    }

    public Collection<String> getUsers(int chatId) {
        return this.chats.get(chatId).getUsers();
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
    
    public int getNewMessagesCount(int chatId, String username){
        return this.chats.get(chatId).getNewMessagesCount(username);
    }
            
    public <T extends Chat> ArrayList<T> getChats(Class<T> type) {
        ArrayList<T> retval = new ArrayList<>();
        for (Chat chat : this.chats.values()) {
            if (chat.getClass() == type) {
                retval.add((T) chat);
            }
        }
        return retval;
    }

    public <T extends Chat> ArrayList<T> getUserChats(String username, Class<T> type) {
        ArrayList<T> retval = new ArrayList<>();
        for (Chat chat : this.chats.values()) {
            if (chat.hasUser(username) && chat.getClass() == type) {
                retval.add((T) chat);
            }
        }
        return retval;
    }

    public ArrayList<HistoryEntry> getChatHistory(int chatId, String username) {
        return this.chats.get(chatId).getHistory(username);
    }
}
