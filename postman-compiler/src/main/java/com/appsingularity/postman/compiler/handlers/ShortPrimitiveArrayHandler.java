package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class ShortPrimitiveArrayHandler extends AbsAttributeHandler {

    public ShortPrimitiveArrayHandler() {
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.ARRAY) {
            if ("short[]".equals(element.asType().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.addStatement("if (source.$L != null) {", attr);
        shipMethod.addStatement("    dest.writeByte((byte) 1)");
        shipMethod.addStatement("    int[] $L_copy = new int[source.$L.length]", attr, attr);
        shipMethod.addStatement("    for (int i = 0; i < source.$L.length; i++) {", attr);
        shipMethod.addStatement("        $L_copy[i] = source.$L[i]", attr, attr);
        shipMethod.addStatement("    }");
        shipMethod.addStatement("    dest.writeIntArray($L_copy)", attr);
        shipMethod.addStatement("} else {");
        shipMethod.addStatement("    dest.writeByte((byte) 0)");
        shipMethod.addStatement("}");
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        receiveMethod.addStatement("// Read $L", attr);
        receiveMethod.addStatement("if (in.readByte() == 1) {");
        receiveMethod.addStatement("    int[] $L_copy = in.createIntArray()", attr);
        receiveMethod.addStatement("    target.$L = new short[$L_copy.length]", attr, attr);
        receiveMethod.addStatement("    for (int i = 0; i < $L_copy.length; i++) {", attr);
        receiveMethod.addStatement("        target.$L[i] = (short) $L_copy[i]", attr, attr);
        receiveMethod.addStatement("    }");
        receiveMethod.addStatement(" }");
        return true;
    }

}
