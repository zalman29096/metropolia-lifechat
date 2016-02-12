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
public class Status {

    private boolean adminAdded;
    private boolean roomsCreated;
    private boolean globalCreated;

    private Status() {
        this.adminAdded = false;
        this.globalCreated = false;
        this.roomsCreated = false;
    }

    public static Status getInstance() {
        return StatusHolder.INSTANCE;
    }

    private static class StatusHolder {

        private static final Status INSTANCE = new Status();
    }

    public void setAdminAdded() {
        this.adminAdded = true;
    }

    public void setGlobalCreated() {
        this.globalCreated = true;
    }

    public void setRoomsCreated() {
        this.roomsCreated = true;
    }

    public boolean isAdminAdded() {
        return adminAdded;
    }

    public boolean isGlobalCreated() {
        return globalCreated;
    }

    public boolean isRoomsCreated() {
        return roomsCreated;
    }

}
