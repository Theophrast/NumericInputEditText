package com.theophrast.forgivingui.numericinputedittext.ui.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;


public abstract class InputEditTextBase extends EditText {

    protected static final String ErrorMessage_Empty = "Empty field";
    protected static final String ErrorMessage_FormatProblem = "Invalid format";


    protected static final String ErrorMessage_OutOfRange_Min_Closed = "Minimum value is ";
    protected static final String ErrorMessage_OutOfRange_Max_Closed = "Maximum value is ";

    protected static final String ErrorMessage_OutOfRange_Min_Open = "Minimum value must be > ";
    protected static final String ErrorMessage_OutOfRange_Max_Open = "Maximum value must be < ";

    protected boolean showMessageOnError = true;
    protected boolean autoCorrectOnError = true;


    public void setShowMessageOnError(boolean showMessageOnError) {
        this.showMessageOnError = showMessageOnError;
    }

    public void setAutoCorrectOnError(boolean autoCorrectOnError) {
        this.autoCorrectOnError = autoCorrectOnError;
    }

    protected void setBaseAttributes(Context context, AttributeSet attrs) {
        String packageName = "http://schemas.android.com/apk/res-auto";
        boolean showMessageOnError = attrs.getAttributeBooleanValue(packageName, "show_message_on_error", true);
        boolean autoCorrectOnError = attrs.getAttributeBooleanValue(packageName, "autocorrect_on_error", true);
        this.showMessageOnError = showMessageOnError;
        this.autoCorrectOnError = autoCorrectOnError;
    }

    /**
     * Set the valid range of the EditText input.
     * <p>
     * Valid interval can be specified with a String by the following format:
     * [ minimal_enabled_value , maximal_enabled_value ]
     * <p>
     * or
     * ] minimal_not_enabled_value , maximal_not_enabled_value [
     * <p>
     * or the combination of the interval limitations:
     * ] minimal_not_enabled_value , maximal_enabled_value ]
     * <p>
     * Infinite values are specified with a - or a + sign:
     * [ - , maximal_enabled_value ]
     * or
     * [ minimal_enabled_value , + ]
     *
     * @param validInterval
     */
    public abstract void setValidInterval(String validInterval);


    public InputEditTextBase(Context context) {
        super(context);
    }

    public InputEditTextBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputEditTextBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputEditTextBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Validate the value written in the EditText by the specified interval, show error message
     * if needed, and correct the written value, if needed.
     *
     * @return true if the value from Edittext is inside the specified range, false otherwise
     */
    public abstract boolean isValid();
}