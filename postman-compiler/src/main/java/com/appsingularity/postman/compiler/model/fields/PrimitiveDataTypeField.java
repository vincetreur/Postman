package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.PrimitiveDataTypeFieldWriter;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class PrimitiveDataTypeField extends SimpleCollectedField {
    @NonNull
    private final Element mElement;

    public static CollectedField canProcessElement(@NonNull Element element) {
        TypeMirror typeMirror = element.asType();
        TypeKind typeKind = typeMirror.getKind();
        boolean ok = false;
        switch (typeKind) {
            case BOOLEAN:
            case CHAR:
            case BYTE:
            case SHORT:
            case INT:
            case FLOAT:
            case LONG:
            case DOUBLE:
                ok = true;
                break;

            default:
        }
        if (ok) {
            return new PrimitiveDataTypeField(element);
        }
        return null;
    }

    private PrimitiveDataTypeField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull @Override
    public CollectedFieldWriter getWriter() {
        return new PrimitiveDataTypeFieldWriter(mElement);
    }

}
