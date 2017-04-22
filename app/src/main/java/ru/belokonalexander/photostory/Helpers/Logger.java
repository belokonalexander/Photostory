package ru.belokonalexander.photostory.Helpers;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Logger {

    private final String main = "TAG";

    public void logThis(Object str){
        Log.e(main, "--> " + str.toString());
    };

    public Logger() {
    }
}
