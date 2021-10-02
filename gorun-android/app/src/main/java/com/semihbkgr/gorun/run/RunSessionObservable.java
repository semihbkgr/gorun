package com.semihbkgr.gorun.run;

public interface RunSessionObservable {

    boolean registerObserver(RunSessionObserver observer);

    boolean unregisterObserver(RunSessionObserver observer);

}
