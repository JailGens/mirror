<h1 align="center">Mirror</h1>

<p align="center">
    <img src="https://img.shields.io/github/v/release/jailgens/mirror?display_name=release&label=Release&style=flat-square&color=12bed3&labelColor=06222b" alt="Version">
    <a href="https://app.codecov.io/gh/jailgens/mirror">
        <img src="https://img.shields.io/codecov/c/github/jailgens/mirror?label=Coverage&style=flat-square&color=12bed3&labelColor=06222b" alt="Coverage">
    </a>
    <img src="https://img.shields.io/github/commit-activity/m/jailgens/mirror?label=Commits&style=flat-square&color=12bed3&labelColor=06222b" alt="Commit activity">
    <img src="https://img.shields.io/github/license/jailgens/mirror?label=License&style=flat-square&color=12bed3&labelColor=06222b" alt="License Apache">
</p>

<p align="center">
    Java reflection wrapper that provides a much cleaner, and easy-to-use API.
</p>

## Table of Contents

1. [Installation](#-installation)
    1. [Gradle (Kotlin DSL)](#gradle-kotlin-dsl)
    2. [Gradle (Groovy DSL)](#gradle-groovy-dsl)
    3. [Maven](#maven)
2. [Contributing](#-contributing)
3. [License](#-license)

## 📦 Installation

Replace `{release}` with the latest release version.

<img src="https://img.shields.io/github/v/release/jailgens/mirror?display_name=release&label=Release&style=flat-square&color=12bed3&labelColor=06222b" alt="Version">

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://repo.sparky983.me/releases")
}

dependencies {
    implementation("net.jailgens:mirror:{release}")
}
```

### Gradle (Groovy DSL)

```groovy
repositories {
    maven {
        url "https://repo.sparky983.me/releases"
    }
}

dependencies {
    implementation "net.jailgens:mirror:{release}"
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>sparky</id>
        <url>https://repo.sparky983.me/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.jailgens</groupId>
        <artifactId>mirror</artifactId>
        <version{release}</version>
    </dependency>
</dependencies>
```

## 🤝 Contributing

[CONTRIBUTING.md](CONTRIBUTING.md)

## 📝 License

[LICENSE](LICENSE)
