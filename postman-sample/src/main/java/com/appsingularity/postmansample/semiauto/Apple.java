package com.appsingularity.postmansample.semiauto;

import android.os.Parcel;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;


@PostmanEnabled
public class Apple extends Fruit {
    boolean mTasty = false;

    public Apple() { }



    protected Apple(Parcel in) {
        // Let the superclass handle it's own parceling (which is hand coded)
        super(in);
        Postman.receive(Apple.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Let the superclass handle it's own parceling (which is hand coded)
        super.writeToParcel(dest, flags);
        Postman.ship(Apple.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Apple> CREATOR = new Creator<Apple>() {
        @Override
        public Apple createFromParcel(Parcel in) {
            return new Apple(in);
        }

        @Override
        public Apple[] newArray(int size) {
            return new Apple[size];
        }
    };
}
