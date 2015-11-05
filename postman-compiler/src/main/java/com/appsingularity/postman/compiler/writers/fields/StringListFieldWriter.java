package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class StringListFieldWriter extends AbsCollectedFieldWriter {

    public StringListFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeStringList(source.$L)", mElement.getSimpleName().toString());
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        receiveMethod.addStatement("target.$L = in.createStringArrayList()", mElement.getSimpleName().toString());
        return true;
    }

}
