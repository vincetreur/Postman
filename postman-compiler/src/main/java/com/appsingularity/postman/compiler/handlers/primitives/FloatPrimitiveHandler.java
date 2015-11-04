package com.appsingularity.postman.compiler.handlers.primitives;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.handlers.AbsAttributeHandler;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class FloatPrimitiveHandler extends AbsAttributeHandler {

    public FloatPrimitiveHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        return (typeKind == TypeKind.FLOAT);
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeFloat(source.$L)", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.readFloat()", element.getSimpleName().toString());
        return true;
    }

}
