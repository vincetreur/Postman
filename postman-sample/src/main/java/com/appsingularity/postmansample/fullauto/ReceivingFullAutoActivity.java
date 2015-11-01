package com.appsingularity.postmansample.fullauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.appsingularity.postmansample.R;
import com.appsingularity.postmansample.util.DrawableHelper;


public class ReceivingFullAutoActivity extends AppCompatActivity {
    private final static String TAG = "ReceivingFullAuto";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiving_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.full_auto_result_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView foundParcelableView = (TextView) findViewById(R.id.found_parcelable);
        TextView foundAttribute1View = (TextView) findViewById(R.id.found_attribute_1);
        TextView foundAttribute2View = (TextView) findViewById(R.id.found_attribute_2);
        foundParcelableView.setText(R.string.full_auto_result_found_parcelable);
        foundAttribute1View.setText(R.string.full_auto_result_found_attr_1);
        foundAttribute2View.setText(R.string.full_auto_result_found_attr_2);
        DrawableHelper.setMarker(this, foundParcelableView, false);
        DrawableHelper.setMarker(this, foundAttribute1View, false);
        DrawableHelper.setMarker(this, foundAttribute2View, false);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Dog dog = extras.getParcelable(FullAutoConst.KEY);
        if (dog != null) {
            DrawableHelper.setMarker(this, foundParcelableView, true);
            if (FullAutoConst.NAME.equals(dog.mName)) {
                DrawableHelper.setMarker(this, foundAttribute1View, true);
            }
            if (FullAutoConst.HAS_TAIL == dog.mHasTail) {
                DrawableHelper.setMarker(this, foundAttribute2View, true);
            }
        }
        Log.i(TAG, "Restored dog with name " + dog.mName + " and has tail " + dog.mHasTail);
    }


}
