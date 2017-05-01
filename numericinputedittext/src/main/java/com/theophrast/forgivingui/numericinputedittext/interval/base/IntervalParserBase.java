package com.theophrast.forgivingui.numericinputedittext.interval.base;

/**
 * Created by theophrast on 2017.04.30..
 */

public abstract class IntervalParserBase {
    private static final String MINMAXSEPARATOR = ",";

    protected String validateBasics(String rawIntervalString) {
        String intervalString = rawIntervalString.replaceAll("\\s", "");

        boolean containsChars = (intervalString.startsWith("[") || intervalString.startsWith("]")) &&
                (intervalString.endsWith("[") || intervalString.endsWith("]")) && intervalString.contains(MINMAXSEPARATOR);
        if (!containsChars) throwInvalidFormatException(rawIntervalString);

        int numberOfBracket = count(intervalString, "[") + count(intervalString, "]");
        if (numberOfBracket != 2) throwInvalidFormatException(rawIntervalString);

        int numberOfComma = count(intervalString, ",");
        if (numberOfComma != 1) throwInvalidFormatException(rawIntervalString);

        return rawIntervalString;
    }


    protected String getValueFromPreProcessedString(ValueType type, String preProcessedString) {
        String[] rangeValues = preProcessedString.split(MINMAXSEPARATOR);
        if (ValueType.MINVALUE.equals(type)) {
            String minStr = rangeValues[0].replaceAll("]", "");
            minStr = minStr.replaceAll("\\[", "");
            return minStr;
        } else {
            String maxStr = rangeValues[1].replaceAll("]", "");
            maxStr = maxStr.replaceAll("\\[", "");
            return maxStr;
        }

    }

    protected boolean isIntervalClosedFor(ValueType type, String preProcessedString) {
        String[] rangeValues = preProcessedString.split(MINMAXSEPARATOR);
        if (ValueType.MINVALUE.equals(type)) {
            return rangeValues[0].startsWith("[");
        } else {
            return rangeValues[1].endsWith("]");
        }
    }

    protected void throwInvalidFormatException(String originalString) {
        String msg = "\nPlease use the following format: [minimumvalue;maximumvalue], eg.: [2,10] or [-5,42]" +
                "\nOr use [-,+] format for mo minimum or maximum value eg.: [5,+] or [-,0] or [-,+]";
        throw new InvalidIntervalException("\nInvalid range set: "
                + originalString + msg);
    }

    protected void throwInvalidInvalidMinMAxValueException(String originalString) {
        throw new InvalidIntervalException("\nInvalid range set : "
                + originalString + "\n minimum value cannot be bigger than maximum value!");
    }

    protected static int count(final String string, final String substring) {
        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != -1) {
            idx++;
            count++;
        }

        return count;
    }

    protected class InvalidIntervalException extends RuntimeException {
        InvalidIntervalException(String message) {
            super(message);
        }
    }

    protected enum ValueType {
        MINVALUE, MAXVALUE
    }
}
