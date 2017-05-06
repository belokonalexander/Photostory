package ru.belokonalexander.photostory.Helpers;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Logger {

    private static final String main = "TAG";

    public static void logThis(Object str){
        Log.e(main, "--> " + str);
    };

    public Logger() {
    }
}
