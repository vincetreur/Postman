package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class ParcelableFieldWriter extends AbsCollectedFieldWriter {

    public ParcelableFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeParcelable(source.$L, flags)", getElement().getSimpleName().toString());
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        receiveMethod.addStatement("target.$L = in.readParcelable($L.class.getClassLoader())"
                , getElement().getSimpleName().toString(), getElement().asType().toString());
    }
}
