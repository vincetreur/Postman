package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.StringListFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class StringListField extends SimpleCollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    private final static List<String> SUPPORTED_ARGUMENT_TYPES;

    static {
        SUPPORTED_ARGUMENT_TYPES = new ArrayList<>();
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.String");
    }

    public static CollectedField canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
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
                        return new StringListField(element);
                    }

                    // Does it implement/extend or is any of the supported argument types?
                    for (String supportedType : SUPPORTED_ARGUMENT_TYPES) {
                        TypeElement typeElement = elements.getTypeElement(supportedType);
                        if (types.isAssignable(typeArgument, typeElement.asType())) {
                            return new StringListField(element);
                        }
                    }
                }
            }
        }
        return null;
    }

    private StringListField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull @Override
    public CollectedFieldWriter getWriter() {
        return new StringListFieldWriter(mElement);
    }

}
