package com.appsingularity.postmansample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appsingularity.postmansample.fullauto.SendingFullAutoActivity;
import com.appsingularity.postmansample.manual.SendingManualActivity;
import com.appsingularity.postmansample.semiauto.SendingSemiAutoActivity;
import com.appsingularity.postmansample.test.TestSendingActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    public void testFullAuto(View view) {
        startActivity(new Intent(this, SendingFullAutoActivity.class));
    }

    public void testSemiAuto(View view) {
        startActivity(new Intent(this, SendingSemiAutoActivity.class));
    }

    public void testManual(View view) {
        startActivity(new Intent(this, SendingManualActivity.class));
    }

    public void test(View view) {
        startActivity(new Intent(this, TestSendingActivity.class));
    }


}
