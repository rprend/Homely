package org.homely;

import java.io.Serializable;

public class Room implements Serializable {
    String name;
    int imageId;

    public Room(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return this.name;
    }

    public int getImage_id() {
        return this.imageId;
    }
}
