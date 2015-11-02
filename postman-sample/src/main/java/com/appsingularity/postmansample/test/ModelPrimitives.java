package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class ModelPrimitives extends Model {
    boolean mBoolean;
    char mChar;
    byte mByte;
    short mShort;
    int mInt;
    long mLong;
    float mFloat;
    double mDouble;

    public ModelPrimitives() {

    }

    @NonNull @Override
    public String getName() { return "Primitives"; }


    @Override
    public void setup() {
        mBoolean = true;
        mChar = 'c';
        mByte = 2;
        mShort = 3;
        mInt = 4;
        mLong = 5;
        mFloat = 6;
        mDouble = 7;
    }

    @Override
    public void showResults(@NonNull Context context, @NonNull ViewGroup layout) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addNewRow(context, inflater, layout, "boolean", mBoolean);
        addNewRow(context, inflater, layout, "char", mChar == 'c');
        addNewRow(context, inflater, layout, "byte", mByte == 2);
        addNewRow(context, inflater, layout, "short", mShort == 3);
        addNewRow(context, inflater, layout, "int", mInt == 4);
        addNewRow(context, inflater, layout, "long", mLong == 5);
        addNewRow(context, inflater, layout, "float", mFloat == 6);
        addNewRow(context, inflater, layout, "double", mDouble == 7);
    }


    protected ModelPrimitives(Parcel in) {
        Postman.receive(ModelPrimitives.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(ModelPrimitives.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelPrimitives> CREATOR = new Creator<ModelPrimitives>() {
        @Override
        public ModelPrimitives createFromParcel(Parcel in) {
            return new ModelPrimitives(in);
        }

        @Override
        public ModelPrimitives[] newArray(int size) {
            return new ModelPrimitives[size];
        }
    };

}
