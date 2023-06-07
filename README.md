# Discord Webhook Interface for Java

![GitHub all releases](https://img.shields.io/github/downloads/7orivorian/DWIJ/total?style=flat-square)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/7orivorian/DWIJ?style=flat-square)
[![](https://jitci.com/gh/7orivorian/DWIJ/svg)](https://jitci.com/gh/7orivorian/DWIJ)

DWIJ makes sending custom webhook messages to Discord simple and painless.

### Features

* Customize every aspect of your messages, including username, content, avatar, embeds, links, and more!
* Small size allows for seamless integration with your project <3

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
    <version>1.0.1</version>
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
    implementation 'com.github.7orivorian:DWIJ:1.0.1'
}
```

### Other

Use a `.jar` file from [releases](https://github.com/7orivorian/DWIJ/releases/tag/1.0.1)

# Building

* Clone this repository
* Run `mvn package`

Packaged jar file can be found in the `target/` directory.

# Usage

```java
// Define urls to keep your code pretty ;P
String webhookUrl = "<your_webhook_url_here>";
String avatarUrl = "<your_avatar_url_here>";

// Construct your fantastic message!
WebhookMessage message = new WebhookMessage(webhookUrl)
        .withAvatar(avatarUrl)
        .withUsername("Happy boi")
        .withContent("Hello world!")
        .withTts(false)
        .withEmbed(
                new Embed()
                        .withTitle("My embed title!")
                        .withUrl("https://github.com/7orivorian")
                        .withDescription("cute lil guyyy")
                        .withImage("https://i.pinimg.com/originals/1b/34/7c/1b347cf538cf2099ed59d88a68c312b9.jpg")
                        .withFooter("Powered by 7orivorian <3", "https://avatars.githubusercontent.com/u/61598620?v=4")
                        .withColor(new Color(0x00EEFF))
        );

// Send your message to all your friends :D
try {
    message.execute();
} catch (IOException e) {
    // Catch any exceptions for bug-squashing ease :>
    e.printStackTrace();
}
```

_Created and maintained by [7orivorian](https://github.com/7orivorian)_
