package com.semihbkgr.gorun.tutorial;

public class Subject {

    private int _id;
    private String title;
    private String description;

    public Subject(int _id, String title, String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
