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
public class ChatsCollection {
    
    private final HashMap<Integer, Chat> chats = new HashMap<>();
    private int chatCounter = 0;
    
    private ChatsCollection() {
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
    
    public void addRoomChat(int roomNumber){
        this.chats.put(chatCounter, new RoomChat(roomNumber, this.chatCounter));
        this.chatCounter++;
    }
    
    public ArrayList<Chat> getAllUserChats(String username){
        ArrayList<Chat> retval = new ArrayList<>();
        for(Chat chat : this.chats.values()){
            if (chat.hasUser(username)){
                retval.add(chat);
            }
        }
        return retval;
    }
}
