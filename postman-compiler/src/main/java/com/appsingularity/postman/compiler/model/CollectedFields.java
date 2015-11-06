package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.model.fields.BasicListField;
import com.appsingularity.postman.compiler.model.fields.NonPrimitiveDataTypeArrayField;
import com.appsingularity.postman.compiler.model.fields.ParcelableArrayField;
import com.appsingularity.postman.compiler.model.fields.ParcelableField;
import com.appsingularity.postman.compiler.model.fields.PrimitiveDataTypeField;
import com.appsingularity.postman.compiler.model.fields.SerializableField;
import com.appsingularity.postman.compiler.model.fields.ShortPrimitiveArrayField;
import com.appsingularity.postman.compiler.model.fields.SparseArrayField;
import com.appsingularity.postman.compiler.model.fields.SparseBooleanArrayField;
import com.appsingularity.postman.compiler.model.fields.StringListField;
import com.appsingularity.postman.compiler.model.fields.GenericArrayField;
import com.appsingularity.postman.compiler.model.fields.GenericField;
import com.appsingularity.postman.compiler.model.fields.TypedObjectField;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class CollectedFields {

    private CollectedFields() {
    }

    @Nullable
    public static CollectedField obtain(@NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        CollectedField field = null;
        if (PrimitiveDataTypeField.canProcessElement(element)) {
            field = new PrimitiveDataTypeField(element);
        } else if (ShortPrimitiveArrayField.canProcessElement(element)) {
            field = new ShortPrimitiveArrayField(element);
        } else if (GenericField.canProcessElement(types, elements, element)) {
            field = new GenericField(element);
        } else if (StringListField.canProcessElement(types, elements, element)) {
            field = new StringListField(element);
        } else if (GenericArrayField.canProcessElement(element)) {
            field = new GenericArrayField(element);
        } else if (BasicListField.canProcessElement(types, elements, element)) {
            field = new BasicListField(element);
        } else if (NonPrimitiveDataTypeArrayField.canProcessElement(element)) {
            field = new NonPrimitiveDataTypeArrayField(element);
        } else if (TypedObjectField.canProcessElement(element)) {
            field = new TypedObjectField(element);
        } else if (SparseBooleanArrayField.canProcessElement(types, elements, element)) {
            field = new SparseBooleanArrayField(element);
        } else if (SparseArrayField.canProcessElement(types, elements, element)) {
            field = new SparseArrayField(element);
        } else if (ParcelableArrayField.canProcessElement(types, elements, element)) {
            field = new ParcelableArrayField(element);
        } else if (ParcelableField.canProcessElement(types, elements, element)) {
            field = new ParcelableField(element);
        } else if (SerializableField.canProcessElement(types, elements, element)) {
            field = new SerializableField(element);
        }
        return field;
    }

}
