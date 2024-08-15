plugins {
    id("java")
}

group = "bancolombia.angel.java.reactiva"
version = "1.0-SNAPSHOT"


repositories {
//    mavenCentral()
    maven {
        url = uri("https://artifactory.apps.bancolombia.com/maven-bancolombia/")
    }
}

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}