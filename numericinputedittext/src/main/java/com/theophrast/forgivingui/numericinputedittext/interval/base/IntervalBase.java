package com.theophrast.forgivingui.numericinputedittext.interval.base;

/**
 * Created by theophrast on 2017.04.26..
 */

public abstract class IntervalBase {

    protected boolean isIntervalMinClosed;
    protected boolean isIntervalMaxClosed;


    public IntervalBase() {
        isIntervalMinClosed = true;
        isIntervalMaxClosed = true;
    }


    public boolean isIntervalMinClosed() {
        return isIntervalMinClosed;
    }

    public void setIntervalMinClosed(boolean intervalMinClosed) {
        isIntervalMinClosed = intervalMinClosed;
    }

    public boolean isIntervalMaxClosed() {
        return isIntervalMaxClosed;
    }

    public void setIntervalMaxClosed(boolean intervalMaxClosed) {
        isIntervalMaxClosed = intervalMaxClosed;
    }

    public enum IntervalPosition {
        INSIDE, OUTOFRANGE_MIN, OUTOFRANGE_MAX
    }



}
