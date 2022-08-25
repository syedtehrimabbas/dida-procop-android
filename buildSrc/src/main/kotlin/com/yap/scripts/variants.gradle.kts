package scripts

import AppConfig
import java.util.*


plugins { id("com.android.application") apply false }

private object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

private object ProductFlavors {
    const val DEV = "dev"
    const val LIVE = "live"
    const val PREPROD = "preprod"
    const val STG = "stg"
    const val QA = "qa"
}

private object FlavorDimensions {
    const val DEFAULT = "default"
}

object Default {
    const val BUILD_TYPE = BuildTypes.DEBUG
    const val BUILD_FLAVOR = ProductFlavors.DEV

    val BUILD_VARIANT = "${BUILD_FLAVOR.capitalize()}${BUILD_TYPE.capitalize()}"
}

android {
    signingConfigs {
        val localProperties = File(project.rootDir, "buildSrc/signing.properties")
        val properties = Properties()
        if (localProperties.exists()) {
            localProperties.inputStream().use { properties.load(it) }
            getByName(BuildTypes.DEBUG) {
                try {
                    if (properties.containsKey("android_debugkey_storefile")) {
                        storeFile =
                            File(
                                project.rootDir,
                                "buildSrc${properties["android_debugkey_storefile"]}"
                            )
                        storePassword = properties["android_debugkey_storePassword"].toString()
                        keyPassword = properties["android_debugkey_keyPassword"].toString()
                        keyAlias = properties["android_debugkey_keyAlias"].toString()
                    }
                } catch (e: Exception) {
                    System.err.println("debug signing.properties file is malformed")
                }
            }
            create(BuildTypes.RELEASE) {
                try {
                    if (properties.containsKey("android_releasekey_storefile")) {
                        storeFile =
                            File(
                                project.rootDir,
                                "buildSrc${properties["android_releasekey_storefile"]}"
                            )
                        storePassword = properties["android_releasekey_storePassword"].toString()
                        keyPassword = properties["android_releasekey_keyPassword"].toString()
                        keyAlias = properties["android_releasekey_keyAlias"].toString()
                    }
                } catch (e: Exception) {
                    System.err.println("release signing.properties file is malformed")
                }
            }
        } else {
            System.err.println("Missing signing.properties file for release and debug signing")
        }
    }
    buildTypes {
        getByName(BuildTypes.DEBUG) {
            signingConfig = signingConfigs.getByName(BuildTypes.DEBUG)
            isMinifyEnabled = false
            isDebuggable = true
            extra["enableCrashlytics"] = false
            extra["alwaysUpdateBuildId"] = false
        }
        getByName(BuildTypes.RELEASE) {
            signingConfig = signingConfigs.getByName(BuildTypes.RELEASE)
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions(FlavorDimensions.DEFAULT)
    productFlavors {
        val localProperties = File(project.rootDir, "buildSrc/apikeys.properties")
        val properties = Properties()
        if (localProperties.exists()) {
            localProperties.inputStream().use { properties.load(it) }
            try {
                create(ProductFlavors.DEV) {
                    dimension = FlavorDimensions.DEFAULT
                    applicationIdSuffix = ".${ProductFlavors.DEV}"
//                    versionNameSuffix = "-${ProductFlavors.DEV} -${AppConfig.VERSION_NAME}"
                    if (properties.containsKey("BASE_URL_DEV")) {
                        buildConfigString(
                            "BASE_URL",
                            decodeBase64(properties["BASE_URL_DEV"].toString())
                        )
                        buildConfigString(
                            "TERM_CONDITION_URL",
                            decodeBase64(properties["TERM_CONDITION_URL"].toString())
                        )
                        buildConfigString(
                            "APP_CENTER_KEY","abeff073-c4ca-409b-98e3-f626ed576904")
                        buildConfigString("ADJUST_TOKEN","mcswsh5vzabk")
                    }
                }
                create(ProductFlavors.PREPROD) {
                    dimension = FlavorDimensions.DEFAULT
                    applicationIdSuffix = ".${ProductFlavors.PREPROD}"
                    versionNameSuffix = "-${ProductFlavors.PREPROD} -${AppConfig.VERSION_NAME}"
                    if (properties.containsKey("BASE_URL_PREPROD")) {
                        buildConfigString(
                            "BASE_URL",
                            decodeBase64(properties["BASE_URL_PREPROD"].toString())
                        )
                        buildConfigString(
                            "TERM_CONDITION_URL",
                            decodeBase64(properties["TERM_CONDITION_URL"].toString())
                        )
                        buildConfigString(
                            "APP_CENTER_KEY","f0b80003-4a19-4bd4-99c1-2428f88497ce")
                        buildConfigString("ADJUST_TOKEN","vvvi5r0pggsg")
                    }
                }
                create(ProductFlavors.STG) {
                    dimension = FlavorDimensions.DEFAULT
                    applicationIdSuffix = ".${ProductFlavors.STG}"
                    versionNameSuffix = "-${ProductFlavors.STG} -${AppConfig.VERSION_NAME}"
                    if (properties.containsKey("BASE_URL_STG")) {
                        buildConfigString(
                            "BASE_URL",
                            decodeBase64(properties["BASE_URL_STG"].toString())
                        )
                        buildConfigString(
                            "TERM_CONDITION_URL",
                            decodeBase64(properties["TERM_CONDITION_URL"].toString())
                        )
                        buildConfigString(
                            "APP_CENTER_KEY","abeff073-c4ca-409b-98e3-f626ed576904")
                        buildConfigString("ADJUST_TOKEN","vvvi5r0pggsg")
                    }
                }
                create(ProductFlavors.QA) {
                    dimension = FlavorDimensions.DEFAULT
                    applicationIdSuffix = ".${ProductFlavors.QA}"
                    versionNameSuffix = "-${ProductFlavors.QA} -${AppConfig.VERSION_NAME}"
                    if (properties.containsKey("BASE_URL_QA")) {
                        buildConfigString(
                            "BASE_URL",
                            decodeBase64(properties["BASE_URL_QA"].toString())
                        )
                        buildConfigString(
                            "TERM_CONDITION_URL",
                            decodeBase64(properties["TERM_CONDITION_URL"].toString())
                        )
                        buildConfigString(
                            "APP_CENTER_KEY","abeff073-c4ca-409b-98e3-f626ed576904")
                    }
                    buildConfigString("ADJUST_TOKEN","vvvi5r0pggsg")
                }
                create(ProductFlavors.LIVE) {
                   // versionNameSuffix = "-${ProductFlavors.LIVE} -${AppConfig.VERSION_NAME_LIVE}"
                    versionCode = AppConfig.VERSION_CODE_LIVE
                    versionName = AppConfig.VERSION_NAME_LIVE
                    applicationId = AppConfig.APP_ID_LIVE
                    if (properties.containsKey("BASE_URL_LIVE")) {
                        dimension = FlavorDimensions.DEFAULT
                        buildConfigString(
                            "BASE_URL",
                            decodeBase64(properties["BASE_URL_LIVE"].toString())
                        )
                        buildConfigString(
                            "TERM_CONDITION_URL",
                            decodeBase64(properties["TERM_CONDITION_URL"].toString())
                        )
                        buildConfigString(
                            "APP_CENTER_KEY","2984ce2b-228e-427a-9102-32256162378e")
                        buildConfigString(
                            "ADJUST_TOKEN","5kz5tul4ybgg")
                    }
                }
            } catch (e: Exception) {
                System.err.println("The BASE_URL_DEV in apikeys.properties file is malformed")
            }
        } else {
            System.err.println("Missing apikeys.properties file.")
        }
    }
}