package ru.belokonalexander.photostory.data.LocalStorage.Models;

import java.util.Random;

import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 16.05.2017.
 */

public class Topic {

    private String title;
    private String desc;
    private Long id;

    static long count = 0;

    public Topic() {
        id = ++count;
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
