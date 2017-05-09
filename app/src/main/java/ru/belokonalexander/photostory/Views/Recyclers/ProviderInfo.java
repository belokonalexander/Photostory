package ru.belokonalexander.photostory.Views.Recyclers;

/**
 * Created by Alexander on 09.05.2017.
 */

public class ProviderInfo {

    UpdateMode inputUpdateMode;

    boolean allDataWasObtained = false;

    public UpdateMode getInputUpdateMode() {
        return inputUpdateMode;
    }

    public void setInputUpdateMode(UpdateMode inputUpdateMode) {
        this.inputUpdateMode = inputUpdateMode;
    }

    public boolean isAllDataWasObtained() {
        return allDataWasObtained;
    }

    public void setAllDataWasObtained(boolean allDataWasObtained) {
        this.allDataWasObtained = allDataWasObtained;
    }

    public enum UpdateMode{
        UPDATE, REWRITE;
    }

    public ProviderInfo(UpdateMode inputUpdateMode, boolean allDataWasObtained) {
        this.inputUpdateMode = inputUpdateMode;
        this.allDataWasObtained = allDataWasObtained;
    }
}
