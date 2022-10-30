plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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

tasks.getByName<JavaCompile>("compileJava") {
    options.compilerArgs.add("-parameters")
}

tasks.getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs.add("-parameters")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
