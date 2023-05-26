import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    `java-library`
}

group = "com.jaytux"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.jetbrains.compose.material:material:1.4.0")
    implementation("org.jetbrains.compose.material3:material3:1.4.0")
    implementation("org.jetbrains.compose:compose-full:1.4.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.4.0")
    implementation("org.antlr:antlr4-runtime:4.12.0")
    implementation("ca.gosyer:kotlin-multiplatform-appdirs:1.1.0")
    implementation(project(":cml_parser"))
    implementation("io.arrow-kt:arrow-core:1.2.0-RC")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "charman"
            packageVersion = "1.0.0"
        }
    }
}
