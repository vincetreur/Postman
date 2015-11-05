package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ParcelableArrayHandler extends AbsAttributeHandler {
    private static final String CLASSNAME = "android.os.Parcelable";

    public ParcelableArrayHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        if (typeKind == TypeKind.ARRAY) {
            TypeElement typeElement = mElements.getTypeElement(CLASSNAME);
            ArrayType arrayType = mTypes.getArrayType(typeElement.asType());
            return (mTypes.isAssignable(element.asType(), arrayType));
        }
        return false;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        shipMethod.addStatement("dest.writeParcelableArray(source.$L, flags)", attr);
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String attr = element.getSimpleName().toString();
        ArrayType arrayType = (ArrayType) element.asType();
        String enclosingType = arrayType.getComponentType().toString();
        receiveMethod.addStatement("android.os.Parcelable[] $LCopy = in.readParcelableArray($L.class.getClassLoader())", attr, enclosingType);
        receiveMethod.beginControlFlow("if ($LCopy != null)", attr);
        receiveMethod.addStatement("target.$L = java.util.Arrays.copyOf($LCopy, $LCopy.length, $L.class)", attr, attr, attr, element.asType().toString());
        receiveMethod.endControlFlow();
        return true;
    }


}
