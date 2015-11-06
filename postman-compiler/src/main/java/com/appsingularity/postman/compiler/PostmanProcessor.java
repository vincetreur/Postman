package com.appsingularity.postman.compiler;


import com.appsingularity.postman.annotations.PostmanEnabled;
import com.appsingularity.postman.compiler.model.CollectedClass;
import com.appsingularity.postman.compiler.model.CollectedClasses;
import com.appsingularity.postman.compiler.writers.CollectedClassWriter;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.tools.Diagnostic.Kind.ERROR;

import static javax.tools.Diagnostic.Kind.OTHER;
import static javax.tools.Diagnostic.Kind.WARNING;


@AutoService(Processor.class)
public class PostmanProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types mTypes;
    private Filer mFiler;

    @Override public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElements = env.getElementUtils();
        mTypes = env.getTypeUtils();
        mFiler = env.getFiler();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();

        types.add(PostmanEnabled.class.getCanonicalName());

        return types;
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Here is where we store all attributes per class that we need to generate code for.
        List<CollectedClass> collectedClasses = new ArrayList<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(PostmanEnabled.class)) {
            // Filter out everything but the classes
            CollectedClass collectedClass = CollectedClasses.obtain(mTypes, mElements, annotatedElement);
            if (collectedClass != null) {
                collectedClasses.add(collectedClass);
            }
        }
        // Generate code
        for (CollectedClass annotatedClass : collectedClasses) {
            note("Processing type %s", annotatedClass.toString());
            try {
                CollectedClassWriter writer = annotatedClass.getWriter();
                writer.writeToFile(mElements, mFiler);
            } catch (IOException e) {
                error(annotatedClass.getClassElement(), "Unable to write code for type %s: %s", annotatedClass,
                        e.getMessage());
            }
        }
        return false;
    }


    private void error(final Element element, String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(ERROR, "Postman: " + message, element);
    }

    private void warn(String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(WARNING, "Postman: " + message);
    }

    private void note(String message, final Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(OTHER, "Postman: " + message);
    }

}
