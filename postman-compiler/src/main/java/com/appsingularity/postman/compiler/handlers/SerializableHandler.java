package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class SerializableHandler extends AbsAttributeHandler {
    private static final String CLASSNAME = "java.io.Serializable";

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {
            if (CLASSNAME.equals(element.asType().toString())) {
                return true;
            }
            // Look for superclasses that implement Parcelable
            return (isSubtypeOfType(element.asType(), CLASSNAME));
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        shipMethod.addStatement("dest.writeSerializable(source.$L)", element.getSimpleName().toString());
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        receiveMethod.addStatement("target.$L = ($L) in.readSerializable()", element.getSimpleName().toString(), element.asType().toString());
        return true;
    }


}