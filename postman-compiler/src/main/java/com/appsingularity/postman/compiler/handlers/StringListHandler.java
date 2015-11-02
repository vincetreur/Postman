package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class StringListHandler extends AbsAttributeHandler {

    public StringListHandler() {
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {

            System.err.println(element.asType().toString());
            if ("java.util.List<java.lang.String>".equals(element.asType().toString())) {
                return true;
            }
            if ("java.util.ArrayList<java.lang.String>".equals(element.asType().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeStringList(source.$L)", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.createStringArrayList()", element.getSimpleName().toString());
        return true;
    }

}
