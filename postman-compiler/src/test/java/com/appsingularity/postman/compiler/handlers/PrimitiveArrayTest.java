package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can process primitive arrays
 */
public class PrimitiveArrayTest {


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
                        "   boolean[] mBoolean;",
                        "   char[] mChar;",
                        "   byte[] mByte;",
                        "   short[] mShort;",
                        "   int[] mInt;",
                        "   long[] mLong;",
                        "   float[] mFloat;",
                        "   double[] mDouble;",
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
                        "      dest.writeBooleanArray(source.mBoolean);",
                        "      dest.writeCharArray(source.mChar);",
                        "      dest.writeByteArray(source.mByte);",
                        "      if (source.mShort != null) {",
                        "         dest.writeByte((byte) 1);",
                        "         int[] mShort_copy = new int[source.mShort.length];",
                        "         for (int i = 0; i < source.mShort.length; i++) {",
                        "           mShort_copy[i] = source.mShort[i];",
                        "         }",
                        "         dest.writeIntArray(mShort_copy);",
                        "      } else {",
                        "         dest.writeByte((byte) 0);",
                        "      }",
                        "      dest.writeIntArray(source.mInt);",
                        "      dest.writeLongArray(source.mLong);",
                        "      dest.writeFloatArray(source.mFloat);",
                        "      dest.writeDoubleArray(source.mDouble);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "      target.mBoolean = in.createBooleanArray();",
                        "      target.mChar = in.createCharArray();",
                        "      target.mByte = in.createByteArray();",
                        "      // Read mShort;",
                        "      if (in.readByte() == 1) {",
                        "        int[] mShort_copy = in.createIntArray();",
                        "        target.mShort = new short[mShort_copy.length];",
                        "        for (int i = 0; i < mShort_copy.length; i++) {",
                        "          target.mShort[i] = (short) mShort_copy[i];",
                        "        }",
                        "      }",
                        "      target.mInt = in.createIntArray();",
                        "      target.mLong = in.createLongArray();",
                        "      target.mFloat = in.createFloatArray();",
                        "      target.mDouble = in.createDoubleArray();",
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
