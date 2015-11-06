package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.ModelUtils;
import com.appsingularity.postman.compiler.writers.CollectedFieldWriter;
import com.appsingularity.postman.compiler.writers.fields.TypedObjectFieldwriter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;

public class TypedObjectField implements CollectedField {
    @NonNull
    private final Element mElement;
    @NonNull
    final private String mNameCapitalized;
    private static List<String> mSupportedArgumentTypes;

    public static boolean canProcessElement(@NonNull Element element) {
        if (ModelUtils.isProcessableAttribute(element)) {
            if (mSupportedArgumentTypes == null) {
                mSupportedArgumentTypes = new ArrayList<>();
                mSupportedArgumentTypes.add("java.lang.String");
                mSupportedArgumentTypes.add("android.util.Size");
                mSupportedArgumentTypes.add("android.util.SizeF");
                mSupportedArgumentTypes.add("android.os.PersistableBundle");
            }
            if (element.asType().getKind() == TypeKind.DECLARED) {
                return (mSupportedArgumentTypes.contains(element.asType().toString()));
            }
        }
        return false;
    }

    public TypedObjectField(@NonNull Element element) {
        mElement = element;
        String name = element.asType().toString();
        name = ModelUtils.removePackageName(name);

        mNameCapitalized = StringUtils.capitalize(name);
    }

    @NonNull
    @Override
    public CollectedFieldWriter getWriter() {
        return new TypedObjectFieldwriter(mElement, mNameCapitalized);
    }

}
