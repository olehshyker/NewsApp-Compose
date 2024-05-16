plugins {
    id("build.android.feature")
}

android {
    namespace = "com.olehsh.newsapp.details"
}

dependencies {
    implementation(projects.core.common)
}