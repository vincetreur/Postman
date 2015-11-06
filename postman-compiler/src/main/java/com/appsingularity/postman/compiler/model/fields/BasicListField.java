package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
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
    private static List<String> mSupportedArgumentTypes;

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedArgumentTypes == null) {
                mSupportedArgumentTypes = new ArrayList<>();
                mSupportedArgumentTypes.add("java.lang.Boolean");
                mSupportedArgumentTypes.add("java.lang.Character");
                mSupportedArgumentTypes.add("java.lang.Byte");
                mSupportedArgumentTypes.add("java.lang.Short");
                mSupportedArgumentTypes.add("java.lang.Integer");
                mSupportedArgumentTypes.add("java.lang.Long");
                mSupportedArgumentTypes.add("java.lang.Float");
                mSupportedArgumentTypes.add("java.lang.Double");
                mSupportedArgumentTypes.add("android.os.Parcelable");
                mSupportedArgumentTypes.add("java.io.Serializable");
                mSupportedArgumentTypes.add("android.os.Bundle");
            }
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
                        if (mSupportedArgumentTypes.contains(typeArgument.toString())) {
                            return true;
                        }

                        // Does it implement/extend or is any of the supported argument types?
                        for (String supportedType : mSupportedArgumentTypes) {
                            TypeElement typeElement = elements.getTypeElement(supportedType);
                            if (types.isAssignable(typeArgument, typeElement.asType())) {
                                return true;
                            }
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
