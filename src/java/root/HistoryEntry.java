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
class HistoryEntry {

    private final String message;
    private final String timestamp;
    private final String username;

    public HistoryEntry(String username, String message, String timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return this.username + " : " + this.message + " : " + this.timestamp;
    }

}
