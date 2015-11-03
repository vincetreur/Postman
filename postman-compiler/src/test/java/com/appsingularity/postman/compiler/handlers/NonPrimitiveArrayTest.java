package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can process Non primitive arrays
 */
public class NonPrimitiveArrayTest {


    @Test
    public void testPrimitiveArrays() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   Boolean[] mBoolean;",
                        "   Character[] mChar;",
                        "   Byte[] mByte;",
                        "   Short[] mShort;",
                        "   Integer[] mInt;",
                        "   Long[] mLong;",
                        "   Float[] mFloat;",
                        "   Double[] mDouble;",
                        "",
                        "   protected Model(Parcel in) {",
                        "     Postman.receive(this, in);",
                        "  }",
                        "",
                        "  @Override",
                        "  public void writeToParcel(Parcel dest, int flags) {",
                        "    Postman.ship(this, dest, flags);",
                        "  }",
                        "",
                        "  @Override",
                        "  public int describeContents() {",
                        "    return 0;",
                        "  }",
                        "",
                        "  public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {",
                        "    @Override",
                        "    public Model createFromParcel(Parcel in) {",
                        "      return new Model(in);",
                        "    }",
                        "",
                        "    @Override",
                        "    public Model[] newArray(int size) {",
                        "      return new Model[size];",
                        "    }",
                        "  };",
                        "}"
                ));
        JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/Model$$Postman",
                Joiner.on('\n').join(
                        "// Generated code from Postman. Do not modify!",
                        "package test;",
                        "",
                        "import com.appsingularity.postman.internal.BasePostman;",
                        "import java.lang.Override;",
                        "",
                        "public final class Model$$Postman extends BasePostman<Model> {",
                        "   @Override",
                        "   public void ship(final Model source, final android.os.Parcel dest, int flags) {",
                        "      dest.writeValue(source.mBoolean);",
                        "      dest.writeValue(source.mChar);",
                        "      dest.writeValue(source.mByte);",
                        "      dest.writeValue(source.mShort);",
                        "      dest.writeValue(source.mInt);",
                        "      dest.writeValue(source.mLong);",
                        "      dest.writeValue(source.mFloat);",
                        "      dest.writeValue(source.mDouble);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "      target.mBoolean = (java.lang.Boolean[]) in.readValue(java.lang.Boolean[].class.getClassLoader());",
                        "      target.mChar = (java.lang.Character[]) in.readValue(java.lang.Character[].class.getClassLoader());",
                        "      target.mByte = (java.lang.Byte[]) in.readValue(java.lang.Byte[].class.getClassLoader());",
                        "      target.mShort = (java.lang.Short[]) in.readValue(java.lang.Short[].class.getClassLoader());",
                        "      target.mInt = (java.lang.Integer[]) in.readValue(java.lang.Integer[].class.getClassLoader());",
                        "      target.mLong = (java.lang.Long[]) in.readValue(java.lang.Long[].class.getClassLoader());",
                        "      target.mFloat = (java.lang.Float[]) in.readValue(java.lang.Float[].class.getClassLoader());",
                        "      target.mDouble = (java.lang.Double[]) in.readValue(java.lang.Double[].class.getClassLoader());",
                        "   }",
                        "}"
                ));


        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource);
    }



}
