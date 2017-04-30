package com.theophrast.forgivingui.numericinputedittext.interval;

import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;

/**
 * Created by theophrast on 2017.04.30..
 */

public class LongInterval extends IntervalBase {

    private Long minValue;
    private Long maxValue;


    private LongInterval() {
        super();
        this.minValue = Long.MIN_VALUE;
        this.maxValue = Long.MAX_VALUE;
    }

    public LongInterval(String intervalString) {
        this();
        IntervalParser.getInstance().parseAsLongIntervalAndMapValuesFor(intervalString, this);
    }


    public Long getMinValue() {
        return minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public IntervalPosition locateValueInRange(long value) {
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

    public long getCorrectedValue(long value) {
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

    public static LongInterval getDefaultLongInterval() {
        return new LongInterval();
    }
}