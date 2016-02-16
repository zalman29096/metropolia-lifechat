/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kirak
 */
@XmlRootElement
public class HistoryEntry {

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
    @XmlElement
    public String getMessage() {
        return message;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    /**
     * @return the timestamp
     */
    @XmlElement
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return this.username + " : " + this.message + " : " + this.timestamp;
    }

}
