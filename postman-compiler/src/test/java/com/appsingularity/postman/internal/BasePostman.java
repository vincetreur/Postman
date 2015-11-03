package com.appsingularity.postman.internal;

import android.os.Parcelable;

/**
 * This dummy class is here so the tests compile
 */
public abstract class BasePostman<T extends Parcelable> {

    public abstract void receive(final T source, final android.os.Parcel dest);

    public abstract void ship(final T target, final android.os.Parcel in, int flags);

}
