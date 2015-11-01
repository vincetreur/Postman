package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class BooleanPrimitiveHandler extends AbsAttributeHandler {

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        return (typeKind == TypeKind.BOOLEAN);
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeByte((byte) (source.$L ? 1 : 0))", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.readByte() != 0", element.getSimpleName().toString());
        return true;
    }

}
