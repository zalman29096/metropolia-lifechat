/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author kirak
 */
public class ChatMessageDecoder implements Decoder.Text<ChatMessage>{

    @Override
    public ChatMessage decode(String message) throws DecodeException {
        ChatMessage chatMessage = new ChatMessage();
        JsonObject json = Json.createReader(new StringReader(message)).readObject();
        chatMessage.setFlag(json.getBoolean("flag"));
        chatMessage.setChat(json.getString("chat"));
        chatMessage.setRoom(json.getString("room"));
        chatMessage.setRole(json.getString("role"));
        chatMessage.setUsernameTo(json.getString("usernameTo"));
        chatMessage.setUsername(json.getString("username"));
        chatMessage.setMessage(json.getString("message"));
        return chatMessage;
    }

    @Override
    public boolean willDecode(String message) {
        boolean flag = true;
        try {
            Json.createReader(new StringReader(message)).readObject();
        }
        catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
