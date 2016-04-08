package com.appsingularity.postman.compiler.model.fields;

import com.appsingularity.postman.compiler.model.CollectedField;

public abstract class SimpleCollectedField implements CollectedField {

    public SimpleCollectedField() {
    }

    @Override
    public boolean hasError() {
        return false;
    }

}
