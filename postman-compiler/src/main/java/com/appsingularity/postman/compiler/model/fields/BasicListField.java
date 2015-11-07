package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.BasicListFieldWriter;


import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class BasicListField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    private final static List<String> SUPPORTED_ARGUMENT_TYPES;

    static {
        SUPPORTED_ARGUMENT_TYPES = new ArrayList<>();
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Boolean");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Character");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Byte");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Short");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Integer");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Long");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Float");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.Double");
        SUPPORTED_ARGUMENT_TYPES.add("android.os.Parcelable");
        SUPPORTED_ARGUMENT_TYPES.add("java.io.Serializable");
        SUPPORTED_ARGUMENT_TYPES.add("android.os.Bundle");
    }

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        TypeKind typeKind = element.asType().getKind();
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
                    if (SUPPORTED_ARGUMENT_TYPES.contains(typeArgument.toString())) {
                        return true;
                    }

                    // Does it implement/extend or is any of the supported argument types?
                    for (String supportedType : SUPPORTED_ARGUMENT_TYPES) {
                        TypeElement typeElement = elements.getTypeElement(supportedType);
                        if (types.isAssignable(typeArgument, typeElement.asType())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public BasicListField(@NonNull Element element) {
        mElement = element;
    }


    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new BasicListFieldWriter(mElement);
    }

}
