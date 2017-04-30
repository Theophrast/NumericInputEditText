package com.theophrast.forgivingui.numericinputedittext.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;

import com.theophrast.forgivingui.numericinputedittext.interval.FloatInterval;
import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;
import com.theophrast.forgivingui.numericinputedittext.ui.base.InputEditTextBase;

/**
 * Created by theophrast on 2017.04.26..
 */

public class FloatInputEditText extends InputEditTextBase {

    FloatInterval mInterval;

    public FloatInputEditText(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public FloatInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public FloatInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FloatInputEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        String range = attrs.getAttributeValue(packageName, "validRange");
        float correction = attrs.getAttributeFloatValue(packageName, "correction", 0.01f);
        boolean showMessageOnError = attrs.getAttributeBooleanValue(packageName, "showMessageOnError", true);
        boolean autoCorrectOnError = attrs.getAttributeBooleanValue(packageName, "autoCorrectOnError", true);

        this.mInterval = new FloatInterval(range);
        this.mInterval.setCorrectionValue(correction);
        this.showMessageOnError = showMessageOnError;
        this.autoCorrectOnError = autoCorrectOnError;
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    @Override
    public void setValidInterval(String validInterval) {
        this.mInterval = new FloatInterval(validInterval);
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
        float floatResult;
        try {
            floatResult = Float.valueOf(stringResult);
        } catch (Exception e) {
            if (!isHiddenValidation && showMessageOnError) {
                this.requestFocus();
                this.setError(ErrorMessage_FormatProblem);
            }
            return false;
        }


        boolean isInRange = isValueInRange(floatResult, isHiddenValidation);
        if (!isHiddenValidation && isInRange && showMessageOnError) {
            setError(null);
        }
        return isInRange;
    }


    private boolean isValueInRange(float value, boolean isHiddenValidation) {
        if (mInterval == null) {
            mInterval = FloatInterval.getDefaultFloatInterval();
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
    public void setValue(float value) {
        this.setText(Float.toString(value));
    }

    /**
     * Return the Integer value of the EditText
     *
     * @return the value of EditText, null if invalid value
     */
    public Float getValue() {
        if (!isValid(true)) return null;
        return Float.valueOf(this.getText().toString());
    }

    protected String getMinErrorMessageBase() {
        return mInterval.isIntervalMinClosed() ? ErrorMessage_OutOfRange_Min_Closed : ErrorMessage_OutOfRange_Min_Open;
    }

    protected String getMaxErrorMessageBase() {
        return mInterval.isIntervalMaxClosed() ? ErrorMessage_OutOfRange_Max_Closed : ErrorMessage_OutOfRange_Max_Open;
    }
}