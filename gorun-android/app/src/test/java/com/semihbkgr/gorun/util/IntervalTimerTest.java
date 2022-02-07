package com.semihbkgr.gorun.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IntervalTimer")
class IntervalTimerTest {

    @Test
    @DisplayName("Get Result Before Expiration")
    void getResultBeforeExpiration() throws InterruptedException {
        IntervalTimer intervalTimer = new IntervalTimer(100L);
        intervalTimer.reflesh();
        Thread.sleep(50L);
        assertTrue(intervalTimer.result());
        intervalTimer.stop();
    }

    @Test
    @DisplayName("Get Result After Expiration")
    void getResultAfterExpiration() throws InterruptedException {
        IntervalTimer intervalTimer = new IntervalTimer(50L);
        intervalTimer.reflesh();
        Thread.sleep(100L);
        assertFalse(intervalTimer.result());
        intervalTimer.stop();
    }

    @Test
    @DisplayName("Get Result After Stop")
    void getResultAfterStop() throws InterruptedException {
        IntervalTimer intervalTimer = new IntervalTimer(100L);
        intervalTimer.reflesh();
        Thread.sleep(50L);
        intervalTimer.stop();
        assertThrows(IllegalStateException.class, intervalTimer::result);
    }

}