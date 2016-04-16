package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.SerializableFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SerializableField extends SimpleCollectedField {
    private static final String CLASSNAME = "java.io.Serializable";
    @NonNull
    private final Element mElement;

    public static CollectedField canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        TypeMirror typeMirror = element.asType();
        TypeKind typeKind = typeMirror.getKind();
        if (typeKind == TypeKind.DECLARED) {
            if (CLASSNAME.equals(element.asType().toString())) {
                return new SerializableField(element);
            }
            // Look for superclasses that implement Parcelable
            TypeElement typeElement = elements.getTypeElement(CLASSNAME);
            if (types.isAssignable(element.asType(), typeElement.asType())) {
                return new SerializableField(element);
            }
        }
        return null;
    }

    private SerializableField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new SerializableFieldWriter(mElement);
    }

}
