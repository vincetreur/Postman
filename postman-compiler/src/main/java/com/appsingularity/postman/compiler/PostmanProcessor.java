package com.appsingularity.postman.compiler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.handlers.AttributeHandler;
import com.appsingularity.postman.compiler.handlers.ParcelableArrayHandler;
import com.appsingularity.postman.compiler.handlers.primitives.CharPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.TypedArrayHandler;
import com.appsingularity.postman.compiler.handlers.primitives.BooleanPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.primitives.BytePrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.primitives.DoublePrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.primitives.FloatPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.GenericObjectHandler;
import com.appsingularity.postman.compiler.handlers.primitives.IntPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.BasicListHandler;
import com.appsingularity.postman.compiler.handlers.primitives.LongPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.ParcelableHandler;
import com.appsingularity.postman.compiler.handlers.SerializableHandler;
import com.appsingularity.postman.annotations.PostmanEnabled;
import com.appsingularity.postman.compiler.handlers.ShortPrimitiveArrayHandler;
import com.appsingularity.postman.compiler.handlers.primitives.ShortPrimitiveHandler;
import com.appsingularity.postman.compiler.handlers.StringListHandler;
import com.appsingularity.postman.compiler.handlers.TypedObjectHandler;
import com.appsingularity.postman.compiler.handlers.BasicArrayHandler;
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
import java.util.LinkedHashMap;
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
import javax.lang.model.util.Types;

import static javax.tools.Diagnostic.Kind.ERROR;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.tools.Diagnostic.Kind.OTHER;
import static javax.tools.Diagnostic.Kind.WARNING;


@AutoService(Processor.class)
public class PostmanProcessor extends AbstractProcessor {
    private Elements mElements;
    private Filer mFiler;

    private List<AttributeHandler> mAttributeHandlers;
    private int mAttributeHandlersSize;

    private static final ClassName BASE_PARCELER = ClassName.get("com.appsingularity.postman.internal", "BasePostman");
    private static final TypeVariableName PARCEL_TYPE_NAME = TypeVariableName.get("android.os.Parcel");

    @Override public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElements = env.getElementUtils();
        Types types = env.getTypeUtils();
        mFiler = env.getFiler();

        mAttributeHandlers = new ArrayList<>();
        mAttributeHandlers.add(new BooleanPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new CharPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new ShortPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new BytePrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new IntPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new FloatPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new LongPrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new DoublePrimitiveHandler(types, mElements));
        mAttributeHandlers.add(new GenericObjectHandler(types, mElements));

        mAttributeHandlers.add(new TypedObjectHandler(types, mElements, "java.lang.String", "String"));
        mAttributeHandlers.add(new StringListHandler(types, mElements));

        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "boolean[]", "Boolean"));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "char[]", "Char"));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "byte[]", "Byte"));
        mAttributeHandlers.add(new ShortPrimitiveArrayHandler(types, mElements));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "int[]", "Int"));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "float[]", "Float"));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "long[]", "Long"));
        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "double[]", "Double"));

        mAttributeHandlers.add(new TypedArrayHandler(types, mElements, "java.lang.String[]", "String"));
        mAttributeHandlers.add(new ParcelableArrayHandler(types, mElements));

        // Lollipop+
        mAttributeHandlers.add(new TypedObjectHandler(types, mElements, "android.util.Size", "Size"));
        mAttributeHandlers.add(new TypedObjectHandler(types, mElements, "android.util.SizeF", "SizeF"));

        mAttributeHandlers.add(new BasicListHandler(types, mElements));
        // Process any sort of array with native types, such as Integer, Short and Byte. But not Object/Serializable/etc
        mAttributeHandlers.add(new BasicArrayHandler(types, mElements));

        // Last resort handlers
        mAttributeHandlers.add(new ParcelableHandler(types, mElements));
        mAttributeHandlers.add(new SerializableHandler(types, mElements));

        mAttributeHandlersSize = mAttributeHandlers.size();
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
        Map<Element, Map<Element, AttributeHandler>> collectedElements = new HashMap<>();
        Map<Element, AttributeHandler> attributes;
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(PostmanEnabled.class)) {
            // Filter out everything but the classes
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                warn("Annotated type " + annotatedElement + " Is not a class!");
                continue;
            }

            attributes = new LinkedHashMap<>();
            collectedElements.put(annotatedElement, attributes);

            List<? extends Element> children = annotatedElement.getEnclosedElements();
            for (Element child : children) {
                if (!isProcessableAttribute(child)) {
                    continue;
                }
                TypeMirror typeMirror = child.asType();
                TypeKind typeKind = typeMirror.getKind();
                AttributeHandler handler = findHandlerForTypeKind(child, typeKind);
                if (handler != null) {
                    attributes.put(child, handler);
                }
            }
        }
        // Generate code
        TypeVariableName simpleClassName;
        TypeSpec.Builder classBuilder;
        Map<Element, AttributeHandler> attributeList;
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

                packageName = mElements.getPackageOf(collectedClass).getQualifiedName().toString();
                javaFile = JavaFile.builder(packageName, classBuilder.build())
                        .addFileComment("Generated code from Postman. Do not modify!")
                        .build();
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                error(collectedClass, "Unable to write code for type %s: %s", collectedClass,
                        e.getMessage());
            }
        }
        return false;
    }

    @NonNull
    private MethodSpec createShipMethodSpec(@NonNull final TypeVariableName typeName
            , @NonNull final TypeVariableName parcelTypeName, @NonNull final Map<Element, AttributeHandler> attributes) {
        ParameterSpec paramsSpec = ParameterSpec.builder(TypeName.INT, "flags").build();
        MethodSpec.Builder shipMethodBuilder = MethodSpec.methodBuilder("ship")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "source", FINAL)
                .addParameter(parcelTypeName, "dest", FINAL)
                .addParameter(paramsSpec);
        AttributeHandler handler;
        TypeMirror typeMirror;
        for (Element attribute : attributes.keySet()) {
            handler = attributes.get(attribute);
            typeMirror = attribute.asType();
            handler.addShipMethod(shipMethodBuilder, attribute, typeMirror.getKind());
        }
        return shipMethodBuilder.build();
    }

    @NonNull
    private MethodSpec createReceiveMethodSpec(@NonNull final TypeVariableName typeName
            , @NonNull final TypeVariableName parcelTypeName, @NonNull final Map<Element, AttributeHandler> attributes) {
        MethodSpec.Builder receiveMethodBuilder = MethodSpec.methodBuilder("receive")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "target", FINAL)
                .addParameter(parcelTypeName, "in", FINAL);
        AttributeHandler handler;
        TypeMirror typeMirror;
        for (Element attribute : attributes.keySet()) {
            handler = attributes.get(attribute);
            typeMirror = attribute.asType();
            handler.addReveiveMethod(receiveMethodBuilder, attribute, typeMirror.getKind());
        }
        return receiveMethodBuilder.build();
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

    @Nullable
    private AttributeHandler findHandlerForTypeKind(@NonNull Element element, @NonNull TypeKind typeKind) {
        for (int i = 0; i < mAttributeHandlersSize; i++) {
            AttributeHandler handler = mAttributeHandlers.get(i);
            if (handler.isProcessableTypeKind(element, typeKind)) {
                return handler;
            }
        }
        warn("Attribute " + element + " of type " + element.asType().toString() + "/" + typeKind + " is not supported yet!");
        return null;
    }

}
