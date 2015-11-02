package com.appsingularity.postmansample.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsingularity.postmansample.R;


public class TestReceivingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_receiving_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.test_result_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView titleView = (TextView) findViewById(R.id.title);
        LinearLayout resultsLayout = (LinearLayout) findViewById(R.id.results);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Model model = extras.getParcelable(Model.KEY);
        titleView.setText(model.getName());
        model.showResults(this, resultsLayout);
    }

}
