package com.appsingularity.postmansample.manual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.appsingularity.postmansample.R;
import com.appsingularity.postmansample.util.DrawableHelper;


public class ReceivingManualActivity extends AppCompatActivity {
    private final static String TAG = "ReceivingManual";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiving_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.manual_result_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView foundParcelableView = (TextView) findViewById(R.id.found_parcelable);
        TextView foundAttribute1View = (TextView) findViewById(R.id.found_attribute_1);
        TextView foundAttribute2View = (TextView) findViewById(R.id.found_attribute_2);
        foundParcelableView.setText(R.string.manual_result_found_parcelable);
        foundAttribute1View.setText(R.string.manual_result_found_attr_1);
        foundAttribute2View.setText(R.string.manual_result_found_attr_2);
        DrawableHelper.setMarker(this, foundParcelableView, false);
        DrawableHelper.setMarker(this, foundAttribute1View, false);
        DrawableHelper.setMarker(this, foundAttribute2View, false);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Car car = extras.getParcelable(ManualConst.KEY);
        if (car != null) {
            DrawableHelper.setMarker(this, foundParcelableView, true);
            if (ManualConst.COLOR.equals(car.mColor)) {
                DrawableHelper.setMarker(this, foundAttribute1View, true);
            }
            if (ManualConst.TYPE.equals(car.mType)) {
                DrawableHelper.setMarker(this, foundAttribute2View, true);
            }
        }
        Log.i(TAG, "Restored car with color " + car.mColor + " and type " + car.mType);
    }

}
