/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

/**
 *
 * @author kirak
 */
public class PrivateGroupChat extends Chat {

    public PrivateGroupChat(int chatId) {
        super(chatId);
    }

    @Override
    public void addUser(String username) {
        if (!this.hasUser(username)) {
            this.users.add(username);
        }
    }

    public void removeUser(String username){
        if (this.hasUser(username)) {
            this.users.remove(username);
        }
    }
}
