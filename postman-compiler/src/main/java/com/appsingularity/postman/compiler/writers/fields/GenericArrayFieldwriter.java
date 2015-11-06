package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class GenericArrayFieldwriter extends AbsCollectedFieldWriter {
    @NonNull
    final private String mNameCapitalized;

    public GenericArrayFieldwriter(@NonNull Element element, @NonNull final String nameCapitalized) {
        super(element);
        mNameCapitalized = nameCapitalized;
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.addStatement("dest.write$LArray(source.$L)", mNameCapitalized, attr);
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.create$LArray()", attr, mNameCapitalized);
        return true;
    }

}
