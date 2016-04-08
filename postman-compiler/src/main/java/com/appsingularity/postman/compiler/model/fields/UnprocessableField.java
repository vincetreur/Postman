package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.FieldErrorWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

/**
 * Filter out fields that cannot be processed due to it's attributes.
 */
public class UnprocessableField extends AbsCollectedField {

    public static CollectedField canProcessElement(@NonNull Logger logger, @NonNull Element element) {
        UnprocessableField instance = new UnprocessableField(element);
        // Filter out everything but attributes
        if (element.getKind() != ElementKind.FIELD) {
            return instance;
        }
        // Skip Android stuff like serialVersionUID and CREATOR
        // Logging them would only mess up the output and would not add any value, so skip silently
        String name = element.getSimpleName().toString();
        if ("CREATOR".equals(name) || "serialVersionUID".equals(name)) {
            return instance;
        }
        if (element.getModifiers().contains(Modifier.STATIC)) {
            logger.other(element, "Field '%s.%s' was not processed, it is static", element.getEnclosingElement().getSimpleName(), element.getSimpleName());
            instance.setError("Field was not processed, it is static");
            return instance;
        }
        if (element.getModifiers().contains(Modifier.FINAL)) {
            logger.other(element, "Field '%s.%s' was not processed, it is final", element.getEnclosingElement().getSimpleName(), element.getSimpleName());
            instance.setError("Field was not processed, it is final");
            return instance;
        }
        if (element.getModifiers().contains(Modifier.TRANSIENT)) {
            logger.other(element, "Field '%s.%s' was not processed, it is transient", element.getEnclosingElement().getSimpleName(), element.getSimpleName());
            instance.setError("Field was not processed, it is transient");
            return instance;
        }
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            logger.other(element, "Field '%s.%s' was not processed, it is private", element.getEnclosingElement().getSimpleName(), element.getSimpleName());
            instance.setError("Field was not processed, it is private");
            return instance;
        }
        // Field can be processed
        return null;
    }

    private UnprocessableField(@NonNull Element element) {
        super(element, new FieldErrorWriter(element, ""));
    }

}
