package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.GenericArrayFieldwriter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;

public class GenericArrayField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    final private String mNameCapitalized;
    private static List<String> mSupportedArgumentTypes;

    public static boolean canProcessElement(@NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedArgumentTypes == null) {
                mSupportedArgumentTypes = new ArrayList<>();
                mSupportedArgumentTypes.add("boolean[]");
                mSupportedArgumentTypes.add("char[]");
                mSupportedArgumentTypes.add("byte[]");
                mSupportedArgumentTypes.add("int[]");
                mSupportedArgumentTypes.add("long[]");
                mSupportedArgumentTypes.add("float[]");
                mSupportedArgumentTypes.add("double[]");
                mSupportedArgumentTypes.add("java.lang.String[]");
            }
            if (element.asType().getKind() == TypeKind.ARRAY) {
                if (mSupportedArgumentTypes.contains(element.asType().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public GenericArrayField(@NonNull Element element) {
        mElement = element;
        ArrayType arrayType = (ArrayType) element.asType();
        String name = arrayType.getComponentType().toString();
        name = ModelUtils.removePackageName(name);
        mNameCapitalized = StringUtils.capitalize(name);
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new GenericArrayFieldwriter(mElement, mNameCapitalized);
    }

}
