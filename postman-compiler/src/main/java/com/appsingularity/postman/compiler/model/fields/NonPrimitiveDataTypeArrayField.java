package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.NonPrimitiveDataTypeArrayFieldwriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class NonPrimitiveDataTypeArrayField implements CollectedField {
    @NonNull
    private final Element mElement;
    private static List<String> mSupportedArgumentTypes;

    public static boolean canProcessElement(@NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedArgumentTypes == null) {
                mSupportedArgumentTypes = new ArrayList<>();
                mSupportedArgumentTypes.add("java.lang.Boolean[]");
                mSupportedArgumentTypes.add("java.lang.Character[]");
                mSupportedArgumentTypes.add("java.lang.Byte[]");
                mSupportedArgumentTypes.add("java.lang.Short[]");
                mSupportedArgumentTypes.add("java.lang.Integer[]");
                mSupportedArgumentTypes.add("java.lang.Long[]");
                mSupportedArgumentTypes.add("java.lang.Float[]");
                mSupportedArgumentTypes.add("java.lang.Double[]");
            }
            if (element.asType().getKind() == TypeKind.ARRAY) {
                if (mSupportedArgumentTypes.contains(element.asType().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public NonPrimitiveDataTypeArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new NonPrimitiveDataTypeArrayFieldwriter(mElement);
    }

}
