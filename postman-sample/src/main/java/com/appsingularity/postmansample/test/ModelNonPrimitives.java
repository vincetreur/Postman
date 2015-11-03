package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class ModelNonPrimitives extends Model {
    Boolean mBoolean;
    Character mChar;
    Byte mByte;
    Short mShort;
    Integer mInt;
    Long mLong;
    Float mFloat;
    Double mDouble;

    public ModelNonPrimitives() {

    }

    @NonNull @Override
    public String getName() {
        return "Non Primitives";
    }


    @Override
    public void setup() {
        mBoolean = true;
        mChar = 'c';
        mByte = 2;
        mShort = 3;
        mInt = 4;
        mLong = 5L;
        mFloat = 6f;
        mDouble = 7d;
    }

    @Override
    public void showResults(@NonNull Context context, @NonNull ViewGroup layout) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addNewRow(context, inflater, layout, "Boolean", mBoolean);
        addNewRow(context, inflater, layout, "Character", mChar.equals(new Character('c')));
        addNewRow(context, inflater, layout, "Byte", mByte == 2);
        addNewRow(context, inflater, layout, "Short", mShort == 3);
        addNewRow(context, inflater, layout, "Integer", mInt == 4);
        addNewRow(context, inflater, layout, "Long", mLong == 5);
        addNewRow(context, inflater, layout, "Float", mFloat == 6);
        addNewRow(context, inflater, layout, "Double", mDouble == 7);
    }


    protected ModelNonPrimitives(Parcel in) {
        Postman.receive(ModelNonPrimitives.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(ModelNonPrimitives.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelNonPrimitives> CREATOR = new Creator<ModelNonPrimitives>() {
        @Override
        public ModelNonPrimitives createFromParcel(Parcel in) {
            return new ModelNonPrimitives(in);
        }

        @Override
        public ModelNonPrimitives[] newArray(int size) {
            return new ModelNonPrimitives[size];
        }
    };

}
