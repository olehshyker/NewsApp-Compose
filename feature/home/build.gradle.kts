plugins {
    id("build.android.feature")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.home"
}

dependencies {
    implementation(projects.core.common)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}