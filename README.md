# Discord Webhook Interface for Java

DWIJ makes sending custom webhook messages to Discord simple and painless.

### Features

* Customize every aspect of your messages, including username, content, avatar, embeds, links, and more!
* Small size allowing for seamless integration with your project

# Importing

### Maven

* Include JitPack in your maven build file

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

* Add DWIJ as a dependency

```xml

<dependency>
    <groupId>com.github.7orivorian</groupId>
    <artifactId>DWIJ</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

* Add JitPack to your root `build.gradle` at the end of repositories

```gradle
repositories {
    maven {
        url 'https://jitpack.io'
    }
}
```

* Add the dependency

```gradle
dependencies {
    implementation 'com.github.7orivorian:DWIJ:1.0.0'
}
```

### Other

Use a `.jar` file from [releases](https://github.com/7orivorian/DWIJ/releases/tag/1.0.0)

# Building

* Clone this repository
* Run `mvn package`

Packaged file can be found in the `target/` directory.

Created and maintained by [7orivorian](https://github.com/7orivorian)