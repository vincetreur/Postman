package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class AbsAttributeHandler implements AttributeHandler {
    @NonNull
    protected final Types mTypes;
    @NonNull
    protected final Elements mElements;

    public AbsAttributeHandler(@NonNull Types types, @NonNull Elements elements) {
        mTypes = types;
        mElements = elements;
    }

    @Override
    public final boolean addShipMethod(@NonNull final MethodSpec.Builder shipMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (!isProcessableTypeKind(element, typeKind)) {
            return false;
        }
        return shipMethod(shipMethod, element, typeKind);
    }

    protected abstract boolean shipMethod(@NonNull final MethodSpec.Builder shipMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind);

    @Override
    public final boolean addReveiveMethod(@NonNull final MethodSpec.Builder receiveMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (!isProcessableTypeKind(element, typeKind)) {
            return false;
        }
        return reveiveMethod(receiveMethod, element, typeKind);
    }

    protected abstract boolean reveiveMethod(@NonNull final MethodSpec.Builder receiveMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind);

    @Nullable
    protected String getArgument(@NonNull Element element) {
        TypeMirror typeMirror = element.asType();
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        String enclosedType = null;
        if (typeArguments != null && !typeArguments.isEmpty()) {
            TypeMirror typeArgument = typeArguments.get(0);
            enclosedType = typeArgument.toString();
        }
        return enclosedType;
    }

}
