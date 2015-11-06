package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.ShortPrinitiveArrayFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ShortPrimitiveArrayField implements CollectedField {
    @NonNull
    private final Element mElement;

    public static boolean canProcessElement(@NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            TypeMirror typeMirror = element.asType();
            TypeKind typeKind = typeMirror.getKind();
            if (typeKind == TypeKind.ARRAY) {
                if ("short[]".equals(element.asType().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ShortPrimitiveArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull @Override
    public CollectedFieldWriter getWriter() {
        return new ShortPrinitiveArrayFieldWriter(mElement);
    }

}
