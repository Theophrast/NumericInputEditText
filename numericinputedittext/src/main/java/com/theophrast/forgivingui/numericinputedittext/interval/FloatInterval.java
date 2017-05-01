package com.theophrast.forgivingui.numericinputedittext.interval;

import com.theophrast.forgivingui.numericinputedittext.interval.base.FloatingPointInterval;

/**
 * Created by theophrast on 2017.04.26..
 */

public class FloatInterval extends FloatingPointInterval {

    private Float minValue;
    private Float maxValue;

    private FloatInterval() {
        super();
        minValue = Float.MIN_VALUE;
        maxValue = Float.MAX_VALUE;
        correctionValue = 0.01f;
    }

    public FloatInterval(String intervalString) {
        this();
        IntervalParser.getInstance().parseAsFloatIntervalAndMapValuesFor(intervalString, this);
    }

    public Float getMinValue() {
        return minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public IntervalPosition locateValueInRange(float value) {
        boolean minvalid = (minValue == null) || (isIntervalMinClosed ? (value >= minValue) : (value > minValue));
        if (!minvalid) {
            return IntervalPosition.OUTOFRANGE_MIN;
        }
        boolean maxvalid = (maxValue == null) || (isIntervalMaxClosed ? (value <= maxValue) : (value < maxValue));
        if (!maxvalid) {
            return IntervalPosition.OUTOFRANGE_MAX;
        }
        return IntervalPosition.INSIDE;
    }

    public float getCorrectedValue(float value) {
        IntervalPosition position = locateValueInRange(value);
        float correctedValue = value;
        switch (position) {
            case INSIDE:
                return correctedValue;
            case OUTOFRANGE_MAX:
                correctedValue = maxValue - (isIntervalMaxClosed ? 0f : correctionValue);
                break;
            case OUTOFRANGE_MIN:
                correctedValue = minValue + (isIntervalMinClosed ? 0f : correctionValue);
                break;
        }
        IntervalPosition positionNew = locateValueInRange(correctedValue);

        if (positionNew.equals(IntervalPosition.INSIDE)) {
            return correctedValue;
        } else {
            return (minValue + maxValue) / 2f; //correct to a valid value
        }
    }

    public static FloatInterval getDefaultFloatInterval() {
        return new FloatInterval();
    }

}