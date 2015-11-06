package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class TypedObjectFieldwriter extends AbsCollectedFieldWriter {
    @NonNull
    final private String mNameCapitalized;

    public TypedObjectFieldwriter(@NonNull Element element, @NonNull final String nameCapitalized) {
        super(element);
        mNameCapitalized = nameCapitalized;
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.addStatement("dest.write$L(source.$L)", mNameCapitalized, attr);
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.read$L()", attr, mNameCapitalized);
        return true;
    }

}
