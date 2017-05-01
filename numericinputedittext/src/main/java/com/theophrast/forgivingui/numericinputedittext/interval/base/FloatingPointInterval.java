package com.theophrast.forgivingui.numericinputedittext.interval.base;

/**
 * Created by theophrast on 2017.05.01..
 */

public abstract class FloatingPointInterval extends IntervalBase {
    protected float correctionValue;

    public float getCorrectionValue() {
        return correctionValue;
    }

    public void setCorrectionValue(float correctionValue) {
        this.correctionValue = correctionValue;
    }
}
