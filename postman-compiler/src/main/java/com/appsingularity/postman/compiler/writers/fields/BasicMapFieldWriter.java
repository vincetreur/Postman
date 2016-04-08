package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;

import javax.lang.model.element.Element;

public class BasicMapFieldWriter extends AbsCollectedFieldWriter {

    public BasicMapFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = getElement().getSimpleName().toString();
        shipMethod.addStatement("dest.writeMap(source.$L)", attr);
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = getElement().getSimpleName().toString();
        receiveMethod.addCode("// Reading target.$L\n", attr);
        receiveMethod.addStatement("target.$L = new $T<>()", attr, HashMap.class);
        receiveMethod.addStatement("in.readMap(target.$L, getClass().getClassLoader())", attr);
    }

}
