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
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeSerializable(source.$L)", getElement().getSimpleName().toString());
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        receiveMethod.addStatement("target.$L = ($L) in.readSerializable()", getElement().getSimpleName().toString()
            , getElement().asType().toString());
    }
}
