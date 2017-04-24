package ru.belokonalexander.photostory.Helpers;

import javax.inject.Inject;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Settings {

    public enum WorkMode {
        Test, Release
    }

    private final WorkMode workMode;


    public Settings(WorkMode workMode) {
        this.workMode = workMode;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }


    //tags
    public final String BACKPRESS_ENABLE = "BACKPRESS";
    public final String TOPIC_TAG = "TOPIC";


    //params


    public final int CLICK_DELAY = 50;
}
