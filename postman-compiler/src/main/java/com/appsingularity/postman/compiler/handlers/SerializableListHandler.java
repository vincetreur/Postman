package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SerializableListHandler extends AbsListHandler {
    @NonNull
    private final Types mTypes;
    @NonNull
    private final Elements mElements;

    public SerializableListHandler(@NonNull Types types, @NonNull Elements elements) {
        super();
        mTypes = types;
        mElements = elements;
        mSupportedArgumentTypes.add("java.io.Serializable");
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

                    // Does it implement Parcelable?
                    TypeElement typeElement = mElements.getTypeElement("java.io.Serializable");
                    if (mTypes.isAssignable(typeArgument, typeElement.asType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.beginControlFlow("if (source.$L != null)", attr);
        shipMethod.addStatement("dest.writeByte((byte) 1)");
        shipMethod.addStatement("dest.writeList(source.$L)", attr);
        shipMethod.nextControlFlow("else");
        shipMethod.addStatement("dest.writeByte((byte) 0)");
        shipMethod.endControlFlow();
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        String enclosedType = getArgument(element);
        receiveMethod.beginControlFlow("if (in.readByte() == 1)");
        receiveMethod.addStatement("target.$L = new java.util.ArrayList<>()", attr);
        receiveMethod.addStatement("in.readList(target.$L, $L.class.getClassLoader())", attr, enclosedType);
        receiveMethod.endControlFlow();
        return true;
    }


    @Nullable
    private String getArgument(@NonNull Element element) {
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


}
