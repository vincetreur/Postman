package com.appsingularity.postman.compiler.writers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class FieldErrorWriter extends AbsCollectedFieldWriter {
    @NonNull
    private final String mMessage;

    public FieldErrorWriter(@NonNull Element element, @NonNull String message) {
        super(element);
        mMessage = message;
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        if (!mMessage.trim().isEmpty()) {
            shipMethod.addCode("// $L: $L\n", getElement().getSimpleName(), mMessage);
        }
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        if (!mMessage.trim().isEmpty()) {
            receiveMethod.addCode("// $L: $L\n", getElement().getSimpleName(), mMessage);
        }
    }

}
