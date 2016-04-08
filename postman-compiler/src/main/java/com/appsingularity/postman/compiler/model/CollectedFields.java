package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.Logger;
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

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class CollectedFields {

    private CollectedFields() {
    }

    @Nullable
    public static CollectedField obtain(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements, @NonNull Element element) {
        if (!ModelUtils.isProcessableAttribute(logger, element)) {
            return null;
        }
        CollectedField field = null;
        try {
            field = PrimitiveDataTypeField.canProcessElement(element);
            field = field == null ? ShortPrimitiveArrayField.canProcessElement(element) : field;
            field = field == null ? GenericField.canProcessElement(types, elements, element) : field;
            field = field == null ? StringListField.canProcessElement(types, elements, element) : field;
            field = field == null ? GenericArrayField.canProcessElement(element) : field;
            field = field == null ? BasicListField.canProcessElement(logger, types, elements, element) : field;
            field = field == null ? BasicMapField.canProcessElement(logger, types, elements, element) : field;
            field = field == null ? NonPrimitiveDataTypeArrayField.canProcessElement(element) : field;
            field = field == null ? TypedObjectField.canProcessElement(element) : field;
            field = field == null ? SparseBooleanArrayField.canProcessElement(types, elements, element) : field;
            field = field == null ? SparseArrayField.canProcessElement(types, elements, element) : field;
            field = field == null ? ParcelableArrayField.canProcessElement(types, elements, element) : field;
            field = field == null ? ParcelableField.canProcessElement(types, elements, element) : field;
            field = field == null ? SerializableField.canProcessElement(types, elements, element) : field;
            if (field == null) {
                logger.warn(element, "Nothing can process field. Maybe it's a raw type?");
            }
        } catch (IllegalArgumentException notProcessableType) {
            // ignore it has already been logged
        }
        return field;
    }

}
