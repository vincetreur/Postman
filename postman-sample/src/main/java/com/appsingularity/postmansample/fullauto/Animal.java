package com.appsingularity.postmansample.fullauto;

import android.os.Parcel;
import android.os.Parcelable;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

// This base class was coded with parceling in mind, so it's safe to process it
@PostmanEnabled
public abstract class Animal implements Parcelable {
    String mName;

    public Animal() { }

    protected Animal(Parcel in) {
        Postman.receive(Animal.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(Animal.class, this, dest, flags);
    }

}
