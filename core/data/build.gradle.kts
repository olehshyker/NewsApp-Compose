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
    implementation(projects.core.database)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.kotlinx.serialization.json)
}