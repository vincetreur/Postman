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
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeSparseBooleanArray(source.$L)", getElement().getSimpleName().toString());
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = getElement().getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.readSparseBooleanArray()", attr);
    }
}
