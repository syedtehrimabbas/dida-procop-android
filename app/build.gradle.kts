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
    defaultConfig.minSdk = AppConfig.MIN_SDK_VERSION
    defaultConfig.targetSdk = AppConfig.TARGET_SDK_VERSION
    defaultConfig.multiDexEnabled = true
    namespace = AppConfig.APP_ID
    defaultConfig.applicationId = AppConfig.APP_ID
    defaultConfig.versionCode = AppConfig.VERSION_CODE
    defaultConfig.versionName = AppConfig.VERSION_NAME
    defaultConfig.testInstrumentationRunner = AppConfig.androidTestInstrumentation
    viewBinding.isEnabled = false
    dataBinding.isEnabled = true

//    viewBinding {
//        android.buildFeatures.viewBinding = false
//    }
//    dataBinding {
//        android.buildFeatures.dataBinding = true
//    }

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
    implementation("androidx.appcompat:appcompat:1.6.1")
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
    implementation("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-common:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    implementation("com.paypal.checkout:android-sdk:0.8.1")
    implementation("com.github.xabaras:RecyclerViewSwipeDecorator:1.4")
    implementation("com.github.smarteist:autoimageslider:1.4.0")
}
