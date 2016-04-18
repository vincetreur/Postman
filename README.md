Postman
============

Easy parceling for Android.

Generate the boilerplate code that is needed for supporting Parcelable.

Postman generates the code that you would normally write in the `writeToParcel` and constructor methods.
You only need to add the `@PostmanEnabled` annotation. And delegate to the `Postman` in your own code.

Your code is still your own, so you can add your own code around calls to Postman, just like when you would write the parceling code yourself.

###Third-party classes
If your models extend classes that are not Postman annotated but are `Parcelable` then you can still use Postman.
Just call your super class so it can do it's own parceling and let Postman do it's job after that.

```java
import android.os.Parcel;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class Model extends ParcelableThirdPartyClass  {
    // Add your methods and attributes here.

    protected Model(Parcel in) {
        super(in);
        Postman.receive(Model.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        Postman.ship(Model.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
```

### Execute code before or after parceling
When you want to run some code before or after parceling (maybe calculate some derived values) then you can do that easily.
Just insert your code around the calls to Postman. 

```java
import android.os.Parcel;
import android.os.Parcelable;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class Model implements Parcelable  {
    // Add your methods and attributes here.

    protected Model(Parcel in) {
        // Insert some code here if you want to
        Postman.receive(Model.class, this, in);
        // Insert some code here if you want to
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Insert some code here if you want to
        Postman.ship(Model.class, this, dest, flags);
        // Insert some code here if you want to
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
```


###ProGuard
Postman plays well with ProGuard. If you use the `postman`-module you don't need to worry about ProGuard & Postman.


###Good to know
-  Attributes must be `public`, `protected` or package protected. Private attributes cannot be accessed from the generated code and will not be parcelled.
- `static` attributes are ignored since they belong to the class not the instance.
- `final` attributes are ignored since Postman cannot detect if they are already initialized.
- You can also exclude an attribute by marking it as `transient`.
- Annotation only works on classes, not on interfaces.
- Any attributes that could not be processed are documented in the generated source. 
- `serialVersionUID` will be skipped silently.
- Static inner classes are also supported.

##Supported types
Right now Postman supports:

- `boolean`, `Boolean`, `boolean[]`, `Boolean[]`, `List<Boolean>` and `ArrayList<Boolean>`
- `char`, `Character`, `char[]`, `Character[]`, `List<Character>` and `ArrayList<Character>`
- `byte`, `Byte`, `byte[]`, `Byte[]`, `List<Byte>` and `ArrayList<Byte>`
- `short`, `Short`, `short[]`, `Short[]`, `List<Short>` and `ArrayList<Short>`
- `int`, `Integer`, `int[]`, `Integer[]`, `List<Integer>` and `ArrayList<Integer>`
- `long`, `Long`, `long[]`, `Long[]`, `List<Long>` and `ArrayList<Long>`
- `float`, `Float`, `float[]`, `Float[]`, `List<Float>` and `ArrayList<Float>`
- `double`, `Double`, `double[]`, `Double[]`, `List<Double>` and `ArrayList<Double>`
- `String`, `String[]`, `List<String>` and `ArrayList<String>`
- `Parcelable`, `Parcelable[]`, `List<Parcelable>` and `ArrayList<Parcelable>`
- `Serializable`, `List<Serializable>` and `ArrayList<Serializable>`
- `Bundle`, `Bundle[]`, `List<Bundle>` and `ArrayList<Bundle>`
- `SparseBooleanArray` and `SparseArray`
- `Map` and `HashMap`, for `Serializable`'s and `Parcelable`'s

`List` will always be returned as `ArrayList`.

`Map` will always be returned as `HashMap`.


For Lollipop and higher Postman also supports:

- `Size` and `SizeF`
- `PersistableBundle`


##How to use
Configure your `build.gradle`

```groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

apply plugin: 'com.neenbedankt.android-apt'

dependencies {
  compile 'com.appsingularity:postman:1.1.1'
  apt 'com.appsingularity:postman-compiler:1.1.1'
}
```

Annotate your model classes and delegate the tedious boilerplate coding to the Postman.

```java
import android.os.Parcel;
import android.os.Parcelable;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;

@PostmanEnabled
public class Model implements Parcelable  {
    // Add your methods and attributes here.

    protected Model(Parcel in) {
        Postman.receive(Model.class, this, in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Postman.ship(Model.class, this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
```

Don't forget to setup annotation processing in your `build.gradle`.
(See the sample project.)


##Yet to do
- Support more attribute types.
- More automated tests.

#Version

Version 1.0.0
- Initial release.

Version 1.0.1
- Fixed issue when used in combination with Dagger.

Version 1.1.0
- Added support for static inner classes.
- Improved type checking for lists and maps. This may break your build in some cases.
- Removed the need to specify the annotations package as dependency.
- Added more tests.
- Postman produces less console output, max 1 line per class.
- Complete logging is now written (as comments) into the generated class, so you can read it at any time.

Version 1.1.1
- Processing now also works for tests.

#License

    Copyright 2016 Vince M. Treur

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
