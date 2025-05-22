plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.wiremock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations {
    create("wiremockStandalone")
}

dependencies {
    implementation("commons-codec:commons-codec:1.18.0")
    implementation("org.wiremock:wiremock-standalone:3.13.0")

    "wiremockStandalone"("org.wiremock:wiremock-standalone:3.13.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveClassifier.set("")
    exclude("META-INF/maven/org.wiremock/**")
    dependencies {
        exclude(dependency("org.wiremock:wiremock-standalone"))
    }
}

tasks.register<Copy>("copyWiremockStandalone") {
    description = "Copies the wiremock-standalone JAR to the project root folder"
    group = "distribution"

    from(configurations["wiremockStandalone"])
    into(projectDir.resolve("build/libs"))

    doLast {
        println("Copied wiremock-standalone JAR to: ${projectDir}")
    }
}

tasks.assemble {
    dependsOn(tasks.shadowJar, tasks["copyWiremockStandalone"])
}
