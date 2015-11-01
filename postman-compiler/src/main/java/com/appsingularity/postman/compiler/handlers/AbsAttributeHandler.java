package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public abstract class AbsAttributeHandler implements AttributeHandler {



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


    protected boolean isSubtypeOfType(@NonNull TypeMirror typeMirror, @NonNull String otherType) {
        if (otherType.equals(typeMirror.toString())) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

}
