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

    public void addMessage(int chatId, String username, String message, String timestamp) {
        if (this.chats.containsKey(chatId)) {
            this.chats.get(chatId).addMessage(username, message, timestamp);
        }
    }

    public void addRoomChat(int roomNumber) {
        this.addChat(new RoomChat(roomNumber, this.chatCounter));
    }

    public void addGlobalChat(String role) {
        this.addChat(new GlobalChat(role, chatCounter));
    }

    public void addPrivateChat(String username1, String username2) {
        this.addChat(new PrivateChat(username1, username2, chatCounter));
    }

    public void addPrivateGroupChat() {
        this.addChat(new PrivateGroupChat(this.chatCounter));
    }

    private void addChat(Chat chat) {
        this.chats.put(this.chatCounter, chat);
        this.chatCounter++;
    }

    /*public ArrayList<Chat> getAllUserChats(String username){
        ArrayList<Chat> retval = new ArrayList<>();
        for(Chat chat : this.chats.values()){
            if (chat.hasUser(username)){
                retval.add(chat);
            }
        }
        return retval;
    }*/
    public ArrayList<RoomChat> getUserRoomChats(String username) {
        ArrayList<RoomChat> retval = new ArrayList<>();
        for (Chat chat : this.chats.values()) {
            if (chat.hasUser(username) && chat instanceof RoomChat) {
                retval.add((RoomChat) chat);
            }
        }
        return retval;
    }

}
