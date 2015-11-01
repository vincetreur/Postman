package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class GenericArrayHandler extends AbsAttributeHandler {
    @NonNull
    final private String mFQName;
    @NonNull
    final private String mName;
    @NonNull
    final private String mNameCapitalized;

    public GenericArrayHandler(@NonNull final String fqName, @NonNull final String name) {
        this(fqName, name, name);
    }

    public GenericArrayHandler(@NonNull final String fqName, @NonNull final String name, @NonNull final String nameCapitalized) {
        mFQName = fqName;
        mName = name;
        mNameCapitalized = nameCapitalized;
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.ARRAY) {
            if (mFQName.equals(element.asType().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.addStatement("dest.write$LArray(source.$L)", mNameCapitalized, attr);
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        receiveMethod.addStatement("target.$L = in.create$LArray()", attr, mNameCapitalized);
        return true;
    }

}
