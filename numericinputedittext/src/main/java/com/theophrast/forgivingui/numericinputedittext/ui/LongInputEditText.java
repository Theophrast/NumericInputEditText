package com.theophrast.forgivingui.numericinputedittext.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;

import com.theophrast.forgivingui.numericinputedittext.interval.LongInterval;
import com.theophrast.forgivingui.numericinputedittext.interval.base.IntervalBase;
import com.theophrast.forgivingui.numericinputedittext.ui.base.InputEditTextBase;

/**
 * Created by theophrast on 2017.04.30..
 */

public class LongInputEditText extends InputEditTextBase {

    LongInterval mInterval;

    public LongInputEditText(Context context) {
        super(context);
        setAttributes(context, null);
    }

    public LongInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public LongInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LongInputEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        String range = attrs.getAttributeValue(packageName, "valid_range");

        this.mInterval = (range==null)?LongInterval.getDefaultLongInterval():new LongInterval(range);

        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    @Override
    public void setValidInterval(String validInterval) {
        this.mInterval = new LongInterval(validInterval);
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
        long intResult;
        try {
            intResult = Long.valueOf(stringResult);
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


    private boolean isValueInRange(Long value, boolean isHiddenValidation) {
        if (mInterval == null) {
            mInterval = LongInterval.getDefaultLongInterval();
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
    public void setValue(long value) {
        this.setText(Long.toString(value));
    }

    /**
     * Return the Long value of the EditText
     *
     * @return the value of EditText, null if invalid value
     */
    public Long getValue() {
        if (!isValid()) return null;
        return Long.valueOf(this.getText().toString());
    }

    protected String getMinErrorMessageBase() {
        return mInterval.isIntervalMinClosed() ? ErrorMessage_OutOfRange_Min_Closed : ErrorMessage_OutOfRange_Min_Open;
    }

    protected String getMaxErrorMessageBase() {
        return mInterval.isIntervalMaxClosed() ? ErrorMessage_OutOfRange_Max_Closed : ErrorMessage_OutOfRange_Max_Open;
    }
}