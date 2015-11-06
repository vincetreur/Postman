package com.appsingularity.postmansample.test;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

import java.util.ArrayList;
import java.util.List;

@PostmanEnabled
public class ModelStrings extends Model {
    String mString;
    String[] mStringArray;
    List<String> mStringList;
    ArrayList<String> mStringArrayList;


    public ModelStrings() {
    }

    @NonNull @Override
    public String getName() {
        return "Strings";
    }


    @Override
    public void setup() {
        mString = "Some string";
        mStringArray = new String[] {"string 1", null, "string 3"};
        mStringList = new ArrayList<>();
        mStringList.add("string 1");
        mStringList.add(null);
        mStringList.add("string 3");
        mStringArrayList = new ArrayList<>();
        mStringArrayList.add("string 1");
        mStringArrayList.add(null);
        mStringArrayList.add("string 3");
    }

    @Override
    public void showResults(@NonNull Context context, @NonNull ViewGroup layout) {
        LayoutInflater inflater = LayoutInflater.from(context);
        addNewRow(context, inflater, layout, "String", "Some string".equals(mString));
        boolean ok = mStringArray != null;
        ok = ok & mStringArray.length == 3;
        ok = ok & "string 1".equals(mStringArray[0]);
        ok = ok & mStringArray[1] == null;
        ok = ok & "string 3".equals(mStringArray[2]);
        addNewRow(context, inflater, layout, "String[]", ok);
        ok = mStringList != null;
        ok = ok & mStringList.size() == 3;
        ok = ok & "string 1".equals(mStringList.get(0));
        ok = ok & mStringList.get(1) == null;
        ok = ok & "string 3".equals(mStringList.get(2));
        addNewRow(context, inflater, layout, "List<String>", ok);
        ok = mStringArrayList != null;
        ok = ok & mStringArrayList.size() == 3;
        ok = ok & "string 1".equals(mStringArrayList.get(0));
        ok = ok & mStringArrayList.get(1) == null;
        ok = ok & "string 3".equals(mStringArrayList.get(2));
        addNewRow(context, inflater, layout, "ArrayList<String>", ok);
    }


    protected ModelStrings(Parcel in) {
        Postman.receive(ModelStrings.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(ModelStrings.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelStrings> CREATOR = new Creator<ModelStrings>() {
        @Override
        public ModelStrings createFromParcel(Parcel in) {
            return new ModelStrings(in);
        }

        @Override
        public ModelStrings[] newArray(int size) {
            return new ModelStrings[size];
        }
    };

}
