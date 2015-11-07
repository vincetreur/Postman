package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.GenericFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class GenericField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    private final static List<String> SUPPORTED_TYPES;

    static {
        SUPPORTED_TYPES = new ArrayList<>();
        SUPPORTED_TYPES.add("java.lang.Boolean");
        SUPPORTED_TYPES.add("java.lang.Character");
        SUPPORTED_TYPES.add("java.lang.Byte");
        SUPPORTED_TYPES.add("java.lang.Short");
        SUPPORTED_TYPES.add("java.lang.Integer");
        SUPPORTED_TYPES.add("java.lang.Float");
        SUPPORTED_TYPES.add("java.lang.Long");
        SUPPORTED_TYPES.add("java.lang.Double");
        SUPPORTED_TYPES.add("android.os.Bundle");
    }

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        return (ModelUtils.isElementOfType(types, elements, element, SUPPORTED_TYPES));
    }

    public GenericField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new GenericFieldWriter(mElement);
    }

}
