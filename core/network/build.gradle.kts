import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    id("build.android.library")
    id("build.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
        buildConfigField(
            "String",
            "NEWS_API_KEY",
            "\"${gradleLocalProperties(rootDir, providers).getProperty("NEWS_API_KEY")}\""
        )
    }
}

dependencies {

    api(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}