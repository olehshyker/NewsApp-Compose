plugins {
    id("build.android.feature")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.bottombar"
}

dependencies {
    implementation(projects.core.common)
}