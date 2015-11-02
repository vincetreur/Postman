package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsingularity.postmansample.R;
import com.appsingularity.postmansample.util.DrawableHelper;

public abstract class Model implements Parcelable {
    public static final String KEY = "model";

    @NonNull
    public abstract String getName();

    public abstract void setup();

    public abstract void showResults(@NonNull Context context, @NonNull ViewGroup layout);

    protected void addNewRow(@NonNull Context context, @NonNull LayoutInflater inflater
            , @NonNull ViewGroup layout, @NonNull String name, boolean ok) {
        TextView row = (TextView) inflater.inflate(R.layout.row, layout, false);
        row.setText(name);
        DrawableHelper.setMarker(context, row, ok);
        layout.addView(row);
    }

}
