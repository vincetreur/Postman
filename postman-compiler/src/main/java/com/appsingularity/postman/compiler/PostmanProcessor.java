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

@AutoService(Processor.class)
public class PostmanProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types mTypes;
    private Filer mFiler;
    private Logger mLogger;

    @Override public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mLogger = new Logger(env);
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
            CollectedClass collectedClass = CollectedClasses.obtain(mLogger, mTypes, mElements, annotatedElement);
            if (collectedClass != null) {
                collectedClasses.add(collectedClass);
            }
        }
        // Generate code
        for (CollectedClass annotatedClass : collectedClasses) {
//            mLogger.other("Processing type %s", annotatedClass.toString());
            try {
                CollectedClassWriter writer = annotatedClass.getWriter();
                writer.writeToFile(mElements, mFiler);
            } catch (IOException e) {
                mLogger.error(annotatedClass.getClassElement(), "Unable to write code for type %s: %s", annotatedClass,
                        e.getMessage());
            }
        }
        return true;
    }


}
