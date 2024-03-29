plugins {
    id(BuildPluginsConfig.androidApplication) apply false
    id(BuildPluginsConfig.androidLibrary) apply false
    kotlin(BuildPluginsConfig.kotlinAndroid) apply false
    kotlin(BuildPluginsConfig.kotlinKapt) apply false
    id(BuildPluginsConfig.kotlinParcelize) apply false
//    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(BuildClassesConfig.ANDROID_GRADLE_PLUGIN)
        classpath(BuildClassesConfig.KOTLIN_GRADLE_PLUGIN)
        classpath(BuildClassesConfig.NAVIGATION_SAFE_ARGS)
        classpath(BuildClassesConfig.HILT_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://maven.google.com")
        }
        maven {
            url = uri("https://cardinalcommerceprod.jfrog.io/artifactory/android")
            credentials {
                username = "paypal_sgerritz"
                password = "AKCp8jQ8tAahqpT5JjZ4FRP2mW7GMoFZ674kGqHmupTesKeAY2G8NcmPKLuTxTGkKjDLRzDUQ"
            }
        }
        jcenter() // Warning: this repository is going to shut down soon
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
subprojects {
    apply {
    }
}
