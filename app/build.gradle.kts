plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.solidmobile"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.solidmobile"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

configurations.all {
    resolutionStrategy {
        force(
            "androidx.test.espresso:espresso-core:3.7.0",
            "androidx.test.espresso:espresso-idling-resource:3.7.0",
            "androidx.test:runner:1.7.0",
            "androidx.test:core:1.7.0",
            "androidx.test:core-ktx:1.7.0",
            "androidx.test:monitor:1.8.0",
            "androidx.test.ext:junit:1.3.0",
        )
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.lifecycle.viewmodel.compose)

//    testImplementation(libs.kotlinx.coroutines.test)
//    testImplementation(libs.turbine)
//    testImplementation(libs.mockk)
//    androidTestImplementation(libs.mockk.android)

    //Тесты
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation("androidx.arch.core:core-testing:2.2.0") // для безопасности, хотя тут не LiveData
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:<compose_version>")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:<compose_version>")

    //Тесты Hilt
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1") // версия как в проекте
    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")

    //Icon
    implementation(libs.androidx.compose.material.icons.core)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    //Room
    implementation("androidx.room:room-runtime:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
    ksp("androidx.room:room-compiler:2.7.1")

    // Retrofit для сетевых запросов
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp для логирования запросов (удобно для дебага)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutines для асинхронности
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle для ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
}