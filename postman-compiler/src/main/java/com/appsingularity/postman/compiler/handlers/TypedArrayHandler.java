package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypedArrayHandler extends AbsAttributeHandler {
    @NonNull
    final private String mFQName;
    @NonNull
    final private String mNameCapitalized;

    public TypedArrayHandler(@NonNull Types types, @NonNull Elements elements, @NonNull final String fqName, @NonNull final String nameCapitalized) {
        super(types, elements);
        mFQName = fqName;
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
