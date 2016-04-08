package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.fields.BasicListFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class BasicListField extends AbsCollectedField {
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

    public static CollectedField canProcessElement(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements,
                                            @NonNull Element element) throws IllegalArgumentException {
        BasicListField instance = new BasicListField(element);
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
                        return instance;
                    }

                    // Does it implement/extend or is any of the supported argument types?
                    if (ModelUtils.isAssignableTo(types, elements, typeArgument, SUPPORTED_ARGUMENT_TYPES)) {
                        return instance;
                    }

                    logger.warn(element, "List holds type that is not Serializable or Parcelable '%s'", typeArgument);
                    instance.setError("List holds type that is not Serializable or Parcelable '%s'", typeArgument);
                    return instance;
                } else {
                    logger.warn(element, "List is a raw type", element.getSimpleName());
                    instance.setError("List is a raw type");
                    return instance;
                }
            }
        }
        return null;
    }

    private BasicListField(@NonNull Element element) {
        super(element, new BasicListFieldWriter(element));
    }


}
