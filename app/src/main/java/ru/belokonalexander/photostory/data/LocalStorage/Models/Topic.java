package ru.belokonalexander.photostory.data.LocalStorage.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Alexander on 16.05.2017.
 */

@Entity
public class Topic {

    @NotNull
    private String title;


    private String desc;


    @Id
    private Long id;


    @Generated(hash = 1630674951)
    public Topic(@NotNull String title, String desc, Long id) {
        this.title = title;
        this.desc = desc;
        this.id = id;
    }

    @Keep
    public static Topic getDummy(int id){
        return new Topic("Dummy title: " + id, "desc: " + id, (long) id);
    }

    @Generated(hash = 849012203)
    public Topic() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title + " / " + desc + " " + id;
    }
}
