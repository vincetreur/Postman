package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.SparseArrayFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SparseArrayField implements CollectedField {
    private static final String CLASSNAME = "android.util.SparseArray";
    @NonNull
    private final Element mElement;
    protected static List<String> mSupportedTypes;

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedTypes == null) {
                mSupportedTypes = new ArrayList<>();
                mSupportedTypes.add("android.util.SparseArray");
            }
            TypeKind typeKind = element.asType().getKind();
            if (typeKind == TypeKind.DECLARED) {
                TypeElement typeElement = elements.getTypeElement(CLASSNAME);
                DeclaredType declaredType = types.getDeclaredType(typeElement);
                return (types.isAssignable(element.asType(), declaredType));
            }
        }
        return false;
    }

    public SparseArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new SparseArrayFieldWriter(mElement);
    }

}
