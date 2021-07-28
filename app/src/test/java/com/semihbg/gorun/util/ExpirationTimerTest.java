package com.semihbg.gorun.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExpirationTimer")
class ExpirationTimerTest {

    @Test
    @DisplayName("Get Result Before Expiration")
    void getResultBeforeExpiration() throws InterruptedException {
        ExpirationTimer expirationTimer=new ExpirationTimer(100L);
        expirationTimer.reflesh();
        Thread.sleep(50L);
        assertTrue(expirationTimer.result());
        expirationTimer.stop();
    }

    @Test
    @DisplayName("Get Result After Expiration")
    void getResultAfterExpiration() throws InterruptedException {
        ExpirationTimer expirationTimer=new ExpirationTimer(50L);
        expirationTimer.reflesh();
        Thread.sleep(100L);
        assertFalse(expirationTimer.result());
        expirationTimer.stop();
    }

    @Test
    @DisplayName("Get Result After Stop")
    void getResultAfterStop() throws InterruptedException {
        ExpirationTimer expirationTimer=new ExpirationTimer(100L);
        expirationTimer.reflesh();
        Thread.sleep(50L);
        expirationTimer.stop();
        assertThrows(IllegalStateException.class, expirationTimer::result);
    }

}