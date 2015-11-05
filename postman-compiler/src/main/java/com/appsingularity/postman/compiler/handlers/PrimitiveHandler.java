package com.appsingularity.postman.compiler.handlers;

import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class PrimitiveHandler extends AbsAttributeHandler {

    public PrimitiveHandler(@NonNull Types types, @NonNull Elements elements) {
        super(types, elements);
    }

    @Override
    public boolean isProcessableTypeKind(@NonNull final Element element, @NonNull final TypeKind typeKind) {
        boolean ok = false;
        switch (typeKind) {
            case BOOLEAN:
            case CHAR:
            case BYTE:
            case SHORT:
            case INT:
            case FLOAT:
            case LONG:
            case DOUBLE:
                ok = true;
                break;

            default:
        }
        return ok;
    }

    @Override
    protected boolean shipMethod(@NonNull MethodSpec.Builder shipMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String name = element.getSimpleName().toString();
        switch (typeKind) {
            case BOOLEAN:
                shipMethod.addStatement("dest.writeByte((byte) (source.$L ? 1 : 0))", name);
                break;

            case CHAR:
                shipMethod.addStatement("dest.writeInt(source.$L)", name);
                break;

            case BYTE:
                shipMethod.addStatement("dest.writeByte(source.$L)", name);
                break;

            case SHORT:
                shipMethod.addStatement("dest.writeInt(source.$L)", name);
                break;

            case INT:
                shipMethod.addStatement("dest.writeInt(source.$L)", name);
                break;

            case FLOAT:
                shipMethod.addStatement("dest.writeFloat(source.$L)", name);
                break;

            case LONG:
                shipMethod.addStatement("dest.writeLong(source.$L)", name);
                break;

            case DOUBLE:
                shipMethod.addStatement("dest.writeDouble(source.$L)", name);
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    protected boolean reveiveMethod(@NonNull MethodSpec.Builder receiveMethod, @NonNull Element element, @NonNull TypeKind typeKind) {
        String name = element.getSimpleName().toString();
        switch (typeKind) {
            case BOOLEAN:
                receiveMethod.addStatement("target.$L = in.readByte() != 0", name);
                break;

            case CHAR:
                receiveMethod.addStatement("target.$L = (char) in.readInt()", name);
                break;

            case BYTE:
                receiveMethod.addStatement("target.$L = in.readByte()", name);
                break;

            case SHORT:
                receiveMethod.addStatement("target.$L = (short) in.readInt()", name);
                break;

            case INT:
                receiveMethod.addStatement("target.$L = in.readInt()", name);
                break;

            case FLOAT:
                receiveMethod.addStatement("target.$L = in.readFloat()", name);
                break;

            case LONG:
                receiveMethod.addStatement("target.$L = in.readLong()", name);
                break;

            case DOUBLE:
                receiveMethod.addStatement("target.$L = in.readDouble()", name);
                break;

            default:
                return false;
        }
        return true;
    }

}
