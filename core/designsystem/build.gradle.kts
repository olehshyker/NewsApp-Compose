plugins {
    id("build.android.library")
    id("build.android.library.compose")
    id("build.spotless")
}

android {
    namespace = "com.olehsh.newsapp.designsystem"
}

dependencies {
    api(libs.coil.kt)
    api(libs.coil.kt.compose)

    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.animation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.constraintlayout)
}