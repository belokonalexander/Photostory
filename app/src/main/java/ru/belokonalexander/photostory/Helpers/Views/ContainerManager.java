package ru.belokonalexander.photostory.Helpers.Views;

/**
 * Created by Alexander on 23.04.2017.
 */

public class ContainerManager {

    private int containerId;
    private String containerTag;

    public ContainerManager(int containerId) {
        this.containerId = containerId;
        containerTag = String.valueOf(containerId);
    }

    /**
     * следит за каждым контейнером у фрагмента
     */
    private int fragmentsCount = 0;

    public String addNewFragment(boolean starter){
        if(starter)
            fragmentsCount = 0;
        else fragmentsCount++;
        return containerTag+"["+String.valueOf(fragmentsCount)+"]";
    }

    public int getContainerId() {
        return containerId;
    }

    public void popNewFragment(){
        fragmentsCount--;
    }

    public int getTopFragment(){
        return fragmentsCount;
    }

    public String getTopFragmentTag(){
        return containerTag+"[" + getTopFragment() + "]";
    }

    public static ContainerManager find(ContainerManager[] cms, int id){
        for(ContainerManager cm : cms){
            if(cm.getContainerId()==id)
                return cm;
        }

        return null;
    }

}
