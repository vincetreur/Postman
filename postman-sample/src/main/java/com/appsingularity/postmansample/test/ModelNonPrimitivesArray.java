package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class ModelNonPrimitivesArray extends Model {
    Boolean[] mBoolean;
    Character[] mChar;
    Byte[] mByte;
    Short[] mShort;
    Integer[] mInt;
    Long[] mLong;
    Float[] mFloat;
    Double[] mDouble;

    public ModelNonPrimitivesArray() {
    }

    @NonNull @Override
    public String getName() {
        return "Non Primitive Arrays";
    }


    @Override
    public void setup() {
        mBoolean = new Boolean[] {true, false };
        mChar = new Character[] {'c', 'd'};
        mByte = new Byte[] {2, 3};
        mShort = new Short[] {4, 5};
        mInt = new Integer[] {6, 7};
        mLong = new Long[] {8L, 9L};
        mFloat = new Float[] {10f, 11f};
        mDouble = new Double[] {12d, 13d};
    }

    @Override
    public void showResults(@NonNull Context context, @NonNull ViewGroup layout) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addNewRow(context, inflater, layout, "Boolean[]", mBoolean != null && mBoolean[0] && !mBoolean[1]);
        addNewRow(context, inflater, layout, "Character[]", mChar != null && mChar[0] == 'c' && mChar[1] == 'd');
        addNewRow(context, inflater, layout, "Byte[]", mByte != null && mByte[0] == 2 && mByte[1] == 3);
        addNewRow(context, inflater, layout, "Short[]", mShort != null && mShort[0] == 4 && mShort[1] == 5);
        addNewRow(context, inflater, layout, "Integer[]", mInt != null && mInt[0] == 6 && mInt[1] == 7);
        addNewRow(context, inflater, layout, "Long[]", mLong != null && mLong[0] == 8 && mLong[1] == 9);
        addNewRow(context, inflater, layout, "Float[]", mFloat != null && mFloat[0] == 10 && mFloat[1] == 11);
        addNewRow(context, inflater, layout, "Double[]", mDouble != null && mDouble[0] == 12 && mDouble[1] == 13);
    }


    protected ModelNonPrimitivesArray(Parcel in) {
        Postman.receive(ModelNonPrimitivesArray.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(ModelNonPrimitivesArray.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelNonPrimitivesArray> CREATOR = new Creator<ModelNonPrimitivesArray>() {
        @Override
        public ModelNonPrimitivesArray createFromParcel(Parcel in) {
            return new ModelNonPrimitivesArray(in);
        }

        @Override
        public ModelNonPrimitivesArray[] newArray(int size) {
            return new ModelNonPrimitivesArray[size];
        }
    };

}
