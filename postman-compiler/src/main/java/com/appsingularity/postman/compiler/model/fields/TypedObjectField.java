package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.TypedObjectFieldWriter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class TypedObjectField extends SimpleCollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    final private String mNameCapitalized;
    @NonNull
    private final static List<String> SUPPORTED_ARGUMENT_TYPES;

    static {
        SUPPORTED_ARGUMENT_TYPES = new ArrayList<>();
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.String");
        SUPPORTED_ARGUMENT_TYPES.add("android.util.Size");
        SUPPORTED_ARGUMENT_TYPES.add("android.util.SizeF");
        SUPPORTED_ARGUMENT_TYPES.add("android.os.PersistableBundle");
    }

    public static CollectedField canProcessElement(@NonNull Element element) {
        if (element.asType().getKind() == TypeKind.DECLARED
            && SUPPORTED_ARGUMENT_TYPES.contains(element.asType().toString())) {
            return new TypedObjectField(element);
        }
        return null;
    }

    private TypedObjectField(@NonNull Element element) {
        mElement = element;
        String name = element.asType().toString();
        name = ModelUtils.removePackageName(name);

        mNameCapitalized = StringUtils.capitalize(name);
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new TypedObjectFieldWriter(mElement, mNameCapitalized);
    }

}
