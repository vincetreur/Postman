package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class BasicListFieldWriter extends AbsCollectedFieldWriter {

    public BasicListFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.beginControlFlow("if (source.$L != null)", attr);
        shipMethod.addStatement("dest.writeByte((byte) 1)");
        shipMethod.addStatement("dest.writeList(source.$L)", attr);
        shipMethod.nextControlFlow("else");
        shipMethod.addStatement("dest.writeByte((byte) 0)");
        shipMethod.endControlFlow();
        return true;
    }

    @Override
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        String enclosedType = ModelUtils.getArgument(mElement);
        receiveMethod.beginControlFlow("if (in.readByte() == 1)");
        receiveMethod.addStatement("target.$L = new java.util.ArrayList<>()", attr);
        receiveMethod.addStatement("in.readList(target.$L, $L.class.getClassLoader())", attr, enclosedType);
        receiveMethod.endControlFlow();
        return true;
    }

}
