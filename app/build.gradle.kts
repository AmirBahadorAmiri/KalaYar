plugins {
    alias(libs.plugins.android.application)
    id("androidx.room")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
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
        versionCode = 1
        versionName = "1.0"

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
        enable=true
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
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.gson)

    implementation(libs.telegramdialog)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation("com.google.dagger:hilt-android:2.59.2")
    implementation("com.google.dagger:dagger-android-support:2.59.2")
    annotationProcessor("com.google.dagger:hilt-compiler:2.59.2")

    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    implementation("com.aminography:primecalendar:1.7.0")

}