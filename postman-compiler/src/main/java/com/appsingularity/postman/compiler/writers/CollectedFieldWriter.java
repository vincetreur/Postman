package com.appsingularity.postman.compiler.writers;


import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;


public interface CollectedFieldWriter {

    void writeShipMethod(@NonNull MethodSpec.Builder shipMethod);

    void writeReceiveMethod(@NonNull MethodSpec.Builder receiveMethod);

}
