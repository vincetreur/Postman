package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class SparseArrayFieldWriter extends AbsCollectedFieldWriter {

    public SparseArrayFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeSparseArray(source.$L)", mElement.getSimpleName().toString());
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.readSparseArray(getClass().getClassLoader())", attr);
    }
}
