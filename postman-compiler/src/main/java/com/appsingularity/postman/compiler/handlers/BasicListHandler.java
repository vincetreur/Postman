package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class BasicListHandler extends AbsAttributeHandler {

    public BasicListHandler() {
    }


    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {
            String type = element.asType().toString();
            if ("java.util.List<java.lang.Boolean>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Boolean>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Character>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Character>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Byte>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Byte>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Short>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Short>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Integer>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Integer>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Long>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Long>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Float>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Float>".equals(type)) {
                return true;
            }
            if ("java.util.List<java.lang.Double>".equals(type)) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.Double>".equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.beginControlFlow("if (source.$L != null)", attr);
        shipMethod.addStatement("dest.writeByte((byte) 1)");
        shipMethod.addStatement("dest.writeList(source.$L)", attr);
        shipMethod.nextControlFlow("else");
        shipMethod.addStatement("dest.writeByte((byte) 0)");
        shipMethod.endControlFlow();
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        receiveMethod.beginControlFlow("if (in.readByte() == 1)");
        receiveMethod.addStatement("target.$L = new java.util.ArrayList<>()", attr);
        receiveMethod.addStatement("in.readList(target.$L, java.util.List.class.getClassLoader())", attr);
        receiveMethod.endControlFlow();
        return true;
    }

}
