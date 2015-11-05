package com.appsingularity.postman.compiler.writers;


import android.support.annotation.NonNull;

import com.squareup.javapoet.MethodSpec;


public interface CollectedFieldWriter {

    boolean writeShipMethod(@NonNull MethodSpec.Builder shipMethod);

    boolean writeReveiveMethod(@NonNull MethodSpec.Builder receiveMethod);

}
