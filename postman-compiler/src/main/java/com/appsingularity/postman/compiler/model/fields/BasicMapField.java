package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.fields.BasicMapFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class BasicMapField extends AbsCollectedField {
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

    public static CollectedField canProcessElement(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements,
                                            @NonNull Element element) throws IllegalArgumentException {
        BasicMapField instance = new BasicMapField(element);
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
                    TypeMirror typeArgument = typeArguments.get(0);
                    if (!isTypeSupported(types, elements, typeArgument)) {
                        logger.warn(element, "Map holds key type that is not Serializable or Parcelable '%s'", typeArgument);
                        instance.setError("Map holds key type that is not Serializable or Parcelable '%s'", typeArgument);
                        return instance;
                    }
                    if (typeArguments.size() < 2) {
                        throw new IllegalArgumentException();
                    }
                    typeArgument = typeArguments.get(1);
                    if (!isTypeSupported(types, elements, typeArgument)) {
                        logger.warn(element, "Map holds value type that is not Serializable or Parcelable '%s'", typeArgument);
                        instance.setError("Map holds value type that is not Serializable or Parcelable '%s'", typeArgument);
                        return instance;
                    }
                    // Types are ok
                    return instance;
                } else {
                    logger.warn(element, "Map is a raw type", element.getSimpleName());
                    instance.setError("Map is a raw type");
                    return instance;
                }
            }
        }
        return null;
    }

    private static boolean isTypeSupported(@NonNull Types types, @NonNull Elements elements, @NonNull TypeMirror typeArgument) {
        if (SUPPORTED_ARGUMENT_TYPES.contains(typeArgument.toString())) {
            return true;
        }

        // Does it implement/extend or is any of the supported argument types?
        return ModelUtils.isAssignableTo(types, elements, typeArgument, SUPPORTED_ARGUMENT_TYPES);
    }

    private BasicMapField(@NonNull Element element) {
        super(element, new BasicMapFieldWriter(element));
    }



}
