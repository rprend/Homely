package org.homely;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class House implements Serializable {

    private String user;
    private int image;

    private List<Room> rooms = new ArrayList<>();

    public House(String user, int image_id) {
        this.user = user;
        this.image = image_id;
    }

    public void addRoom(String name, int image_id) {
        this.rooms.add(new Room(name, image_id));
    }

    public String getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.user + "'s Home";
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getImage() {
        return this.image;
    }
}
