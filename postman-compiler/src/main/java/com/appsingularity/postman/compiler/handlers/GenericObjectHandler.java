package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class GenericObjectHandler extends AbsAttributeHandler {
    protected List<String> mSupportedTypes;

    public GenericObjectHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
        mSupportedTypes = new ArrayList<>();
        mSupportedTypes.add("java.lang.Boolean");
        mSupportedTypes.add("java.lang.Character");
        mSupportedTypes.add("java.lang.Byte");
        mSupportedTypes.add("java.lang.Short");
        mSupportedTypes.add("java.lang.Integer");
        mSupportedTypes.add("java.lang.Float");
        mSupportedTypes.add("java.lang.Long");
        mSupportedTypes.add("java.lang.Double");
        mSupportedTypes.add("android.os.Bundle");
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {
            // Does it implement/extend or is any of the supported types?
            for (String supportedType : mSupportedTypes) {
                TypeElement typeElement = mElements.getTypeElement(supportedType);
                if (mTypes.isAssignable(element.asType(), typeElement.asType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeValue(source.$L)", element.getSimpleName().toString());
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
