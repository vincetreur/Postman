package com.appsingularity.postmansample.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appsingularity.postmansample.R;


public class TestSendingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sending_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.test_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void testPrimitives(View view) {
        send(new ModelPrimitives());
    }

    public void testNonPrimitives(View view) {
        send(new ModelNonPrimitives());
    }

    public void testPrimitivesArrays(View view) {
        send(new ModelPrimitivesArray());
    }

    public void testNonPrimitivesArrays(View view) {
        send(new ModelNonPrimitivesArray());
    }

    public void testStrings(View view) {
        send(new ModelStrings());
    }

    private void send(@NonNull Model model) {
        model.setup();
        Intent intent = new Intent(this, TestReceivingActivity.class);
        intent.putExtra(Model.KEY, model);
        startActivity(intent);
    }

}
