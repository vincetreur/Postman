package com.appsingularity.postmansample.fullauto;

import android.os.Parcel;
import android.view.View;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@PostmanEnabled
public class Dog extends Animal {
    boolean mHasTail = false;
    List<View> mViews = new ArrayList<>();
    Map<String, View> mViewMap = new HashMap<>();

    public Dog() { }



    protected Dog(Parcel in) {
        // Let the superclass handle it's own parceling
        super(in);
        Postman.receive(Dog.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Let the superclass handle it's own parceling
        super.writeToParcel(dest, flags);
        Postman.ship(Dog.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };
}
