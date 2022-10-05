package com.paltech.constant;

/**
 * @author isabella.huynh
 * created on Nov/9/2019.
 */
public class StopWatch {
    long startTime = 0;
    long elapsed = 0;

    public StopWatch(){
        startTime = 0;
        elapsed = 0;
    }

    public void start(){
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
    }

    public long getElapsedTime(){
        if (startTime == 0) {
            return 0;
        }
        return (System.currentTimeMillis() - startTime);
    }

    public void stop(){
        if (startTime == 0) {
            return;
        }
        elapsed = (System.currentTimeMillis() - startTime);
    }

}
