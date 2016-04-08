package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.fields.SupportedTypes;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ModelUtils {

    private ModelUtils() {
    }

    @Nullable
    public static String getArgument(@NonNull Element element) {
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

    public static boolean isProcessableAttribute(@NonNull Logger logger, @NonNull Element element) {
        // Filter out everything but attributes
        if (element.getKind() != ElementKind.FIELD) {
            return false;
        }
        // Filter out static attributes
        if (element.getModifiers().contains(Modifier.STATIC)) {
            return false;
        }
        // Filter out transient attributes
        if (element.getModifiers().contains(Modifier.TRANSIENT)) {
            return false;
        }
        // Filter out private attributes
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            logger.other(element, "Field could not be processed, it's private.");
            return false;
        }
        return true;
    }

    @NonNull
    public static String removePackageName(@NonNull String name) {
        // String package name
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(pos + 1);
        }
        return name;
    }

    public static boolean isElementOfType(@NonNull Types types, @NonNull Elements elements, @NonNull Element element
            , @NonNull List<String> supportedTypes) {
        TypeKind typeKind = element.asType().getKind();
        if (typeKind == TypeKind.DECLARED) {
            // Does it implement/extend or isElementOfType any of the supported types?
            for (String supportedType : supportedTypes) {
                TypeElement typeElement = elements.getTypeElement(supportedType);
                if (types.isAssignable(element.asType(), typeElement.asType())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAssignableTo(@NonNull Types types, @NonNull Elements elements,
                                         @NonNull TypeMirror typeArgument, @NonNull List<String> supportedTypes) {
        if (SupportedTypes.supportedGenerics().contains(typeArgument.toString())) {
            return true;
        }
        for (String supportedType : supportedTypes) {
            TypeElement typeElement = elements.getTypeElement(supportedType);
            if (types.isAssignable(typeArgument, typeElement.asType())) {
                return true;
            }
        }
        return false;
    }

}
