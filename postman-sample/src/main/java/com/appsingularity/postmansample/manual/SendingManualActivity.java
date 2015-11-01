package com.appsingularity.postmansample.manual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.appsingularity.postmansample.R;


public class SendingManualActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.manual_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(R.string.manual_description);
        TextView codeView = (TextView) findViewById(R.id.code_subclass);
        codeView.setText(R.string.manual_code_subclass);
        codeView = (TextView) findViewById(R.id.code_superclass);
        codeView.setText(R.string.manual_code_superclass);
    }


    public void send(View view) {
        Car car = new Car();
        car.mColor = ManualConst.COLOR;
        car.mType = ManualConst.TYPE;

        Intent intent = new Intent(this, ReceivingManualActivity.class);
        intent.putExtra(ManualConst.KEY, car);
        startActivity(intent);
    }


}
