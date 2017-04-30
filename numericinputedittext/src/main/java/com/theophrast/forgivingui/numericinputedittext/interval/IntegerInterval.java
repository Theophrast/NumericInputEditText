package com.theophrast.forgivingui.numericinputedittext.interval;


import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;

public class IntegerInterval extends IntervalBase {

    private Integer minValue;
    private Integer maxValue;

    private IntegerInterval() {
        super();
        minValue = Integer.MAX_VALUE;
        maxValue = Integer.MIN_VALUE;
    }

    public IntegerInterval(String intervalString) {
        this();
        IntervalParser.getInstance().parseAsIntegerIntervalAndMapValuesFor(intervalString, this);
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public IntervalPosition locateValueInRange(int value) {
        boolean minvalid = (minValue == null) || (isIntervalMinClosed ? (value>=minValue ) : (value>minValue));
        if (!minvalid) {
            return IntervalPosition.OUTOFRANGE_MIN;
        }
        boolean maxvalid = (maxValue == null) || (isIntervalMaxClosed ? (value<=maxValue) : (value<maxValue));
        if (!maxvalid) {
            return IntervalPosition.OUTOFRANGE_MAX;
        }
        return IntervalPosition.INSIDE;
    }

    public int getCorrectedValue(int value) {
        IntervalPosition position = locateValueInRange(value);
        switch (position) {
            case INSIDE:
                return value;
            case OUTOFRANGE_MAX:
                return maxValue - (isIntervalMaxClosed ? 0 : 1);
            case OUTOFRANGE_MIN:
                return minValue + (isIntervalMinClosed ? 0 : 1);
        }
        return value;
    }

    public static IntegerInterval getDefaultIntegerInterval() {
        return new IntegerInterval();
    }

}
