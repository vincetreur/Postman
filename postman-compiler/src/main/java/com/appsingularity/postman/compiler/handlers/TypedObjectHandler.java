package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypedObjectHandler extends AbsAttributeHandler {
    @NonNull
    final private String mFQName;
    @NonNull
    final private String mName;

    public TypedObjectHandler(@NonNull Types types, @NonNull Elements elements, @NonNull final String fqName, @NonNull final String name) {
        super(types, elements);
        mFQName = fqName;
        mName = name;
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {
            if (mFQName.equals(element.asType().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.write$L(source.$L)", mName, element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = in.read$L()", element.getSimpleName().toString(), mName);
        return true;
    }

}
