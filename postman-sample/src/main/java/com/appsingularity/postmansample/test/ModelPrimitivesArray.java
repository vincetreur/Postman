package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class ModelPrimitivesArray extends Model {
    boolean[] mBoolean;
    char[] mChar;
    byte[] mByte;
    short[] mShort;
    int[] mInt;
    long[] mLong;
    float[] mFloat;
    double[] mDouble;

    public ModelPrimitivesArray() {
    }

    @NonNull @Override
    public String getName() {
        return "Primitive Array";
    }


    @Override
    public void setup() {
        mBoolean = new boolean[] {true, false };
        mChar = new char[] {'c', 'd'};
        mByte = new byte[] {2, 3};
        mShort = new short[] {4, 5};
        mInt = new int[] {6, 7};
        mLong = new long[] {8, 9};
        mFloat = new float[] {10, 11};
        mDouble = new double[] {12, 13};
    }

    @Override
    public void showResults(@NonNull Context context, @NonNull ViewGroup layout) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addNewRow(context, inflater, layout, "boolean[]", mBoolean != null && mBoolean[0] && !mBoolean[1]);
        addNewRow(context, inflater, layout, "char[]", mChar != null && mChar[0] == 'c' && mChar[1] == 'd');
        addNewRow(context, inflater, layout, "byte[]", mByte != null && mByte[0] == 2 && mByte[1] == 3);
        addNewRow(context, inflater, layout, "short[]", mShort != null && mShort[0] == 4 && mShort[1] == 5);
        addNewRow(context, inflater, layout, "int[]", mInt != null && mInt[0] == 6 && mInt[1] == 7);
        addNewRow(context, inflater, layout, "long[]", mLong != null && mLong[0] == 8 && mLong[1] == 9);
        addNewRow(context, inflater, layout, "float[]", mFloat != null && mFloat[0] == 10 && mFloat[1] == 11);
        addNewRow(context, inflater, layout, "double[]", mDouble != null && mDouble[0] == 12 && mDouble[1] == 13);
    }


    protected ModelPrimitivesArray(Parcel in) {
        Postman.receive(ModelPrimitivesArray.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(ModelPrimitivesArray.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelPrimitivesArray> CREATOR = new Creator<ModelPrimitivesArray>() {
        @Override
        public ModelPrimitivesArray createFromParcel(Parcel in) {
            return new ModelPrimitivesArray(in);
        }

        @Override
        public ModelPrimitivesArray[] newArray(int size) {
            return new ModelPrimitivesArray[size];
        }
    };

}
