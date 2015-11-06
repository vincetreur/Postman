package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.SerializableFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SerializableField implements CollectedField {
    private static final String CLASSNAME = "java.io.Serializable";
    @NonNull
    private final Element mElement;

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            TypeMirror typeMirror = element.asType();
            TypeKind typeKind = typeMirror.getKind();
            if (typeKind == TypeKind.DECLARED) {
                if (CLASSNAME.equals(element.asType().toString())) {
                    return true;
                }
                // Look for superclasses that implement Parcelable
                TypeElement typeElement = elements.getTypeElement(CLASSNAME);
                return (types.isAssignable(element.asType(), typeElement.asType()));
            }
        }
        return false;
    }

    public SerializableField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new SerializableFieldWriter(mElement);
    }

}
