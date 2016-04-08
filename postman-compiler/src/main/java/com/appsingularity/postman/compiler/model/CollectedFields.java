package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.model.fields.BasicListField;
import com.appsingularity.postman.compiler.model.fields.BasicMapField;
import com.appsingularity.postman.compiler.model.fields.GenericArrayField;
import com.appsingularity.postman.compiler.model.fields.GenericField;
import com.appsingularity.postman.compiler.model.fields.NonPrimitiveDataTypeArrayField;
import com.appsingularity.postman.compiler.model.fields.ParcelableArrayField;
import com.appsingularity.postman.compiler.model.fields.ParcelableField;
import com.appsingularity.postman.compiler.model.fields.PrimitiveDataTypeField;
import com.appsingularity.postman.compiler.model.fields.SerializableField;
import com.appsingularity.postman.compiler.model.fields.ShortPrimitiveArrayField;
import com.appsingularity.postman.compiler.model.fields.SparseArrayField;
import com.appsingularity.postman.compiler.model.fields.SparseBooleanArrayField;
import com.appsingularity.postman.compiler.model.fields.StringListField;
import com.appsingularity.postman.compiler.model.fields.TypedObjectField;
import com.appsingularity.postman.compiler.model.fields.UnprocessableField;
import com.appsingularity.postman.compiler.model.fields.UnprocessedField;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class CollectedFields {

    private CollectedFields() {
        // Do not instantiate this class
    }

    @Nullable
    public static CollectedField obtain(@NonNull Types types, @NonNull Elements elements, @NonNull Element element)
        throws com.appsingularity.postman.compiler.model.FieldProcessingException {
        // Filter out fields that cannot be processed due to private/static/transient/etc
        CollectedField field = UnprocessableField.canProcessElement(element);
        // Try to find a handler for this field
        field = field == null ? PrimitiveDataTypeField.canProcessElement(element) : field;
        field = field == null ? ShortPrimitiveArrayField.canProcessElement(element) : field;
        field = field == null ? GenericField.canProcessElement(types, elements, element) : field;
        field = field == null ? StringListField.canProcessElement(types, elements, element) : field;
        field = field == null ? GenericArrayField.canProcessElement(element) : field;
        field = field == null ? BasicListField.canProcessElement(types, elements, element) : field;
        field = field == null ? BasicMapField.canProcessElement(types, elements, element) : field;
        field = field == null ? NonPrimitiveDataTypeArrayField.canProcessElement(element) : field;
        field = field == null ? TypedObjectField.canProcessElement(element) : field;
        field = field == null ? SparseBooleanArrayField.canProcessElement(types, elements, element) : field;
        field = field == null ? SparseArrayField.canProcessElement(types, elements, element) : field;
        field = field == null ? ParcelableArrayField.canProcessElement(types, elements, element) : field;
        field = field == null ? ParcelableField.canProcessElement(types, elements, element) : field;
        field = field == null ? SerializableField.canProcessElement(types, elements, element) : field;
        // Catch all field, that handles all unprocessed fields
        field = field == null ? UnprocessedField.canProcessElement(element) : field;

        return field;
    }

}
