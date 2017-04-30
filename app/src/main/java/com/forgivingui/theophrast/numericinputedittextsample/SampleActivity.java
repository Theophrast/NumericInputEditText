package com.forgivingui.theophrast.numericinputedittextsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.theophrast.forgivingui.numericinputedittext.ui.FloatInputEditText;
import com.theophrast.forgivingui.numericinputedittext.ui.IntegerInputEditText;


public class SampleActivity extends AppCompatActivity {

    IntegerInputEditText integerInputEditText;
    FloatInputEditText floatInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        integerInputEditText = (IntegerInputEditText) findViewById(R.id.et_intinput);
        floatInputEditText = (FloatInputEditText) findViewById(R.id.et_floatinput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void validateIntegerInputEditText(View v) {
        boolean isValid = integerInputEditText.isValid();
        if(isValid){
            Toast.makeText(v.getContext(),"Value in IntegerInputEditText is valid\nValue: "+integerInputEditText.getValue(),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(v.getContext(),"Value in IntegerInputEditText is INVALID",Toast.LENGTH_LONG).show();
        }


    }
 public void validateFloatInputEditText(View v) {
        boolean isValid = floatInputEditText.isValid();
        if(isValid){
            Toast.makeText(v.getContext(),"Value in FloatInputEditText is valid\nValue: "+floatInputEditText.getValue(),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(v.getContext(),"Value in FloatInputEditText is INVALID",Toast.LENGTH_LONG).show();
        }


    }


}
