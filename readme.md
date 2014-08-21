ARGB Color Picker Library For Android
=========
----------------------------------------------------------------------------------
A Simple aRGB color picker library for android, the color picker is a dialog fragment which allows a user to pick a color. 

![Error loading image](https://raw.githubusercontent.com/Jayen/RGBColorPickerLibrary/master/screenshots/screenshot.png)
Usage
--------------

To add librray in Android Studio:

* First import the library as a Module into project.
* Add the modules as a dependency in build.gradle

If you get the error:
```
Plugin with id 'com.adroid.library' not found
```
You need to add the build script to your build.gradle file
```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:0.12.2'
    }
}
```

Alternatively, using Android studio an AAR can be generated and then copied into the libs folder of the app. AAR will be in the build/outputs/aar/ directory when built.
To use the AAR you need to add it to the gradle build as:
```sh
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
```

License
----
MIT License (See License.txt)

