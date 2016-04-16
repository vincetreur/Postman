package com.appsingularity.postmansample.fullauto;

import android.os.Parcel;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;


@PostmanEnabled
public class Dog extends Animal {
    boolean mHasTail = false;

    public Dog() {
        super();
    }

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
