package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class NonPrimitiveDataTypeArrayFieldWriter extends AbsCollectedFieldWriter {

    public NonPrimitiveDataTypeArrayFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = getElement().getSimpleName().toString();
        shipMethod.addStatement("dest.writeValue(source.$L)", attr);
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = getElement().getSimpleName().toString();
        String type = getElement().asType().toString();
        receiveMethod.addStatement("target.$L = ($L) in.readValue($L.class.getClassLoader())", attr, type, type);
    }

}
