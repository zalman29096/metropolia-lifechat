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
public interface Chat {
    public boolean hasUser(String username);
    public void addUser(String username);
    public void addMessage(String username, String message, String timestamp);
    public ArrayList<HistoryEntry> getHistory();
    public ArrayList<String> getUsers();
}
