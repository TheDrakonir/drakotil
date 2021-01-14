[![](https://jitpack.io/v/TheDrakonir/drakotil.svg)](https://jitpack.io/#TheDrakonir/drakotil)

# drakotil
Drakotil is a java library containing many utility functions and classes to make development easier and faster.

## How to use:

### 1. Include the dependency in your project

#### Using gradle

1. Add the jitpack.io repository to your build.gradle
```gradle
repositories {
  maven { url 'https://jitpack.io' }
}
```
2. Add this project to your dependencies (use `master-SNAPSHOT` for the latest build or any specific version like `v1.0.0` for a specific version)
```gradle
dependencies {
  implementation 'com.github.TheDrakonir:drakotil:master-SNAPSHOT'
}
```

#### Using maven

1. Add the jitpack.io repository to your build file
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
2. Add this project to your dependencies (use `master-SNAPSHOT` for the latest build or any specific version like `v1.0.0` for a specific version)
```xml
<dependency>
  <groupId>com.github.TheDrakonir</groupId>
  <artifactId>drakotil</artifactId>
  <version>master-SNAPSHOT</version>
</dependency>
```

### 2. Use the given classes:

#### Logging (drakotil.logging)
You can get a reference to a logger by using `LoggerFactory.getLogger()`. 
This will return either a previous instanced logger or a new console logger per default. 
If you want to use a different kind of logger you can use their access method directly (e.g. `getConsoleLogger`, `getFileLogger`) in your main class to instantiate exactly this kind of logger to be reused in the future runtime of your program.
If you want to configure the output properties of the file logger you can use the `LoggerFactory.configureFileLogger(options)` method to customize it to your needs.

#### OS (drakotil.os.OS)
This class holds information about the running os and user as well as the java system used.
|method|result|
|-----|-----|
|`OS.getOS()`|returns a value of the `OS_type` enum (`WINDOWS`, `MAC_OS`, `LINUX` or `UNKNOWN`)|
|`OS.getVersion()`|returns the version number of the os|
|`OS.getJavaClassVersion()`|returns the java class version number of the installed java virtual machine|
|`OS.getUsername()`|returns the currently signed in user's name|
|`OS.getUserHome()`|return a path object to the home directory of the current user|
|`OS.getUserLanguage()`|returns the system language of the current user|

#### Enviroment (drakotil.os.Enviroment)
The enviroment class provides easy access to enviroment variables and .env file configurations.
Use `Enviroment.getValue(String key)` to get an Optional containing the value for the key or else an empty optional.
If you use `Enviroment.getValueOrElse(String key, String defaultValue)`, you will get either the value for the specified key or your default value from the second argument.

#### Stringifier (drakotil.serialization.Stringifier)
This class can be used to get a more insightful string representation of every object.
By using the `Stringifier.stringify(Object object)` method you will get a string of the object listing all its fields if no custom `toString()` method is specified.
This will even work recursively, if the `MAX_DEPTH` constant is increased (defaults to 1).
