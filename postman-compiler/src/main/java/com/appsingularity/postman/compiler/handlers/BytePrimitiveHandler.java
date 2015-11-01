package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class BytePrimitiveHandler extends AbsAttributeHandler {

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        return (typeKind == TypeKind.BYTE);
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeByte(source.$L)", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.readByte()", element.getSimpleName().toString());
        return true;
    }

}
