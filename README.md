Postman
============

Automated Parceling for Android.

Generate the boilerplate code that is needed for supporting Parcelable.

Postman generates the code that you would normally write in the ```writeToParcel``` and constructor methods. 
You only need to add the ```@PostmanEnabled``` annotation. And delegate to the ```Postman``` in your own code.

###Third-party classes
You can go all in with Postman, or mix it with hand crafted ```Parcelable``` classes.
Inheritance is not a problem for Postman and it can even handle third-prty classes that are not designed to work with Postman. (See the sample project for some examples.)

###ProGuard
Postman plays well with ProGuard. If you use the ```postman```-module you don't need to worry about ProGuard & Postman.


###Good to know
-  Attributes must be ```public```, ```protected``` or package protected. Private attributes cannot be accessed from the generated code and will not be parcelled.
- ```static``` attributes are ignored since they belong to the class not the instance.
- If you want to exclude an attribute mark it as ```transient```. 
- Annotation only works on classes, not on interfaces.

##Supported types
Right now Postman supports:

- ```boolean```, ```byte```, ```int```, ```long```, ```float``` and ```double```.
- ```Boolean```, ```Byte```, ```Integer```, ```Long```, ```Float``` and ```Double```.
- ``` boolean[]```, ```char[]```, ```byte[]```, ```int[]```, ```long[]```, ```float[]``` and ```double[]```.
- ```String```, ```Parcelable```, ```Serializable``` and ```Bundle```.
- ```String[]```.


##How to use
Annotate your model classes and delegate the tedious boilerplate coding to the Postman.

```java
import android.os.Parcel;

import com.appsingularity.postman.Postman;
import com.appsingularity.postman.annotations.PostmanEnabled;


public class Dog implements Parcelable  {
	// Add your methods and attributes here.
	
	protected Dog(Parcel in) {
		Postman.receive(Dog.class, this, in);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Postman.ship(Dog.class, this, dest, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Dog> CREATOR = new Creator<Dog>() {
		@Override
		public Dog createFromParcel(Parcel in) {
			return new Dog(in);
		}

		@Override
		public Dog[] newArray(int size) {
			return new Dog[size];
		}
	};
}
```

Don't foget to setup annotation processing in your ```build.gradle```.
(See the sample project.)


##Yet to do
- Support more attribute types, such as
	- Typed arrays
	- Untyped arrays
	- Binders
	- FileDescriptor
- More automated tests.

#License

    Copyright 2015 Vince M. Treur

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



