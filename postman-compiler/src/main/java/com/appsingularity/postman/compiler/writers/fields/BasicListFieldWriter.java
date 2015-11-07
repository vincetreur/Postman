package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;

import javax.lang.model.element.Element;

public class BasicListFieldWriter extends AbsCollectedFieldWriter {

    public BasicListFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.addStatement("// Writing source.$L", attr);
        shipMethod.beginControlFlow("if (source.$L != null)", attr);
        shipMethod.addStatement("dest.writeByte((byte) 1)");
        shipMethod.addStatement("dest.writeList(source.$L)", attr);
        shipMethod.nextControlFlow("else");
        shipMethod.addStatement("dest.writeByte((byte) 0)");
        shipMethod.endControlFlow();
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        String enclosedType = ModelUtils.getArgument(mElement);
        receiveMethod.addStatement("// Reading target.$L", attr);
        receiveMethod.beginControlFlow("if (in.readByte() == 1)");
        receiveMethod.addStatement("target.$L = new $T<>()", attr, ArrayList.class);
        receiveMethod.addStatement("in.readList(target.$L, $L.class.getClassLoader())", attr, enclosedType);
        receiveMethod.endControlFlow();
    }

}
