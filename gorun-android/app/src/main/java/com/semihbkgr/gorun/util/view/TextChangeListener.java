package com.semihbkgr.gorun.util.view;

@FunctionalInterface
public interface TextChangeListener {

    void onChanged(Event event,String text);

    enum Event{
        APPEND,
        UPDATE
    }

}
