plugins {
    `java-library`
    `maven-publish`
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.checkerframework:checker-qual:3.26.0")

    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "mirror"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "sparky"
            url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                uri("https://repo.sparky983.me/snapshots")
            } else {
                uri("https://repo.sparky983.me/releases")
            }
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

tasks.getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs.add("-parameters")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
