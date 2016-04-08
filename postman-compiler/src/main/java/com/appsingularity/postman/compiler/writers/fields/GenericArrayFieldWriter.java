package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class GenericArrayFieldWriter extends AbsCollectedFieldWriter {
    @NonNull
    final private String mNameCapitalized;

    public GenericArrayFieldWriter(@NonNull Element element, @NonNull final String nameCapitalized) {
        super(element);
        mNameCapitalized = nameCapitalized;
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = getElement().getSimpleName().toString();
        shipMethod.addStatement("dest.write$LArray(source.$L)", mNameCapitalized, attr);
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = getElement().getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.create$LArray()", attr, mNameCapitalized);
    }

}
