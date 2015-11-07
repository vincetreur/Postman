package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class ShortPrimitiveArrayFieldWriter extends AbsCollectedFieldWriter {

    public ShortPrimitiveArrayFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = mElement.getSimpleName().toString();
        shipMethod.addStatement("// Writing source.$L", attr);
        shipMethod.beginControlFlow("if (source.$L != null)", attr);
        shipMethod.addStatement("dest.writeByte((byte) 1)");
        shipMethod.addStatement("int[] $L_copy = new int[source.$L.length]", attr, attr);
        shipMethod.beginControlFlow("for (int i = 0; i < source.$L.length; i++)", attr);
        shipMethod.addStatement("$L_copy[i] = source.$L[i]", attr, attr);
        shipMethod.endControlFlow();
        shipMethod.addStatement("dest.writeIntArray($L_copy)", attr);
        shipMethod.nextControlFlow("else");
        shipMethod.addStatement("dest.writeByte((byte) 0)");
        shipMethod.endControlFlow();
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = mElement.getSimpleName().toString();
        receiveMethod.addStatement("// Reading target.$L", attr);
        receiveMethod.beginControlFlow("if (in.readByte() == 1)");
        receiveMethod.addStatement("int[] $L_copy = in.createIntArray()", attr);
        receiveMethod.addStatement("target.$L = new short[$L_copy.length]", attr, attr);
        receiveMethod.beginControlFlow("for (int i = 0; i < $L_copy.length; i++)", attr);
        receiveMethod.addStatement("target.$L[i] = (short) $L_copy[i]", attr, attr);
        receiveMethod.endControlFlow();
        receiveMethod.endControlFlow();
    }

}
