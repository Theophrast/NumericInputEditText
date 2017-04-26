package com.forgivingui.theophrast.numericinputedittext.ui.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by theophrast on 2017.04.26..
 */

public abstract class InputEditTextBase extends EditText {

    protected static final String ErrorMessage_Empty = "Empty field";
    protected static final String ErrorMessage_FormatProblem = "Invalid format";


    protected static final String ErrorMessage_OutOfRange_Min_Closed = "Minimum value is ";
    protected static final String ErrorMessage_OutOfRange_Max_Closed = "Maximum value is ";

    protected static final String ErrorMessage_OutOfRange_Min_Open = "Minimum value must be > ";
    protected static final String ErrorMessage_OutOfRange_Max_Open = "Maximum value must be < ";


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

    public abstract boolean isValid();
}