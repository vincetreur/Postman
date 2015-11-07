package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.GenericArrayFieldWriter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;

public class GenericArrayField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    final private String mNameCapitalized;
    @NonNull
    private final static List<String> SUPPORTED_ARGUMENT_TYPES;

    static {
        SUPPORTED_ARGUMENT_TYPES = new ArrayList<>();
        SUPPORTED_ARGUMENT_TYPES.add("boolean[]");
        SUPPORTED_ARGUMENT_TYPES.add("char[]");
        SUPPORTED_ARGUMENT_TYPES.add("byte[]");
        SUPPORTED_ARGUMENT_TYPES.add("int[]");
        SUPPORTED_ARGUMENT_TYPES.add("long[]");
        SUPPORTED_ARGUMENT_TYPES.add("float[]");
        SUPPORTED_ARGUMENT_TYPES.add("double[]");
        SUPPORTED_ARGUMENT_TYPES.add("java.lang.String[]");
    }

    public static boolean canProcessElement(@NonNull Element element) {
        if (element.asType().getKind() == TypeKind.ARRAY) {
            if (SUPPORTED_ARGUMENT_TYPES.contains(element.asType().toString())) {
                return true;
            }
        }
        return false;
    }

    public GenericArrayField(@NonNull Element element) {
        mElement = element;
        ArrayType arrayType = (ArrayType) element.asType();
        String name = arrayType.getComponentType().toString();
        name = ModelUtils.removePackageName(name);
        mNameCapitalized = StringUtils.capitalize(name);
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new GenericArrayFieldWriter(mElement, mNameCapitalized);
    }

}
