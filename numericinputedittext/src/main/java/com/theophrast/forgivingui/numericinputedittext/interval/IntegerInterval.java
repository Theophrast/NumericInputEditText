package com.theophrast.forgivingui.numericinputedittext.interval;


import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;

public class IntegerInterval extends IntervalBase {
    private Integer minValue = null;
    private Integer maxValue = null;


    private IntegerInterval(Integer minValue, boolean isMinClosed, Integer maxValue, boolean isMaxClosed) {
        this.minValue = minValue;
        this.isIntervalMinClosed = isMinClosed;
        this.maxValue = maxValue;
        this.isIntervalMaxClosed = isMaxClosed;
    }

    public IntegerInterval(String intervalString) {
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
                minValue = Integer.valueOf(rangeValues[0].substring(1));
            }

            if (rangeValues[1].substring(0, rangeValues[1].length() - 1).equals("+")) {
                maxValue = null;
            } else {
                maxValue = Integer.valueOf(rangeValues[1].substring(0, rangeValues[1].length() - 1));
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

    private void throwInvalidFormatException(String originalString) {
        String msg = "\nPlease use the following format: [minimumvalue;maximumvalue], eg.: [2,10] or [-5,42]" +
                "\nOr use [-,+] format for mo minimum or maximum value eg.: [5,+] or [-,0] or [-,+]";
        throw new InvalidIntervalException("\nInvalid range set for IntegerInterval: "
                + originalString + msg);
    }

    private void throwInvalidInvalidMinMAxValueException(String originalString) {
        throw new InvalidIntervalException("\nInvalid range set for IntegerInterval: "
                + originalString + "\n minimum value cannot be bigger than maximum value!");
    }


    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public IntervalPosition locateValueInRange(int value) {
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

    public static IntegerInterval getDefaultIntegerRange() {
        return new IntegerInterval(null, true, null, true);
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
