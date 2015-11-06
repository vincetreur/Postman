package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class NonPrimitiveDataTypeArrayFieldwriter extends AbsCollectedFieldWriter {

    public NonPrimitiveDataTypeArrayFieldwriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.addStatement("dest.writeValue(source.$L)", attr);
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        String type = mElement.asType().toString();
        receiveMethod.addStatement("target.$L = ($L) in.readValue($L.class.getClassLoader())", attr, type, type);
        return true;
    }

}
