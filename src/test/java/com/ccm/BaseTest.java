package com.ccm;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;

public abstract class BaseTest {

    public void onStart(@Observes StartupEvent ev) {
        setUp();
    }

    protected abstract void setUp ();
}
