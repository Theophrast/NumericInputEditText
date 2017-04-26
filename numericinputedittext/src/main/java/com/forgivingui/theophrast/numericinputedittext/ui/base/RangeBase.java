package com.forgivingui.theophrast.numericinputedittext.ui.base;

/**
 * Created by theophrast on 2017.04.26..
 */

public abstract class RangeBase {
    protected static String MINMAXSEPARATOR = ",";
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

    public enum RangePosition {
        INSIDE, OUTOFRANGE_MIN, OUTOFRANGE_MAX
    }

    protected class InvalidRangeException extends RuntimeException{
        public InvalidRangeException(String message) {
            super(message);
        }
    }

}
