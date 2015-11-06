package com.appsingularity.postman.compiler.writers.fields;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.writers.AbsCollectedFieldWriter;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

public class PrinitiveDataTypeFieldWriter extends AbsCollectedFieldWriter {

    public PrinitiveDataTypeFieldWriter(@NonNull Element element) {
        super(element);
    }

    @Override
    public boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod) {
        String name = mElement.getSimpleName().toString();
        switch (mElement.asType().getKind()) {
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
    public boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod) {
        String name = mElement.getSimpleName().toString();
        switch (mElement.asType().getKind()) {
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
