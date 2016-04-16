package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;


public interface CollectedField {

    @NonNull
    CollectedFieldWriter getWriter();

    boolean hasError();

}
