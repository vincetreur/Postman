package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.SparseArrayFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SparseArrayField extends SimpleCollectedField {
    private static final String CLASSNAME = "android.util.SparseArray";
    @NonNull
    private final Element mElement;

    public static CollectedField canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        TypeKind typeKind = element.asType().getKind();
        if (typeKind == TypeKind.DECLARED) {
            TypeElement typeElement = elements.getTypeElement(CLASSNAME);
            DeclaredType declaredType = types.getDeclaredType(typeElement);
            if (types.isAssignable(element.asType(), declaredType)) {
                return new SparseArrayField(element);
            }
        }
        return null;
    }

    private SparseArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new SparseArrayFieldWriter(mElement);
    }

}
