package com.appsingularity.postman.compiler.writers;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.model.CollectedClass;
import com.appsingularity.postman.compiler.model.CollectedField;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class PostmenEnabledCollectedClassWriter implements CollectedClassWriter {
    private static final ClassName BASE_PARCELER = ClassName.get("com.appsingularity.postman.internal", "BasePostman");
    private static final TypeVariableName PARCEL_TYPE_NAME = TypeVariableName.get("android.os.Parcel");
    @NonNull private final CollectedClass mCollectedClass;

    public PostmenEnabledCollectedClassWriter(@NonNull CollectedClass collectedClass) {
        mCollectedClass = collectedClass;
    }


    /**
     * Get the type name as string, including the names of any classes it is enclosed in.
     * @param element The element we want the name of
     * @return The name
     */
    @NonNull
    private String getTypeNameAsString(@NonNull Element element) {
        Element enclosingElement = element.getEnclosingElement();
        if (enclosingElement.getKind() == ElementKind.CLASS) {
            return getTypeNameAsString(enclosingElement) + "." + element.getSimpleName().toString();
        }

        return element.getSimpleName().toString();
    }

    /**
     * Transform a type name (as string) into a filename.
     * @param name The type name
     * @return The filename representing the type
     */
    @NonNull
    private String asFileName(@NonNull String name) {
        return name.replace('.', '$');
    }

    @Override
    public void writeToFile(@NonNull Elements elements, @NonNull Filer filer) throws IOException {
        Element classElement = mCollectedClass.getClassElement();
        String typeNameAsString = getTypeNameAsString(classElement);
        TypeName typeName = TypeVariableName.get(typeNameAsString);
        List<CollectedField> fields = mCollectedClass.getFields();
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(asFileName(typeNameAsString) + "$$Postman")
                .addModifiers(PUBLIC, FINAL);
        classBuilder.superclass(ParameterizedTypeName.get(BASE_PARCELER, typeName));

        // Generate parcel method
        classBuilder.addMethod(createShipMethodSpec(typeName, fields));
        // Generate unparcel method
        classBuilder.addMethod(createReceiveMethodSpec(typeName, fields));

        String packageName = elements.getPackageOf(classElement).getQualifiedName().toString();
        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build())
                .addFileComment("Generated code from Postman. Do not modify!")
                .build();
        javaFile.writeTo(filer);
    }


    @NonNull
    private MethodSpec createShipMethodSpec(@NonNull final TypeName typeName
            , @NonNull final List<CollectedField> fields) {
        ParameterSpec paramsSpec = ParameterSpec.builder(TypeName.INT, "flags").build();
        MethodSpec.Builder shipMethodBuilder = MethodSpec.methodBuilder("ship")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "source", FINAL)
                .addParameter(PARCEL_TYPE_NAME, "dest", FINAL)
                .addParameter(paramsSpec);
        CollectedFieldWriter writer;
        for (CollectedField field : fields) {
            writer = field.getWriter();
            writer.writeShipMethod(shipMethodBuilder);
        }
        return shipMethodBuilder.build();
    }

    @NonNull
    private MethodSpec createReceiveMethodSpec(@NonNull final TypeName typeName
            , @NonNull List<CollectedField> fields) {
        MethodSpec.Builder receiveMethodBuilder = MethodSpec.methodBuilder("receive")
                .addModifiers(PUBLIC).addAnnotation(Override.class)
                .addParameter(typeName, "target", FINAL)
                .addParameter(PARCEL_TYPE_NAME, "in", FINAL);
        CollectedFieldWriter writer;
        for (CollectedField field : fields) {
            writer = field.getWriter();
            writer.writeReceiveMethod(receiveMethodBuilder);
        }
        return receiveMethodBuilder.build();
    }

}
