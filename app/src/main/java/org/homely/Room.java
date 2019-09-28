package org.homely;

import java.io.Serializable;

public class Room implements Serializable {
    String name;
    String imagePath;

    public Room(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return this.name;
    }

    public String getImagePath() {
        return this.imagePath;
    }
}
