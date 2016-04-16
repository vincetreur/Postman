package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can process static inner classes
 */
public class InnerClassTest {

    @Test
    public void testFailOnNonStaticInnerClass() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.ArrayList;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   String mString;",
                        "",
                        "   protected Model(Parcel in) {",
                        "     Postman.receive(Model.class, this, in);",
                        "  }",
                        "",
                        "  @Override",
                        "  public void writeToParcel(Parcel dest, int flags) {",
                        "    Postman.ship(Model.class, this, dest, flags);",
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
                        "",
                        "  @PostmanEnabled",
                        "  abstract class Inner implements Parcelable {",
                        "    protected Inner(Parcel in) {",
                        "      Postman.receive(Inner.class, this, in);",
                        "    }",
                        "",
                        "    @Override",
                        "    public void writeToParcel(Parcel dest, int flags) {",
                        "      Postman.ship(Inner.class, this, dest, flags);",
                        "    }",
                        "",
                        "    @Override",
                        "    public int describeContents() {",
                        "      return 0;",
                        "    }",
                        "",
                        "  }",
                        "",
                        "}"
                ));

        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .failsToCompile()
                .withErrorCount(1)
                .withErrorContaining("Class 'Inner' is an inner class of 'Model' and is annotated with @PostmanEnabled, but is not static.");
    }

    @Test
    public void testStaticInnerClass() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.ArrayList;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   String mString;",
                        "",
                        "   protected Model(Parcel in) {",
                        "     Postman.receive(Model.class, this, in);",
                        "  }",
                        "",
                        "  @Override",
                        "  public void writeToParcel(Parcel dest, int flags) {",
                        "    Postman.ship(Model.class, this, dest, flags);",
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
                        "",
                        "  @PostmanEnabled",
                        "  static class Inner implements Parcelable {",
                        "    String mInnerString;",
                        "",
                        "    protected Inner(Parcel in) {",
                        "      Postman.receive(Inner.class, this, in);",
                        "    }",
                        "",
                        "    @Override",
                        "    public void writeToParcel(Parcel dest, int flags) {",
                        "      Postman.ship(Inner.class, this, dest, flags);",
                        "    }",
                        "",
                        "    @Override",
                        "    public int describeContents() {",
                        "      return 0;",
                        "    }",
                        "",
                        "    public static final Parcelable.Creator<Inner> CREATOR = new Parcelable.Creator<Inner>() {",
                        "      @Override",
                        "      public Inner createFromParcel(Parcel in) {",
                        "        return new Inner(in);",
                        "      }",
                        "",
                        "      @Override",
                        "      public Inner[] newArray(int size) {",
                        "        return new Inner[size];",
                        "      }",
                        "    };",
                        "  }",
                        "",
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
                        "      dest.writeString(source.mString);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "      target.mString = in.readString();",
                        "   }",
                        "}"
                ));
        JavaFileObject expectedSourceInnerClass = JavaFileObjects.forSourceString("test/Model$Inner$$Postman",
                Joiner.on('\n').join(
                        "// Generated code from Postman. Do not modify!",
                        "package test;",
                        "",
                        "import com.appsingularity.postman.internal.BasePostman;",
                        "import java.lang.Override;",
                        "",
                        "public final class Model$Inner$$Postman extends BasePostman<Model.Inner> {",
                        "   @Override",
                        "   public void ship(final Model.Inner source, final android.os.Parcel dest, int flags) {",
                        "      dest.writeString(source.mInnerString);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model.Inner target, final android.os.Parcel in) {",
                        "      target.mInnerString = in.readString();",
                        "   }",
                        "}"
                ));
        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource, expectedSourceInnerClass);
    }

}
