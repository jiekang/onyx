package org.onyx.system.base.program;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseClass {

    private final long DEFAULT_WAIT = 1000l;
    private AtomicBoolean b = new AtomicBoolean(true);

    public void begin() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodOne();
        }
    }

    private void methodOne() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodTwo();
        }
    }

    private void methodTwo() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodThree();
            methodFour();
            methodOne();
        }
    }

    private void methodThree() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
        }
    }

    private void methodFour() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
        }
    }

    public void stop() {
        b.set(false);
    }
}
