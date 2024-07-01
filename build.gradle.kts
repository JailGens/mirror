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
    implementation("org.checkerframework:checker-qual:3.45.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core:5.12.0")
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
                uri("https://repo.jailgens.net/snapshots")
            } else {
                uri("https://repo.jailgens.net/releases")
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
