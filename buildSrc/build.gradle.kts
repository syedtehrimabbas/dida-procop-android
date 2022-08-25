object Dependencies {
    const val AndroidBuildTools = "com.android.tools.build:gradle:7.0.4"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
//    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.14.1"
}

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}
kotlinDslPluginOptions.experimentalWarning.set(false)
dependencies {
    implementation(Dependencies.AndroidBuildTools)
    implementation(Dependencies.kotlinGradlePlugin)
//    implementation(Dependencies.kotlinGradlePlugin)
//    implementation(Dependencies.detektGradlePlugin)
}
