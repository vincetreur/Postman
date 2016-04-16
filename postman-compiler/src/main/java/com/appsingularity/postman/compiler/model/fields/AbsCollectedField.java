package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.FieldErrorWriter;

import javax.lang.model.element.Element;

public abstract class AbsCollectedField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    private CollectedFieldWriter mWriter;
    private boolean mHasError = false;


    public AbsCollectedField(@NonNull Element element, @NonNull CollectedFieldWriter writer) {
        mElement = element;
        mWriter = writer;
    }

    protected void setError(@NonNull String errorMsg, final Object... args) {
        mHasError = true;
        String message = errorMsg;
        if (args.length > 0) {
            message = String.format(errorMsg, args);
        }
        mWriter = new FieldErrorWriter(mElement, message);
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return mWriter;
    }

    @Override
    public boolean hasError() {
        return mHasError;
    }

}
