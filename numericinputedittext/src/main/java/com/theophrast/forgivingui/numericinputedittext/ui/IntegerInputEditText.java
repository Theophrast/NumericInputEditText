package com.theophrast.forgivingui.numericinputedittext.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;

import com.theophrast.forgivingui.numericinputedittext.interval.IntegerInterval;
import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;
import com.theophrast.forgivingui.numericinputedittext.ui.base.InputEditTextBase;


/**
 * Created by theophrast on 2017.04.26..
 */

public class IntegerInputEditText extends InputEditTextBase {

    IntegerInterval mInterval;

    public IntegerInputEditText(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public IntegerInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public IntegerInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IntegerInputEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        String range = attrs.getAttributeValue(packageName, "validRange");
        boolean showMessageOnError = attrs.getAttributeBooleanValue(packageName, "showMessageOnError", true);
        boolean autoCorrectOnError = attrs.getAttributeBooleanValue(packageName, "autoCorrectOnError", true);

        this.mInterval = new IntegerInterval(range);
        this.showMessageOnError = showMessageOnError;
        this.autoCorrectOnError = autoCorrectOnError;

        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    @Override
    public void setValidInterval(String validInterval) {
        this.mInterval = new IntegerInterval(validInterval);
    }

    @Override
    public boolean isValid() {
        return isValid(false);
    }

    private boolean isValid(boolean isHiddenValidation) {

        String stringResult = this.getText().toString();

        if (stringResult.isEmpty()) {
            if (!isHiddenValidation && showMessageOnError) {
                this.requestFocus();
                this.setError(ErrorMessage_Empty);
            }
            return false;
        }
        int intResult;
        try {
            intResult = Integer.valueOf(stringResult);
        } catch (Exception e) {
            if (!isHiddenValidation && showMessageOnError) {
                this.requestFocus();
                this.setError(ErrorMessage_FormatProblem);
            }
            return false;
        }


        boolean isInRange = isValueInRange(intResult, isHiddenValidation);
        if (!isHiddenValidation && isInRange && showMessageOnError) {
            setError(null);
        }
        return isInRange;
    }


    private boolean isValueInRange(Integer value, boolean isHiddenValidation) {
        if (mInterval == null) {
            mInterval = IntegerInterval.getDefaultIntegerInterval();
        }
        if (!isHiddenValidation && autoCorrectOnError) setValue(mInterval.getCorrectedValue(value));

        IntervalBase.IntervalPosition posInRange = mInterval.locateValueInRange(value);
        switch (posInRange) {
            case OUTOFRANGE_MAX:
                if (!isHiddenValidation && showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMaxErrorMessageBase() + mInterval.getMaxValue());
                }
                return false;
            case OUTOFRANGE_MIN:
                if (!isHiddenValidation && showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMinErrorMessageBase() + mInterval.getMinValue());
                }
                return false;
            case INSIDE:
                return true;
        }
        return false;
    }

    /**
     * Set the value of the editText.
     *
     * @param value
     */
    public void setValue(int value) {
        this.setText(Integer.toString(value));
    }

    /**
     * Return the Integer value of the EditText
     *
     * @return the value of EditText, null if invalid value
     */
    public Integer getValue() {
        if (!isValid()) return null;
        return Integer.valueOf(this.getText().toString());
    }

    protected String getMinErrorMessageBase() {
        return mInterval.isIntervalMinClosed() ? ErrorMessage_OutOfRange_Min_Closed : ErrorMessage_OutOfRange_Min_Open;
    }

    protected String getMaxErrorMessageBase() {
        return mInterval.isIntervalMaxClosed() ? ErrorMessage_OutOfRange_Max_Closed : ErrorMessage_OutOfRange_Max_Open;
    }
}
