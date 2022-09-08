plugins {
    id(BuildPluginsConfig.androidApplication)
    kotlin(BuildPluginsConfig.kotlinAndroid)
    kotlin(BuildPluginsConfig.kotlinKapt)
    id(BuildPluginsConfig.kotlinParcelize)
    id(BuildPluginsConfig.androidHilt)
    id(BuildPluginsConfig.navigationSafeArgs)

    // Internal Script plugins
    id(ScriptPlugins.variants)
    id(ScriptPlugins.compilation)
//    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        multiDexEnabled = true
        applicationId = AppConfig.APP_ID
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }
    viewBinding {
        android.buildFeatures.viewBinding = false
    }

    dataBinding {
        android.buildFeatures.dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(DependenciesManager.kotlinImplementation)
    implementation(DependenciesManager.lifeCycleKtxImplementation)
    implementation(DependenciesManager.androidxImplementation)
    implementation(DependenciesManager.navigationImplementation)
    implementation(DependenciesManager.thirdPartyImplementation)
    implementation(DependenciesManager.networkImplementation)
    implementation(DependenciesManager.hiltImplementation)
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(DependenciesManager.hiltKapt)
    kapt(DependenciesManager.networkKapt)
    testImplementation(DependenciesManager.testingImplementation)
    androidTestImplementation(DependenciesManager.androidTestImplementation)
    implementation(project(mapOf("path" to ":woodroid")))
    implementation(project(mapOf("path" to ":cocart")))

    // Room components
    implementation("android.arch.persistence.room:runtime:1.1.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
    implementation("androidx.room:room-ktx:2.4.3")
    implementation("androidx.room:room-common:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("com.paypal.checkout:android-sdk:0.8.1")
}
