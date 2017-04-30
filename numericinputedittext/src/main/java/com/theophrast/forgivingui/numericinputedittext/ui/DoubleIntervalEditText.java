package com.theophrast.forgivingui.numericinputedittext.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;

import com.theophrast.forgivingui.numericinputedittext.interval.DoubleInterval;
import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;
import com.theophrast.forgivingui.numericinputedittext.ui.base.InputEditTextBase;

/**
 * Created by theophrast on 2017.04.30..
 */

public class DoubleIntervalEditText extends InputEditTextBase {

    DoubleInterval mInterval;

    public DoubleIntervalEditText(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public DoubleIntervalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public DoubleIntervalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DoubleIntervalEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        this.mInterval = new DoubleInterval(range);
        this.mInterval.setCorrectionValue(correction);
        this.showMessageOnError = showMessageOnError;
        this.autoCorrectOnError = autoCorrectOnError;
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    @Override
    public void setValidInterval(String validInterval) {
        this.mInterval = new DoubleInterval(validInterval);
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
            mInterval = DoubleInterval.getDefaultDoubleInterval();
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
    public void setValue(double value) {
        this.setText(Double.toString(value));
    }

    /**
     * Return the Float value of the EditText
     *
     * @return the value of EditText, null if invalid value
     */
    public Double getValue() {
        if (!isValid(true)) return null;
        return Double.valueOf(this.getText().toString());
    }

    protected String getMinErrorMessageBase() {
        return mInterval.isIntervalMinClosed() ? ErrorMessage_OutOfRange_Min_Closed : ErrorMessage_OutOfRange_Min_Open;
    }

    protected String getMaxErrorMessageBase() {
        return mInterval.isIntervalMaxClosed() ? ErrorMessage_OutOfRange_Max_Closed : ErrorMessage_OutOfRange_Max_Open;
    }
}