package me.ardafirdausr.reado.model;

import java.io.Serializable;

// DATA MODEL OF STAGE
// IMPLEMENT SERIALIZABLE, OBJECT CAN BE TRANSFERED AS BYTE DATA
public class Stage implements Serializable {

    int id;
    String name, description, media;

    public Stage(){}

    public Stage(int id, String name, String description, String media) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
