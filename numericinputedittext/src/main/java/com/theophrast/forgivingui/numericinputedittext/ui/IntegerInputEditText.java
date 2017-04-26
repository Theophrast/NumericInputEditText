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

    IntegerInterval mRange;

    public void setShowMessageOnError(boolean showMessageOnError) {
        this.showMessageOnError = showMessageOnError;
    }

    public void setAutoCorrectOnError(boolean autoCorrectOnError) {
        this.autoCorrectOnError = autoCorrectOnError;
    }

    private boolean showMessageOnError=true;
    private boolean autoCorrectOnError=true;


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

        this.mRange=new IntegerInterval(range);
        this.showMessageOnError=showMessageOnError;
        this.autoCorrectOnError=autoCorrectOnError;

        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    @Override
    public void setValidInterval(String validInterval) {
        this.mRange = new IntegerInterval(validInterval);
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
            mRange = IntegerInterval.getDefaultIntegerInterval();
        }
        if (autoCorrectOnError) setValue(mRange.getCorrectedValue(value));

        IntervalBase.IntervalPosition posInRange = mRange.locateValueInRange(value);
        switch (posInRange) {
            case OUTOFRANGE_MAX:
                if (showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMaxErrorMessageBase() + mRange.getMaxValue());
                }
                return false;
            case OUTOFRANGE_MIN:
                if (showMessageOnError) {
                    this.requestFocus();
                    this.setError(getMinErrorMessageBase() + mRange.getMinValue());
                }
                return false;
            case INSIDE:
                return true;
        }
        return false;
    }

    /**
     * Set the value of the editText.
     * @param value
     */
    public void setValue(int value){
        this.setText(Integer.toString(value));
    }

    /**
     * Return the Integer value of the EditText
     * @return the value of EditText, null if invalid value
     */
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
