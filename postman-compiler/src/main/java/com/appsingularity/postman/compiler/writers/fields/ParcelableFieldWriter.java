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
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        shipMethod.addStatement("dest.writeParcelable(source.$L, flags)", mElement.getSimpleName().toString());
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        receiveMethod.addStatement("target.$L = in.readParcelable($L.class.getClassLoader())"
                , mElement.getSimpleName().toString(), mElement.asType().toString());
        return true;
    }
}
