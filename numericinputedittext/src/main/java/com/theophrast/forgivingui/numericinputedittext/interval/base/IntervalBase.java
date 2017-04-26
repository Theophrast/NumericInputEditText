package com.theophrast.forgivingui.numericinputedittext.interval.base;

/**
 * Created by theophrast on 2017.04.26..
 */

public abstract class IntervalBase {
    protected static final String MINMAXSEPARATOR = ",";
    protected boolean isIntervalMinClosed = true;
    protected boolean isIntervalMaxClosed = true;


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

    protected class InvalidIntervalException extends RuntimeException{
        public InvalidIntervalException(String message) {
            super(message);
        }
    }

}
