plugins {
    id("build.android.library")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.domain"
}

dependencies {
    api(projects.core.model)
    api(projects.core.data)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.javax.inject)
}