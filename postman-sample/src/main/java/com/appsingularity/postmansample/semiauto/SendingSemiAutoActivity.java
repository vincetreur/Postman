package com.appsingularity.postmansample.semiauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.appsingularity.postmansample.R;


public class SendingSemiAutoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.semi_auto_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(R.string.semi_auto_description);
        TextView codeView = (TextView) findViewById(R.id.code_subclass);
        codeView.setText(R.string.semi_auto_code_subclass);
        codeView = (TextView) findViewById(R.id.code_superclass);
        codeView.setText(R.string.semi_auto_code_superclass);
    }


    public void send(View view) {
        Apple apple = new Apple();
        apple.mColor = SemiAutoConst.COLOR;
        apple.mTasty = SemiAutoConst.IS_TASTY;

        Intent intent = new Intent(this, ReceivingSemiAutoActivity.class);
        intent.putExtra(SemiAutoConst.KEY, apple);
        startActivity(intent);
    }


}
