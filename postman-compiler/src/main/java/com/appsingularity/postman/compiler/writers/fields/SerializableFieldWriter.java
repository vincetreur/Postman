package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class SerializableFieldWriter extends AbsCollectedFieldWriter {

    public SerializableFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeSerializable(source.$L)", mElement.getSimpleName().toString());
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        receiveMethod.addStatement("target.$L = ($L) in.readSerializable()", mElement.getSimpleName().toString()
            , mElement.asType().toString());
        return true;
    }
}
