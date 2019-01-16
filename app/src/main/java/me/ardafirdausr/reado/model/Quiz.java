package me.ardafirdausr.reado.model;

import java.io.Serializable;

// DATA MODEL OF QUIZ
// IMPLEMENT SERIALIZABLE, OBJECT CAN BE TRANSFERED AS BYTE DATA
public class Quiz implements Serializable {

    int id, stage, level;
    String question, answere, media;

    public Quiz(){ }

    public Quiz(int id, int stage, int level, String question, String answere, String media) {
        this.id = id;
        this.stage = stage;
        this.level = level;
        this.question = question;
        this.answere = answere;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswere() {
        return answere;
    }

    public void setAnswere(String answere) {
        this.answere = answere;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
