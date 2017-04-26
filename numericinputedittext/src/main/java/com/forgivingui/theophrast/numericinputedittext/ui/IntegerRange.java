package com.forgivingui.theophrast.numericinputedittext.ui;


import com.forgivingui.theophrast.numericinputedittext.ui.base.RangeBase;

/**
 * Created by theophrast on 2017.04.26..
 */

public class IntegerRange extends RangeBase {
    private Integer minValue = null;
    private Integer maxValue = null;


    private IntegerRange(Integer minValue, boolean isMinClosed, Integer maxValue, boolean isMaxClosed) {
        this.minValue = minValue;
        this.isIntervalMinClosed = isMinClosed;
        this.maxValue = maxValue;
        this.isIntervalMaxClosed = isMaxClosed;
    }

    public IntegerRange(String rangeString) {
        if (rangeString == null) {
            this.minValue = null;
            this.isIntervalMinClosed = true;
            this.maxValue = null;
            this.isIntervalMaxClosed = true;
            return;
        }
        String str = rangeString.replaceAll("\\s", "");
        boolean containsChars = (str.startsWith("[") || str.startsWith("]")) &&
                (str.endsWith("[") || str.endsWith("]")) && str.contains(MINMAXSEPARATOR);
        if (!containsChars) throwInvalidFormatException(rangeString);

        int numberOfBracket = count(str, "[") + count(str, "]");
        if (numberOfBracket != 2) throwInvalidFormatException(rangeString);

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
                    throwInvalidInvalidMinMAxValueException(rangeString);
                } else if ((maxValue - minValue) == 0) {
                    if ((!isIntervalMinClosed) || (!isIntervalMaxClosed)) {
                        throwInvalidInvalidMinMAxValueException(rangeString);
                    }
                }

            }
        } catch (Exception e) {
            throwInvalidFormatException(rangeString);
        }

    }

    private void throwInvalidFormatException(String originalString) {
        String msg = "\nPlease use the following format: [minimumvalue;maximumvalue], eg.: [2,10] or [-5,42]" +
                "\nOr use [-,+] format for mo minimum or maximum value eg.: [5,+] or [-,0] or [-,+]";
        throw new InvalidRangeException("\nInvalid range set for IntegerRange: "
                + originalString + msg);
    }

    private void throwInvalidInvalidMinMAxValueException(String originalString) {
        throw new InvalidRangeException("\nInvalid range set for IntegerRange: "
                + originalString + "\n minimum value cannot be bigger than maximum value!");
    }


    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public RangeBase.RangePosition locateValueInRange(int value) {
        boolean minvalid = (minValue == null) || (value > (isIntervalMinClosed ? (minValue - 1) : minValue));
        if (!minvalid) {
            return RangePosition.OUTOFRANGE_MIN;
        }
        boolean maxvalid = (maxValue == null) || (value < (isIntervalMaxClosed ? (maxValue + 1) : maxValue));
        if (!maxvalid) {
            return RangePosition.OUTOFRANGE_MAX;
        }
        return RangePosition.INSIDE;
    }

    public static IntegerRange getDefaultIntegerRange() {
        return new IntegerRange(null, true, null, true);
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
