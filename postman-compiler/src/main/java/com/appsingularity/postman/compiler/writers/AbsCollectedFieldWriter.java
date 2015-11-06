package com.appsingularity.postman.compiler.writers;

import android.support.annotation.NonNull;

import javax.lang.model.element.Element;

public abstract class AbsCollectedFieldWriter implements CollectedFieldWriter {
    @NonNull protected final Element mElement;

    public AbsCollectedFieldWriter(@NonNull Element element) {
        mElement = element;
    }

}
