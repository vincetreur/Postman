package com.appsingularity.postmansample.semiauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.appsingularity.postmansample.R;
import com.appsingularity.postmansample.util.DrawableHelper;


public class ReceivingSemiAutoActivity extends AppCompatActivity {
    private final static String TAG = "ReceivingSemiAuto";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiving_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.semi_auto_result_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView foundParcelableView = (TextView) findViewById(R.id.found_parcelable);
        TextView foundAttribute1View = (TextView) findViewById(R.id.found_attribute_1);
        TextView foundAttribute2View = (TextView) findViewById(R.id.found_attribute_2);
        foundParcelableView.setText(R.string.result_found_parcelable);
        foundAttribute1View.setText(R.string.result_found_attr_subclass);
        foundAttribute2View.setText(R.string.result_found_attr_superclass);
        DrawableHelper.setMarker(this, foundParcelableView, false);
        DrawableHelper.setMarker(this, foundAttribute1View, false);
        DrawableHelper.setMarker(this, foundAttribute2View, false);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Apple apple = extras.getParcelable(SemiAutoConst.KEY);
        if (apple != null) {
            DrawableHelper.setMarker(this, foundParcelableView, true);
            if (SemiAutoConst.IS_TASTY == apple.mTasty) {
                DrawableHelper.setMarker(this, foundAttribute1View, true);
            }
            if (SemiAutoConst.COLOR.equals(apple.mColor)) {
                DrawableHelper.setMarker(this, foundAttribute2View, true);
            }
        }
        Log.i(TAG, "Restored apple with color " + apple.mColor + " and is tasty " + apple.mTasty);
    }





}
