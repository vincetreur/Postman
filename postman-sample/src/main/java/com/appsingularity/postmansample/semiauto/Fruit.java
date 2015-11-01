package com.appsingularity.postmansample.semiauto;

import android.os.Parcel;
import android.os.Parcelable;

// This base class was coded with hand coded parceling in mind
public class Fruit implements Parcelable {
    String mColor = "Green";

    public Fruit() { }



    protected Fruit(Parcel in) {
        mColor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };
}
