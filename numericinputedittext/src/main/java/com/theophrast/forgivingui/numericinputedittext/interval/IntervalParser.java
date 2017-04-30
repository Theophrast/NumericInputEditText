package com.theophrast.forgivingui.numericinputedittext.interval;

import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalParserBase;

/**
 * Created by theophrast on 2017.04.30..
 */

public class IntervalParser extends IntervalParserBase {


    private static IntervalParser ourInstance = new IntervalParser();

    static IntervalParser getInstance() {
        return ourInstance;
    }

    void parseAsIntegerIntervalAndMapValuesFor(String rawIntervalString, IntegerInterval interval) {
        String intervalString = validateBasics(rawIntervalString);

        if (intervalString == null) {
            return;
        }
        boolean isIntervalMinClosed = true;
        boolean isIntervalMaxClosed = true;
        Integer minValue = Integer.MIN_VALUE;
        Integer maxValue = Integer.MAX_VALUE;
        try {
            isIntervalMinClosed = isIntervalClosedFor(ValueType.MINVALUE, intervalString);
            isIntervalMaxClosed = isIntervalClosedFor(ValueType.MAXVALUE, intervalString);
            String minString = getValueFromPreProcessedString(ValueType.MINVALUE, intervalString);
            String maxString = getValueFromPreProcessedString(ValueType.MAXVALUE, intervalString);

            minValue = minString.equals("-") ? Integer.MIN_VALUE : Integer.valueOf(minString);
            maxValue = maxString.equals("+") ? Integer.MAX_VALUE : Integer.valueOf(maxString);

            if ((maxValue - minValue) < 0) {
                throwInvalidInvalidMinMAxValueException(rawIntervalString);
            } else if ((maxValue - minValue) == 0) {
                if ((!isIntervalMinClosed) || (!isIntervalMaxClosed)) {
                    throwInvalidInvalidMinMAxValueException(rawIntervalString);
                }
            }
        } catch (Exception e) {
            throwInvalidFormatException(rawIntervalString);
        }
        interval.setMinValue(minValue);
        interval.setIntervalMinClosed(isIntervalMinClosed);
        interval.setMaxValue(maxValue);
        interval.setIntervalMaxClosed(isIntervalMaxClosed);
    }

    void parseAsLongIntervalAndMapValuesFor(String rawIntervalString, LongInterval interval) {
        String intervalString = validateBasics(rawIntervalString);

        if (intervalString == null) {
            return;
        }
        boolean isIntervalMinClosed = true;
        boolean isIntervalMaxClosed = true;
        Long minValue = Long.MIN_VALUE;
        Long maxValue = Long.MAX_VALUE;
        try {
            isIntervalMinClosed = isIntervalClosedFor(ValueType.MINVALUE, intervalString);
            isIntervalMaxClosed = isIntervalClosedFor(ValueType.MAXVALUE, intervalString);
            String minString = getValueFromPreProcessedString(ValueType.MINVALUE, intervalString);
            String maxString = getValueFromPreProcessedString(ValueType.MAXVALUE, intervalString);

            minValue = minString.equals("-") ? Long.MIN_VALUE : Long.valueOf(minString);
            maxValue = maxString.equals("+") ? Long.MAX_VALUE : Long.valueOf(maxString);

            if ((maxValue - minValue) < 0) {
                throwInvalidInvalidMinMAxValueException(rawIntervalString);
            } else if ((maxValue - minValue) == 0) {
                if ((!isIntervalMinClosed) || (!isIntervalMaxClosed)) {
                    throwInvalidInvalidMinMAxValueException(rawIntervalString);
                }
            }
        } catch (Exception e) {
            throwInvalidFormatException(rawIntervalString);
        }

        interval.setMinValue(minValue);
        interval.setIntervalMinClosed(isIntervalMinClosed);
        interval.setMaxValue(maxValue);
        interval.setIntervalMaxClosed(isIntervalMaxClosed);
    }

    void parseAsFloatIntervalAndMapValuesFor(String rawIntervalString, FloatInterval interval) {
        String intervalString = validateBasics(rawIntervalString);

        if (intervalString == null) {
            return;
        }
        boolean isIntervalMinClosed = true;
        boolean isIntervalMaxClosed = true;
        Float minValue = Float.MIN_VALUE;
        Float maxValue = Float.MAX_VALUE;
        try {
            isIntervalMinClosed = isIntervalClosedFor(ValueType.MINVALUE, intervalString);
            isIntervalMaxClosed = isIntervalClosedFor(ValueType.MAXVALUE, intervalString);
            String minString = getValueFromPreProcessedString(ValueType.MINVALUE, intervalString);
            String maxString = getValueFromPreProcessedString(ValueType.MAXVALUE, intervalString);

            minValue = minString.equals("-") ? Float.MIN_VALUE : Float.valueOf(minString);
            maxValue = maxString.equals("+") ? Float.MAX_VALUE : Float.valueOf(maxString);

            if ((maxValue - minValue) < 0) {
                throwInvalidInvalidMinMAxValueException(rawIntervalString);
            } else if ((maxValue - minValue) == 0) {
                if ((!isIntervalMinClosed) || (!isIntervalMaxClosed)) {
                    throwInvalidInvalidMinMAxValueException(rawIntervalString);
                }
            }
        } catch (Exception e) {
            throwInvalidFormatException(rawIntervalString);
        }

        interval.setMinValue(minValue);
        interval.setIntervalMinClosed(isIntervalMinClosed);
        interval.setMaxValue(maxValue);
        interval.setIntervalMaxClosed(isIntervalMaxClosed);
    }

}
