package com.appsingularity.postman.compiler.handlers;

import com.appsingularity.postman.compiler.PostmanProcessor;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Make sure we can process List
 */
public class ListTest {

    @Test
    public void testRawList() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.List;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   List mList;",
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
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
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
    public void testListWithLegalType() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.List;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   List<Integer> mList;",
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
                        "     // Writing source.mList;",
                        "     if (source.mList != null) {",
                        "       dest.writeByte((byte) 1);",
                        "       dest.writeList(source.mList);",
                        "     } else {",
                        "       dest.writeByte((byte) 0);",
                        "     }",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "     // Reading target.mList;",
                        "     if (in.readByte() == 1) {",
                        "       target.mList = new ArrayList<>();",
                        "       in.readList(target.mList, java.lang.Integer.class.getClassLoader());",
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
    public void testListWithStringType() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.List;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   List<String> mList;",
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
                        "     dest.writeStringList(source.mList);",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "      target.mList = in.createStringArrayList();",
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
    public void testListWithIllegalType() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.Model",
                Joiner.on('\n').join(
                        "package test;",
                        "",
                        "import com.appsingularity.postman.Postman;",
                        "import com.appsingularity.postman.annotations.PostmanEnabled;",
                        "import java.util.List;",
                        "import android.os.Parcel;",
                        "import android.os.Parcelable;",
                        "",
                        "@PostmanEnabled",
                        "public class Model implements Parcelable {",
                        "   List<android.view.View> mList;",
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
                        "     // mList: Collection holds unsupported type 'android.view.View'",
                        "   }",
                        "",
                        "   @Override",
                        "   public void receive(final Model target, final android.os.Parcel in) {",
                        "     // mList: Collection holds unsupported type 'android.view.View'",
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
