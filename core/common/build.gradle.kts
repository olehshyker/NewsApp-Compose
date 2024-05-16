plugins {
    id("build.android.library")
}

android {
    namespace = "com.olehsh.newsapp.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}