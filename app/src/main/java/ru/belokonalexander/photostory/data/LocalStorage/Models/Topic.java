package ru.belokonalexander.photostory.data.LocalStorage.Models;

/**
 * Created by Alexander on 16.05.2017.
 */

public class Topic {

    private String title;
    private String desc;
    private Long id;

    public Topic(Long id) {
        this.id = id;
        desc = " Desc: " + id;
        title = " Title: " + id;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
