package com.appsingularity.postmansample.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.TextView;

import com.appsingularity.postmansample.R;

public class DrawableHelper {

    private DrawableHelper() {
    }

    public static void setMarker(@NonNull Context context, @NonNull TextView textView, boolean ok) {
        int iconResId = ok ? R.drawable.ic_check_black_24dp : R.drawable.ic_close_black_24dp;
        int colorResId = ok ? R.color.green_500 : R.color.red_500;
        Drawable d = ContextCompat.getDrawable(context, iconResId);
        Drawable wrappedD = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedD, ContextCompat.getColor(context, colorResId));
        textView.setCompoundDrawablesWithIntrinsicBounds(wrappedD, null, null, null);
    }

}
