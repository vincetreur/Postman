package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class SparseBooleanArrayFieldWriter extends AbsCollectedFieldWriter {

    public SparseBooleanArrayFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeSparseBooleanArray(source.$L)", mElement.getSimpleName().toString());
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.readSparseBooleanArray()", attr);
        return true;
    }
}
