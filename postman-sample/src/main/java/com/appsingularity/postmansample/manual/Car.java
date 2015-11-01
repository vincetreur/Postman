package com.appsingularity.postmansample.manual;


import android.os.Parcel;
import android.os.Parcelable;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;


@PostmanEnabled
public class Car extends Vehicle implements Parcelable {
    String mColor;

    public Car() { }

    protected Car(Parcel in) {
        Postman.receive(Car.class, this, in);
        // Manually process attributes from the superclass since it is not parcelable
        mType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(Car.class, this, dest, flags);
        // Manually process attributes from the superclass since it is not parcelable
        dest.writeString(mType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}
