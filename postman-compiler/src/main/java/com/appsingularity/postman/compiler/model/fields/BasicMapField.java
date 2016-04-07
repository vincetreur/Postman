package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.BasicMapFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class BasicMapField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    private final static List<String> SUPPORTED_TYPES;
    @NonNull
    private final static List<String> SUPPORTED_ARGUMENT_TYPES;

    static {
        SUPPORTED_TYPES = new ArrayList<>();
        SUPPORTED_TYPES.add("java.util.Map");
        SUPPORTED_TYPES.add("java.util.HashMap");
        SUPPORTED_ARGUMENT_TYPES = new ArrayList<>();
        SUPPORTED_ARGUMENT_TYPES.add("android.os.Parcelable");
        SUPPORTED_ARGUMENT_TYPES.add("java.io.Serializable");
    }

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        TypeKind typeKind = element.asType().getKind();
        if (typeKind == TypeKind.DECLARED) {
            // First check if it is a Map or HashMap
            TypeMirror typeMirror = element.asType();
            DeclaredType declaredType = (DeclaredType) typeMirror;
            Element typeAsElement = declaredType.asElement();
            if (SUPPORTED_TYPES.contains(typeAsElement.toString())) {
                // Then check the type argument
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (typeArguments != null && !typeArguments.isEmpty()) {
                    // TODO: Check second type?
                    TypeMirror typeArgument = typeArguments.get(0);
                    if (SUPPORTED_ARGUMENT_TYPES.contains(typeArgument.toString())) {
                        return true;
                    }

                    // Does it implement/extend or is any of the supported argument types?
                    for (String supportedArgType : SUPPORTED_ARGUMENT_TYPES) {
                        TypeElement typeElement = elements.getTypeElement(supportedArgType);
                        if (types.isAssignable(typeArgument, typeElement.asType())) {
                            return true;
                        }
                    }
                    // TODO: Log info about not processing
                }
            }
        }
        return false;
    }

    public BasicMapField(@NonNull Element element) {
        mElement = element;
    }


    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new BasicMapFieldWriter(mElement);
    }

}
