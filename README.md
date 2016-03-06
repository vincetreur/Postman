Postman
============

Easy parceling for Android.

Generate the boilerplate code that is needed for supporting Parcelable.

Postman generates the code that you would normally write in the ```writeToParcel``` and constructor methods. 
You only need to add the ```@PostmanEnabled``` annotation. And delegate to the ```Postman``` in your own code.

###Third-party classes
You can go all in with Postman, or mix it with hand crafted ```Parcelable``` classes.
It can even handle third-party classes that are not designed to work with Postman. (See the sample project for some examples.)

###ProGuard
Postman plays well with ProGuard. If you use the ```postman```-module you don't need to worry about ProGuard & Postman.


###Good to know
-  Attributes must be ```public```, ```protected``` or package protected. Private attributes cannot be accessed from the generated code and will not be parcelled.
- ```static``` attributes are ignored since they belong to the class not the instance.
- You can also exclude an attribute by marking it as ```transient```.
- Annotation only works on classes, not on interfaces.

##Supported types
Right now Postman supports:

- ```boolean```, ```Boolean```, ```boolean[]```, ```Boolean[]```, ```List<Boolean>``` and ```ArrayList<Boolean>```
- ```char```, ```Character```, ```char[]```, ```Character[]```, ```List<Character>``` and ```ArrayList<Character>```
- ```byte```, ```Byte```, ```byte[]```, ```Byte[]```, ```List<Byte>``` and ```ArrayList<Byte>```
- ```short```, ```Short```, ```short[]```, ```Short[]```, ```List<Short>``` and ```ArrayList<Short>```
- ```int```, ```Integer```, ```int[]```, ```Integer[]```, ```List<Integer>``` and ```ArrayList<Integer>```
- ```long```, ```Long```, ```long[]```, ```Long[]```, ```List<Long>``` and ```ArrayList<Long>```
- ```float```, ```Float```, ```float[]```, ```Float[]```, ```List<Float>``` and ```ArrayList<Float>```
- ```double```, ```Double```, ```double[]```, ```Double[]```, ```List<Double>``` and ```ArrayList<Double>```
- ```String```, ```String[]```, ```List<String>``` and ```ArrayList<String>```
- ```Parcelable```, ```Parcelable[]```, ```List<Parcelable>``` and ```ArrayList<Parcelable>```
- ```Serializable```, ```List<Serializable>``` and ```ArrayList<Serializable>```
- ```Bundle```, ```Bundle[]```, ```List<Bundle>``` and ```ArrayList<Bundle>```
- ```SparseBooleanArray``` and ```SparseArray```
- ```Map``` and ```HashMap```, for ```Serializable```'s and ```Parcelable```'s

```List``` will always be returned as ```ArrayList```.

```Map``` will always be returned as ```HashMap```.


For Lollipop and higher Postman also supports:

- ```Size``` and ```SizeF```
- ```PersistableBundle```


##How to use
Configure your ```build.gradle```

```groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

apply plugin: 'com.neenbedankt.android-apt'

dependencies {
  compile 'com.appsingularity:postman:1.0.1'
  compile 'com.appsingularity:postman-annotations:1.0.1'
  apt 'com.appsingularity:postman-compiler:1.0.1'
}
```

Annotate your model classes and delegate the tedious boilerplate coding to the Postman.

```java
import android.os.Parcel;

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

Don't forget to setup annotation processing in your ```build.gradle```.
(See the sample project.)


##Yet to do
- Support more attribute types.
- Support for user specific types.
- More automated tests.

#Version

1.0.0 - Initial release

1.0.1 - Fixed issue when used in combination with Dagger

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



