package com.theophrast.forgivingui.numericinputedittext.interval;

import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;

/**
 * Created by theophrast on 2017.04.30..
 */

public class DoubleInterval extends IntervalBase {

    private Double minValue;
    private Double maxValue;
    private double correctionValue;


    private DoubleInterval() {
        super();
        minValue = Double.MIN_VALUE;
        maxValue = Double.MAX_VALUE;
        correctionValue = 0.01d;
    }

    public DoubleInterval(String intervalString) {
        this();
        IntervalParser.getInstance().parseAsDoubleIntervalAndMapValuesFor(intervalString, this);
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public void setCorrectionValue(double correctionValue) {
        this.correctionValue = correctionValue;
    }

    public IntervalPosition locateValueInRange(double value) {
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

    public double getCorrectedValue(double value) {
        IntervalPosition position = locateValueInRange(value);
        double correctedValue = value;
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

    public static DoubleInterval getDefaultFloatInterval() {
        return new DoubleInterval();
    }

}