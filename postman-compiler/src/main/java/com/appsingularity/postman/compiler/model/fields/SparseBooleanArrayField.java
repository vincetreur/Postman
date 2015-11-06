package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.GenericFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.SparseBooleanArrayFieldWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class SparseBooleanArrayField implements CollectedField {
    @NonNull
    private final Element mElement;
    protected static List<String> mSupportedTypes;

    public static boolean canProcessElement(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedTypes == null) {
                mSupportedTypes = new ArrayList<>();
                mSupportedTypes.add("android.util.SparseBooleanArray");
            }
            return (ModelUtils.isElementOfType(types, elements, element, mSupportedTypes));
        }
        return false;
    }

    public SparseBooleanArrayField(@NonNull Element element) {
        mElement = element;
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new SparseBooleanArrayFieldWriter(mElement);
    }

}
