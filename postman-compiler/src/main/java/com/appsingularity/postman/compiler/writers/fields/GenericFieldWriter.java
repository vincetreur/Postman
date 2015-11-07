package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class GenericFieldWriter extends AbsCollectedFieldWriter {

    public GenericFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeValue(source.$L)", mElement.getSimpleName().toString());
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        String type = mElement.asType().toString();
        receiveMethod.addStatement("target.$L = ($L) in.readValue($L.class.getClassLoader())", attr, type, type);
    }
}
