package com.forgivingui.theophrast.numericinputedittextsample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.theophrast.forgivingui.numericinputedittext.ui.FloatInputEditText;
import com.theophrast.forgivingui.numericinputedittext.ui.IntegerInputEditText;


public class SampleActivity extends AppCompatActivity {

    IntegerInputEditText integerInputEditText;
    FloatInputEditText floatInputEditText;

    Button bt_intInputValidation;
    Button bt_floatInputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        integerInputEditText = (IntegerInputEditText) findViewById(R.id.et_intinput);
        floatInputEditText = (FloatInputEditText) findViewById(R.id.et_floatinput);

        bt_intInputValidation = (Button) findViewById(R.id.bt_intinputvalidation);
        bt_floatInputValidation = (Button) findViewById(R.id.bt_floatinputvalidation);

        bt_intInputValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateIntegerInputEditText(view.getContext());
            }
        });

        bt_floatInputValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFloatInputEditText(view.getContext());
            }
        });
    }


    private void validateIntegerInputEditText(Context ct) {
        boolean isValid = integerInputEditText.isValid();
        if (isValid) {
            Toast.makeText(ct, "Value in IntegerInputEditText is valid\nValue: " + integerInputEditText.getValue(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ct, "Value in IntegerInputEditText is INVALID", Toast.LENGTH_LONG).show();
        }
    }

    private void validateFloatInputEditText(Context ct) {
        boolean isValid = floatInputEditText.isValid();
        if (isValid) {
            Toast.makeText(ct, "Value in FloatInputEditText is valid\nValue: " + floatInputEditText.getValue(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ct, "Value in FloatInputEditText is INVALID", Toast.LENGTH_LONG).show();
        }
    }


}
