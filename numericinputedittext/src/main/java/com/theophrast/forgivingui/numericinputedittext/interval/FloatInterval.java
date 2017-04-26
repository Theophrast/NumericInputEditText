package com.theophrast.forgivingui.numericinputedittext.interval;

import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;

/**
 * Created by theophrast on 2017.04.26..
 */

public class FloatInterval extends IntervalBase {
    private Float minValue = null;
    private Float maxValue = null;
    private float correctionValue;



    private FloatInterval(Float minValue, boolean isMinClosed, Float maxValue, boolean isMaxClosed, float correctionValue) {
        this.minValue = minValue;
        this.isIntervalMinClosed = isMinClosed;
        this.maxValue = maxValue;
        this.isIntervalMaxClosed = isMaxClosed;
        this.correctionValue = correctionValue;
    }

    public FloatInterval(String intervalString) {
        if (intervalString == null) {
            this.minValue = null;
            this.isIntervalMinClosed = true;
            this.maxValue = null;
            this.isIntervalMaxClosed = true;
            return;
        }
        String str = intervalString.replaceAll("\\s", "");
        boolean containsChars = (str.startsWith("[") || str.startsWith("]")) &&
                (str.endsWith("[") || str.endsWith("]")) && str.contains(MINMAXSEPARATOR);
        if (!containsChars) throwInvalidFormatException(intervalString);

        int numberOfBracket = count(str, "[") + count(str, "]");
        if (numberOfBracket != 2) throwInvalidFormatException(intervalString);

        try {
            String[] rangeValues = str.split(MINMAXSEPARATOR);
            isIntervalMinClosed = rangeValues[0].startsWith("[");
            isIntervalMaxClosed = rangeValues[1].endsWith("]");
            if (rangeValues[0].substring(1).equals("-")) {
                minValue = null;
            } else {
                minValue = Float.valueOf(rangeValues[0].substring(1));
            }

            if (rangeValues[1].substring(0, rangeValues[1].length() - 1).equals("+")) {
                maxValue = null;
            } else {
                maxValue = Float.valueOf(rangeValues[1].substring(0, rangeValues[1].length() - 1));
            }
            if ((minValue != null) && (maxValue != null)) {
                if ((maxValue - minValue) < 0) {
                    throwInvalidInvalidMinMAxValueException(intervalString);
                } else if ((maxValue - minValue) == 0) {
                    if ((!isIntervalMinClosed) || (!isIntervalMaxClosed)) {
                        throwInvalidInvalidMinMAxValueException(intervalString);
                    }
                }

            }
        } catch (Exception e) {
            throwInvalidFormatException(intervalString);
        }

    }

    public void setCorrectionValue(float correctionValue) {
        this.correctionValue = correctionValue;
    }

    private void throwInvalidFormatException(String originalString) {
        String msg = "\nPlease use the following format: [minimumvalue;maximumvalue], eg.: [2,10] or [-5,42]" +
                "\nOr use [-,+] format for mo minimum or maximum value eg.: [5,+] or [-,0] or [-,+]";
        throw new InvalidIntervalException("\nInvalid range set for FloatInterval: "
                + originalString + msg);
    }

    private void throwInvalidInvalidMinMAxValueException(String originalString) {
        throw new InvalidIntervalException("\nInvalid range set for FloatInterval: "
                + originalString + "\n minimum value cannot be bigger than maximum value!");
    }


    public Float getMinValue() {
        return minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public IntervalPosition locateValueInRange(float value) {
        boolean minvalid = (minValue == null) || (value > (isIntervalMinClosed ? (minValue - 1) : minValue));
        if (!minvalid) {
            return IntervalPosition.OUTOFRANGE_MIN;
        }
        boolean maxvalid = (maxValue == null) || (value < (isIntervalMaxClosed ? (maxValue + 1) : maxValue));
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
                correctedValue= maxValue - (isIntervalMaxClosed ? 0f : correctionValue);
                break;
            case OUTOFRANGE_MIN:
                correctedValue= minValue + (isIntervalMinClosed ? 0f : correctionValue);
                break;
        }
        IntervalPosition positionNew = locateValueInRange(correctedValue);

        if(positionNew.equals(IntervalPosition.INSIDE)) {
            return correctedValue;
        }else {
            return (minValue+maxValue)/2f; //correct to a valid value
        }
    }

    public static FloatInterval getDefaultFloatInterval() {
        return new FloatInterval(null, true, null, true, 0.01f);
    }

    public static int count(final String string, final String substring) {
        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != -1) {
            idx++;
            count++;
        }

        return count;
    }
}