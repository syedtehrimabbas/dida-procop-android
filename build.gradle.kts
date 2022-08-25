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
