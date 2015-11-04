package com.appsingularity.postman.compiler.handlers.primitives;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.handlers.AbsAttributeHandler;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class DoublePrimitiveHandler extends AbsAttributeHandler {

    public DoublePrimitiveHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
    }


    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        return (typeKind == TypeKind.DOUBLE);
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeDouble(source.$L)", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.readDouble()", element.getSimpleName().toString());
        return true;
    }

}
