package com.appsingularity.postman.compiler;

import android.support.annotation.NonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.WARNING;

public class Logger {
    @NonNull
    private final ProcessingEnvironment mEnv;

    public Logger(@NonNull final ProcessingEnvironment env) {
        mEnv = env;
    }

    public void error(@NonNull final Element element, @NonNull String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mEnv.getMessager().printMessage(ERROR, "Postman: " + message, element);
    }

    public void warn(@NonNull final Element element, @NonNull String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mEnv.getMessager().printMessage(WARNING, "Postman: " + message, element);
    }

    public void other(@NonNull final Element element, @NonNull String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        System.out.println("Postman: " + message);
    }

}
