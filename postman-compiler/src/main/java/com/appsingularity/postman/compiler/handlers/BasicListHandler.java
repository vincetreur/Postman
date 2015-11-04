package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class BasicListHandler extends AbsListHandler {

    public BasicListHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
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

}
