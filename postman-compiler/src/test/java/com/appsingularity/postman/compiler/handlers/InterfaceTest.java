package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can not process interfaces
 */
public class InterfaceTest {

    @Test
    public void testFailOnInterfaces() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public interface Model extends Parcelable {",
                        "",
                        "}"
                ));

        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .failsToCompile()
                .withErrorCount(1)
                .withErrorContaining("Interface 'Model' should not be annotated with @PostmanEnabled.");
    }

}
