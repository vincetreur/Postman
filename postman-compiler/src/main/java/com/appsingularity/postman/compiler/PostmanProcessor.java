package com.appsingularity.postman.compiler;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.handlers.AttributeHandler;
import com.appsingularity.postman.compiler.handlers.BooleanPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.BytePrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.DoublePrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.FloatPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.GenericObjectHandler;
import com.appsingularity.postman.compiler.handlers.IntPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.LongPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.ParcelableHandler;
import com.appsingularity.postman.compiler.handlers.SerializableHandler;
import com.appsingularity.postman.compiler.handlers.StringHandler;
import com.appsingularity.postman.annotations.PostmanEnabled;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import static javax.tools.Diagnostic.Kind.ERROR;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.tools.Diagnostic.Kind.OTHER;
import static javax.tools.Diagnostic.Kind.WARNING;


@AutoService(Processor.class)
public class PostmanProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Filer filer;

    private List<AttributeHandler> mAttributeHandlers;
    private int mAttributeHandlersSize;

    private static final ClassName BASE_PARCELER = ClassName.get("com.appsingularity.postman.internal", "BasePostman");
    private static final TypeVariableName PARCEL_TYPE_NAME = TypeVariableName.get("android.os.Parcel");

    @Override public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();

        mAttributeHandlers = new ArrayList<>();
        mAttributeHandlers.add(new BooleanPrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Boolean"));

        mAttributeHandlers.add(new BytePrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Byte"));

        mAttributeHandlers.add(new IntPrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Integer"));

        mAttributeHandlers.add(new LongPrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Long"));

        mAttributeHandlers.add(new DoublePrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Double"));

        mAttributeHandlers.add(new FloatPrimitiveHandler());
        mAttributeHandlers.add(new GenericObjectHandler("java.lang.Float"));

        mAttributeHandlers.add(new StringHandler());
        mAttributeHandlers.add(new ParcelableHandler());
        mAttributeHandlers.add(new SerializableHandler());
        mAttributeHandlers.add(new GenericObjectHandler("android.os.Bundle"));

        mAttributeHandlersSize = mAttributeHandlers.size();
        // TODO: Add Binders
        // TODO: Add FileDescriptor
        // TODO: Add untyped arrays
        // TODO: Add typed arrays

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
        Map<Element, List<Element>> collectedElements = new HashMap<>();
        List<Element> attributes;
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(PostmanEnabled.class)) {
            // Filter out everything but the classes
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                warn("Annotated type " + annotatedElement + " Is not a class!");
                continue;
            }

            attributes = new ArrayList<>();
            collectedElements.put(annotatedElement, attributes);

            List<? extends Element> children = annotatedElement.getEnclosedElements();
            for (Element child : children) {
                if (!isProcessableAttribute(child)) {
                    continue;
                }
                TypeMirror typeMirror = child.asType();
                TypeKind typeKind = typeMirror.getKind();
                if (!isProcessableTypeKind(child, typeKind)) {
                    continue;
                }
                attributes.add(child);
            }
        }
        // Generate code
        TypeVariableName simpleClassName;
        TypeSpec.Builder classBuilder;
        List<Element> attributeList;
        String packageName;
        JavaFile javaFile;
        for (Element collectedClass : collectedElements.keySet()) {
            note("Processing type %s", collectedClass.toString());
            try {
                simpleClassName = TypeVariableName.get(collectedClass.getSimpleName().toString());
                classBuilder = TypeSpec.classBuilder(collectedClass.getSimpleName() + "$$Postman")
                        .addModifiers(PUBLIC, FINAL);
                classBuilder.superclass(ParameterizedTypeName.get(BASE_PARCELER, simpleClassName));

                attributeList = collectedElements.get(collectedClass);
                // Generate parcel method
                classBuilder.addMethod(createShipMethodSpec(simpleClassName, PARCEL_TYPE_NAME, attributeList));
                // Generate unparcel method
                classBuilder.addMethod(createReceiveMethodSpec(simpleClassName, PARCEL_TYPE_NAME, attributeList));

                packageName = elementUtils.getPackageOf(collectedClass).getQualifiedName().toString();
                javaFile = JavaFile.builder(packageName, classBuilder.build())
                        .addFileComment("Generated code from Postman. Do not modify!")
                        .build();
                javaFile.writeTo(filer);
            } catch (IOException e) {
                error(collectedClass, "Unable to write code for type %s: %s", collectedClass,
                        e.getMessage());
            }
        }
        return false;
    }

    @NonNull
    private MethodSpec createShipMethodSpec(@NonNull final TypeVariableName typeName
            , @NonNull final TypeVariableName parcelTypeName, @NonNull final List<Element> attributes) {
        ParameterSpec paramsSpec = ParameterSpec.builder(TypeName.INT, "flags").build();
        MethodSpec.Builder pareldMethod = MethodSpec.methodBuilder("ship")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "source", FINAL)
                .addParameter(parcelTypeName, "dest", FINAL)
                .addParameter(paramsSpec);
        for (Element attribute : attributes) {
            TypeMirror typeMirror = attribute.asType();
            TypeKind typeKind = typeMirror.getKind();
            for (int i = 0; i < mAttributeHandlersSize; i++) {
                AttributeHandler handler = mAttributeHandlers.get(i);
                if (handler.addShipMethod(pareldMethod, attribute, typeKind)) {
                    break;
                }
            }
        }
        return pareldMethod.build();
    }

    @NonNull
    private MethodSpec createReceiveMethodSpec(@NonNull final TypeVariableName typeName
            , @NonNull final TypeVariableName parcelTypeName, @NonNull final List<Element> attributes) {
        MethodSpec.Builder unpareldMethod = MethodSpec.methodBuilder("receive")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "target", FINAL)
                .addParameter(parcelTypeName, "in", FINAL);
        for (Element attribute : attributes) {
            TypeMirror typeMirror = attribute.asType();
            TypeKind typeKind = typeMirror.getKind();
            for (int i = 0; i < mAttributeHandlersSize; i++) {
                AttributeHandler handler = mAttributeHandlers.get(i);
                if (handler.addReveiveMethod(unpareldMethod, attribute, typeKind)) {
                    break;
                }
            }
        }
        return unpareldMethod.build();
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

    private boolean isProcessableAttribute(@NonNull Element element) {
        // Filter out everything but attributes
        if (element.getKind() != ElementKind.FIELD) {
            return false;
        }
        // Filter out private attributes
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            return false;
        }
        // Filter out static attributes
        if (element.getModifiers().contains(Modifier.STATIC)) {
            return false;
        }
        // Filter out transient attributes
        if (element.getModifiers().contains(Modifier.TRANSIENT)) {
            return false;
        }
        return true;
    }

    private boolean isProcessableTypeKind(@NonNull Element element, @NonNull TypeKind typeKind) {
        for (int i = 0; i < mAttributeHandlersSize; i++) {
            AttributeHandler handler = mAttributeHandlers.get(i);
            if (handler.isProcessableTypeKind(element, typeKind)) {
                return true;
            }
        }
        warn("Attribute " + element + " of type " + typeKind + " is not supported yet!");
        return false;
    }

}
