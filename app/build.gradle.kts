plugins {
    alias(libs.plugins.android.application)
    id("androidx.room")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 2

val debugMinify = false
val releaseMinify = true

private fun generateVersionCode() : Int {
    return versionMajor * 10000 + versionMinor * 100 + versionPatch
}

private fun generateVersionName() : String {
    return "${versionMajor}.${versionMinor}.${versionPatch}"
}

android {
    namespace = "com.amirbahadoramiri.kalayar"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.amirbahadoramiri.kalayar"
        minSdk = 26
        targetSdk = 37
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    dataBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

}

kotlin {
    jvmToolchain(21)
}

room {
    schemaDirectory("$projectDir/schemas")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.gson)

    implementation(libs.telegramdialog)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.chip.navigation.bar)

    implementation(libs.primecalendar)

    implementation(libs.pinview)

}
