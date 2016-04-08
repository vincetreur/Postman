package com.appsingularity.postman.compiler;

import android.support.annotation.NonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.Diagnostic.Kind.WARNING;

public class Logger {
    @NonNull
    private final ProcessingEnvironment mEnv;

    public Logger(@NonNull final ProcessingEnvironment env) {
        mEnv = env;
    }

    public void error(final Element element, String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mEnv.getMessager().printMessage(ERROR, "Postman: " + message, element);
    }

    public void warn(final Element element, String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mEnv.getMessager().printMessage(WARNING, "Postman: " + message, element);
    }

    public void note(String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mEnv.getMessager().printMessage(NOTE, "Postman: " + message);
    }

    public void other(final Element element, String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        System.out.println("Postman: " + message);
    }
}
