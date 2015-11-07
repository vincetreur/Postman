package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.ParcelableArrayFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ParcelableArrayField implements CollectedField {
    private static final String CLASSNAME = "android.os.Parcelable";
    @NonNull
    private final Element mElement;

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        TypeMirror typeMirror = element.asType();
        TypeKind typeKind = typeMirror.getKind();
        if (typeKind == TypeKind.ARRAY) {
            TypeElement typeElement = elements.getTypeElement(CLASSNAME);
            ArrayType arrayType = types.getArrayType(typeElement.asType());
            return (types.isAssignable(element.asType(), arrayType));
        }
        return false;
    }

    public ParcelableArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new ParcelableArrayFieldWriter(mElement);
    }

}
