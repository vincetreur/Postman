package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class BasicArrayHandler extends AbsAttributeHandler {

    public BasicArrayHandler() {
    }


    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.ARRAY) {
            String type = element.asType().toString();
            if ("java.lang.Boolean[]".equals(type)) {
                return true;
            }
            if ("java.lang.Character[]".equals(type)) {
                return true;
            }
            if ("java.lang.Byte[]".equals(type)) {
                return true;
            }
            if ("java.lang.Short[]".equals(type)) {
                return true;
            }
            if ("java.lang.Integer[]".equals(type)) {
                return true;
            }
            if ("java.lang.Float[]".equals(type)) {
                return true;
            }
            if ("java.lang.Long[]".equals(type)) {
                return true;
            }
            if ("java.lang.Double[]".equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.addStatement("dest.writeValue(source.$L)", attr);
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        String type = element.asType().toString();
        receiveMethod.addStatement("target.$L = ($L) in.readValue($L.class.getClassLoader())", attr, type, type);
        return true;
    }

}
