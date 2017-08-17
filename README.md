# SimpleLocationGetter
Very simple way to get last known location.

This library is just a wrap on newly released Location API: https://android-developers.googleblog.com/2017/06/reduce-friction-with-new-location-apis.html

# Getting started

This library will perfectly fit your existing build.gradle files:
```gradle
//Project build.gradle
allprojects {
    repositories {
        jcenter()
        maven {
            url  "http://dl.bintray.com/skullper/maven"
        }
    }
}
//app(module) build.gradle
compile 'com.github.skullper:locationgetter:0.1@arr'
compile 'com.google.android.gms:play-services-location:11.0.1'
```

Then you just need to add this two lines of code to get last known location:

```java
SimpleLocationGetter getter = new SimpleLocationGetter(this, this);
getter.getLastLocation();
```

Oh, and don't forget to implement this awesome well written listener to class where the previous strings are declared:

```java
implements SimpleLocationGetter.OnLocationGetListener
...
@Override
public void onLocationReady(Location location){
	Log.d("LOCATION", "onLocationReady: lat="+location.getLatitude() + " lon="+location.getLongitude());
}

@Override
public void onError(String error){
	Log.e("LOCATION", "Error: "+error);
}
```
As you probably see in ```onLocationReady()``` method you'll get your location and if API cannot get location or you just forgot to turn on your GPS you'll get a exact description in ```onError()``` method

And that's all folks!

Nope, also you need to check permissions by yourself. Sorry for this, but it's a ```SIMPLE``` location getter.

Good luck|have fun
