package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can process Bundles
 */
public class BundleTest {


    @Test
    public void testBundle() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import android.os.Bundle;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "import com.appsingularity.postman.compiler.handlers.MySerializable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   Bundle mBundle;",
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
                        "      dest.writeValue(source.mBundle);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "      target.mBundle = (android.os.Bundle) in.readValue(android.os.Bundle.class.getClassLoader());",
                        "   }",
                        "}"
                ));


        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource);
    }


    @Test
    public void testBundleArray() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import android.os.Bundle;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "import com.appsingularity.postman.compiler.handlers.MySerializable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   Bundle[] mBundles;",
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
                        "      dest.writeParcelableArray(source.mBundles, flags);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "     android.os.Parcelable[] mBundlesCopy = in.readParcelableArray(android.os.Bundle.class.getClassLoader());",
                        "     if (mBundlesCopy != null) {",
                        "       target.mBundles = java.util.Arrays.copyOf(mBundlesCopy, mBundlesCopy.length, android.os.Bundle[].class);",
                        "     }",
                        "   }",
                        "}"
                ));


        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource);
    }

    @Test
    public void testBundleList() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import com.appsingularity.postman.compiler.handlers.MySerializable;",
                        "import android.os.Bundle;",
                        "import java.util.List;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   List<Bundle> mBundles;",
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
                        "}"
                ));
        JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/Model$$Postman",
                Joiner.on('\n').join(
                        "// Generated code from Postman. Do not modify!",
                        "package test;",
                        "",
                        "import com.appsingularity.postman.internal.BasePostman;",
                        "import java.lang.Override;",
                        "import java.util.ArrayList;",
                        "",
                        "public final class Model$$Postman extends BasePostman<Model> {",
                        "   @Override",
                        "   public void ship(final Model source, final android.os.Parcel dest, int flags) {",
                        "     if (source.mBundles != null) {",
                        "       dest.writeByte((byte) 1);",
                        "       dest.writeList(source.mBundles);",
                        "     } else {",
                        "       dest.writeByte((byte) 0);",
                        "     }",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "     if (in.readByte() == 1) {",
                        "       target.mBundles = new ArrayList<>();",
                        "       in.readList(target.mBundles, android.os.Bundle.class.getClassLoader());",
                        "     }",
                        "   }",
                        "}"
                ));


        assertAbout(javaSource()).that(source)
                .processedWith(new PostmanProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(expectedSource);
    }

    @Test
    public void testBundleArrayList() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import com.appsingularity.postman.compiler.handlers.MySerializable;",
                        "import android.os.Bundle;",
                        "import java.util.ArrayList;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   ArrayList<Bundle> mBundles;",
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
                        "}"
                ));
        JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/Model$$Postman",
                Joiner.on('\n').join(
                        "// Generated code from Postman. Do not modify!",
                        "package test;",
                        "",
                        "import com.appsingularity.postman.internal.BasePostman;",
                        "import java.lang.Override;",
                        "import java.util.ArrayList;",
                        "",
                        "public final class Model$$Postman extends BasePostman<Model> {",
                        "   @Override",
                        "   public void ship(final Model source, final android.os.Parcel dest, int flags) {",
                        "     if (source.mBundles != null) {",
                        "       dest.writeByte((byte) 1);",
                        "       dest.writeList(source.mBundles);",
                        "     } else {",
                        "       dest.writeByte((byte) 0);",
                        "     }",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "     if (in.readByte() == 1) {",
                        "       target.mBundles = new ArrayList<>();",
                        "       in.readList(target.mBundles, android.os.Bundle.class.getClassLoader());",
                        "     }",
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
