package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public interface AttributeHandler {

    boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind);

    boolean addShipMethod(@NonNull final MethodSpec.Builder shipMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind);

    boolean addReveiveMethod(@NonNull final MethodSpec.Builder receiveMethod
            , @NonNull final Element element, @NonNull final TypeKind typeKind);


}
