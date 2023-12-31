plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.travelinindia"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.travelinindia"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("org.jsoup:jsoup:1.15.4")

    //circular Image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //lottie animation
    val lottieVersion = "3.4.0"
    implementation ("com.airbnb.android:lottie:$lottieVersion")

    //gson
    implementation ("com.google.code.gson:gson:2.10.1")


    //Firebase
    implementation ("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation ("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation ("com.google.firebase:firebase-storage-ktx:20.2.1")


    //for xml data
    implementation("org.simpleframework:simple-xml:2.7.1")

    //rezorpay
    implementation ("com.razorpay:checkout:1.6.33")

}