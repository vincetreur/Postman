package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SupportedTypes {

    @NonNull
    private final static List<String> SUPPORTED_GENERICS;

    static {
        SUPPORTED_GENERICS = new ArrayList<>();
        SUPPORTED_GENERICS.add("android.os.Parcelable");
        SUPPORTED_GENERICS.add("java.io.Serializable");
    }

    private SupportedTypes() {
    }

    @NonNull
    public static List<String> supportedGenerics() {
        return SUPPORTED_GENERICS;
    }

}
