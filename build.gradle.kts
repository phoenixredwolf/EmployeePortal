import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

buildscript {
    apply("versions.gradle")
}

val ktor: String by project
val kotlinx_html: String by project
val mysql: String by project
val react: String by project
val serialization: String by project
val coroutines: String by project

plugins {
    kotlin("multiplatform") version "1.6.21"
    application
    kotlin("plugin.serialization") version "1.6.21"
}

group = "com.ravebizz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
                implementation("io.ktor:ktor-client-core:$ktor")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:$ktor")
                implementation("io.ktor:ktor-html-builder:$ktor")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinx_html")
                implementation("mysql:mysql-connector-java:$mysql")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktor")
                implementation("io.ktor:ktor-client-json:$ktor")
                implementation("io.ktor:ktor-client-serialization:$ktor")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:$react")
                implementation(npm("react", "17.0.2"))
                implementation(npm("react-dom", "17.0.2"))
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("ServerKt")
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "js.js"
}

tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")
        || project.gradle.startParameter.taskNames.contains("installDist")
    ) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val jsBrowserWebpack = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(jsBrowserWebpack)
    from(File(jsBrowserWebpack.destinationDirectory, jsBrowserWebpack.outputFileName))
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar"))
}