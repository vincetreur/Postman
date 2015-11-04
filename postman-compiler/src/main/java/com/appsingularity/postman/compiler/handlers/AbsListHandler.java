package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class AbsListHandler extends AbsAttributeHandler {
    protected List<String> mSupportedArgumentTypes;

    public AbsListHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
        mSupportedArgumentTypes = new ArrayList<>();
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.DECLARED) {
            // First check if it is a List or ArrayList
            TypeMirror typeMirror = element.asType();
            DeclaredType declaredType = (DeclaredType) typeMirror;
            Element typeAsElement = declaredType.asElement();
            if ("java.util.List".equals(typeAsElement.toString()) || "java.util.ArrayList".equals(typeAsElement.toString())) {
                // Then check the type argument
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (typeArguments != null && !typeArguments.isEmpty()) {
                    TypeMirror typeArgument = typeArguments.get(0);
                    if (mSupportedArgumentTypes.contains(typeArgument.toString())) {
                        return true;
                    }

                    // Does it implement/extend or is any of the supported argument types?
                    for (String supportedType : mSupportedArgumentTypes) {
                        TypeElement typeElement = mElements.getTypeElement(supportedType);
                        if (mTypes.isAssignable(typeArgument, typeElement.asType())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
