package com.appsingularity.postman.compiler.model.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedField;
import com.appsingularity.postman.compiler.writers.FieldErrorWriter;

import javax.lang.model.element.Element;

/**
 * Catch all field that is used to 'catch' all the fields that have not been processed.
 */
public class UnprocessedField extends AbsCollectedField {

    public static CollectedField canProcessElement(@NonNull Element element) {
        UnprocessedField instance = new UnprocessedField(element);
        instance.setError("Field was not processed, nothing could handle it");
        return instance;
    }

    private UnprocessedField(@NonNull Element element) {
        super(element, new FieldErrorWriter(element, ""));
    }

}
