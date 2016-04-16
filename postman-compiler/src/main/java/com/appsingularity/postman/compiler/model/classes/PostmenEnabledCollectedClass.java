package com.appsingularity.postman.compiler.model.classes;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.CollectedClass;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.model.CollectedFields;
import com.appsingularity.postman.compiler.model.FieldProcessingException;
import com.appsingularity.postman.compiler.writers.CollectedClassWriter;
import com.appsingularity.postman.compiler.writers.PostmenEnabledCollectedClassWriter;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class PostmenEnabledCollectedClass implements CollectedClass {
    @NonNull private final List<CollectedField> mFields;
    @NonNull private final Element mClassElement;

    public static boolean canProcessElement(@NonNull Logger logger, @NonNull Element classElement) {
        if (classElement.getKind() == ElementKind.INTERFACE) {
            logger.error(classElement, "Interface '%s' should not be annotated with @PostmanEnabled.",
                    classElement.getSimpleName());
            return false;
        }
        // Check for non-static inner classes
        Element enclosingElement = classElement.getEnclosingElement();
        if (enclosingElement != null) {
            if (enclosingElement.getKind() == ElementKind.CLASS && !classElement.getModifiers().contains(Modifier.STATIC)) {
                logger.error(classElement, "Class '%s' is an inner class of '%s' and is annotated with @PostmanEnabled, but is not static.",
                        classElement.getSimpleName(), enclosingElement.getSimpleName());
                return false;
            }
        }
        if (classElement.getKind() != ElementKind.CLASS) {
            logger.error(classElement, "File '%s' is annotated with @PostmanEnabled but it is not a class.",
                    classElement.getSimpleName());
            return false;
        }
        return true;
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
    public boolean addChild(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements, @NonNull Element child) {
        boolean addedWithoutError = true;
        CollectedField field;
        try {
            field = CollectedFields.obtain(types, elements, child);
            if (field != null) {
                if (field.hasError()) {
                    addedWithoutError = false;
                }
                mFields.add(field);
            }
        } catch (FieldProcessingException fpe) {
            addedWithoutError = false;
        }
        return addedWithoutError;
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
