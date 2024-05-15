plugins {
    id("build.android.library")
    id("build.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)

    implementation(libs.kotlinx.serialization.json)
}