package com.forgivingui.theophrast.numericinputedittext.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.forgivingui.theophrast.numericinputedittext.ui.base.InputEditTextBase;
import com.forgivingui.theophrast.numericinputedittext.ui.base.RangeBase;

/**
 * Created by theophrast on 2017.04.26..
 */

public class IntInputEditText extends InputEditTextBase {

    IntegerRange mRange;

    public void setShowMessageOnError(boolean showMessageOnError) {
        this.showMessageOnError = showMessageOnError;
    }

    public void setAutoCorrectOnError(boolean autoCorrectOnError) {
        this.autoCorrectOnError = autoCorrectOnError;
    }

    private boolean showMessageOnError=true;
    private boolean autoCorrectOnError=true;

    public IntInputEditText(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public IntInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public IntInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IntInputEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        String range = attrs.getAttributeValue(packageName, "validRange");
        boolean showMessageOnError = attrs.getAttributeBooleanValue(packageName, "showMessageOnError", true);
        boolean autoCorrectOnError = attrs.getAttributeBooleanValue(packageName, "autoCorrectOnError", true);

        this.mRange=new IntegerRange(range);
        this.showMessageOnError=showMessageOnError;
        this.autoCorrectOnError=autoCorrectOnError;
    }

    public void setRange(String range) {
        this.mRange = new IntegerRange(range);
    }

    private void setRange(IntegerRange range) {
        this.mRange = range;
    }


    @Override
    public boolean isValid() {
        String stringResult = this.getText().toString();

        if (stringResult.isEmpty()) {
            if (showMessageOnError) {
                this.requestFocus();
                this.setError(ErrorMessage_Empty);
            }
            return false;
        }
        int intResult;
        try {
            intResult = Integer.valueOf(stringResult);
        } catch (Exception e) {
            if (showMessageOnError) {
                this.requestFocus();
                this.setError(ErrorMessage_FormatProblem);
            }
            return false;
        }


        boolean isInRange = isValueInRange(intResult);
        if (isInRange && showMessageOnError) {
            setError(null);
        }
        return isInRange;
    }


    private boolean isValueInRange(Integer value) {
        if (mRange == null) {
            mRange = IntegerRange.getDefaultIntegerRange();
        }
        RangeBase.RangePosition posInRange = mRange.locateValueInRange(value);

        switch (posInRange) {
            case OUTOFRANGE_MAX:
                if (showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMaxErrorMessageBase() + mRange.getMaxValue());
                }
                if (autoCorrectOnError) overWritetoMaxLimit();
                return false;
            case OUTOFRANGE_MIN:
                if (showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMinErrorMessageBase() + mRange.getMinValue());
                }
                if (autoCorrectOnError) overWriteToMinLimit();
                return false;
            case INSIDE:
                return true;
        }
        return false;
    }

    private void overWriteToMinLimit() {
        if (mRange.getMinValue() == null) return;
        Integer newValue = mRange.getMinValue() + (mRange.isIntervalMinClosed() ? 0 : 1);
        this.setText(Integer.toString(newValue));

    }

    private void overWritetoMaxLimit() {
        if (mRange.getMaxValue() == null) return;
        Integer newValue = mRange.getMaxValue() - (mRange.isIntervalMaxClosed() ? 0 : 1);
        this.setText(Integer.toString(newValue));
    }

    public Integer getValue() {
        if (!isValid()) return null;
        return Integer.valueOf(this.getText().toString());
    }

    protected String getMinErrorMessageBase() {
        return mRange.isIntervalMinClosed() ? ErrorMessage_OutOfRange_Min_Closed : ErrorMessage_OutOfRange_Min_Open;
    }

    protected String getMaxErrorMessageBase() {
        return mRange.isIntervalMaxClosed() ? ErrorMessage_OutOfRange_Max_Closed : ErrorMessage_OutOfRange_Max_Open;
    }
}
