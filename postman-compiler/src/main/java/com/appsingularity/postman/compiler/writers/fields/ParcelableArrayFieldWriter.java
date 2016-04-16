package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;

public class ParcelableArrayFieldWriter extends AbsCollectedFieldWriter {

    public ParcelableArrayFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public void writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String attr = getElement().getSimpleName().toString();
        shipMethod.addStatement("dest.writeParcelableArray(source.$L, flags)", attr);
    }

    @Override
    public void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String attr = getElement().getSimpleName().toString();
        ArrayType arrayType = (ArrayType) getElement().asType();
        String enclosingType = arrayType.getComponentType().toString();
        receiveMethod.addCode("// Reading target.$L\n", attr);
        receiveMethod.addStatement("android.os.Parcelable[] $LCopy = in.readParcelableArray($L.class.getClassLoader())", attr, enclosingType);
        receiveMethod.beginControlFlow("if ($LCopy != null)", attr);
        receiveMethod.addStatement("target.$L = java.util.Arrays.copyOf($LCopy, $LCopy.length, $L.class)", attr, attr, attr, arrayType.toString());
        receiveMethod.endControlFlow();
    }
}
