package ru.belokonalexander.photostory.Moxy.Strategy;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

import java.util.Iterator;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 10.05.2017.
 */

public class TagAndTypeStrategy implements StateStrategy {

    private enum StrategyType {
        SINGLE, CHAIN;
    }

    private String delimiter = "$";

    private String getTag(String incomingTag){
        return incomingTag.substring(0,incomingTag.lastIndexOf(delimiter));
    }

    private StrategyType getType(String incomingTag){
        return StrategyType.valueOf(incomingTag.substring(incomingTag.lastIndexOf(delimiter)+1,incomingTag.length()).toUpperCase());
    }

    @Override
    public <View extends MvpView> void beforeApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {
        Iterator<ViewCommand<View>> iterator = currentState.iterator();



        if(incomingCommand.getStrategyType().equals(TagAndTypeStrategy.class)) {
            String incomingTag = incomingCommand.getTag();
            StrategyType type = getType(incomingTag);
            String tag = getTag(incomingTag);

            if (type==StrategyType.CHAIN)
                currentState.add(incomingCommand);
            else {
                while (iterator.hasNext()) {
                    ViewCommand<View> entry = iterator.next();

                    if (entry.getStrategyType().equals(TagAndTypeStrategy.class) && getTag(entry.getTag()).equals(tag)) {
                                iterator.remove();
                        }
                    }

                    currentState.add(incomingCommand);
                }
            }
    }

    @Override
    public <View extends MvpView> void afterApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {
        // pass
    }
}