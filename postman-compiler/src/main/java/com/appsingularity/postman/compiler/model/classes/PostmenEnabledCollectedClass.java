package com.appsingularity.postman.compiler.model.classes;

import android.support.annotation.NonNull;

import com.appsingularity.postman.annotations.PostmanEnabled;
import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.CollectedClass;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.CollectedFields;
import com.appsingularity.postman.compiler.writers.CollectedClassWriter;
import com.appsingularity.postman.compiler.writers.PostmenEnabledCollectedClassWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class PostmenEnabledCollectedClass implements CollectedClass {
    @NonNull private final List<CollectedField> mFields;
    @NonNull private final Element mClassElement;

    public static boolean canProcessElement(@NonNull Element classElement) {
        return (classElement.getKind() == ElementKind.CLASS)
            && (classElement.getAnnotation(PostmanEnabled.class) != null);
    }

    public PostmenEnabledCollectedClass(@NonNull Element classElement) {
        mClassElement = classElement;
        mFields = new ArrayList<>();
    }

    @Override
    public Element getClassElement() {
        return mClassElement;
    }

    @Override
    public List<CollectedField> getFields() {
        return mFields;
    }


    @Override
    public void addChild(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements, @NonNull Element child) {
        CollectedField field = CollectedFields.obtain(logger, types, elements, child);
        if (field != null) {
            mFields.add(field);
        }
    }

    @Override
    public CollectedClassWriter getWriter() {
        return new PostmenEnabledCollectedClassWriter(this);
    }

    @Override
    public String toString() {
        return mClassElement.toString();
    }
}
