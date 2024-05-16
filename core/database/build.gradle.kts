plugins {
    id("build.android.library")
    id("build.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.olehsh.newsapp.database"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.android)
}