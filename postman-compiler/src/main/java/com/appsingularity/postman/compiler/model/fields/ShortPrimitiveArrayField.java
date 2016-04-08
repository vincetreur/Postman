package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.ShortPrimitiveArrayFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ShortPrimitiveArrayField implements CollectedField {
    @NonNull
    private final Element mElement;

    public static CollectedField canProcessElement(@NonNull Element element) {
        TypeMirror typeMirror = element.asType();
        TypeKind typeKind = typeMirror.getKind();
        if (typeKind == TypeKind.ARRAY) {
            if ("short[]".equals(element.asType().toString())) {
                return new ShortPrimitiveArrayField(element);
            }
        }
        return null;
    }

    private ShortPrimitiveArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new ShortPrimitiveArrayFieldWriter(mElement);
    }

}
