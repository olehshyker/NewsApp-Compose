plugins {
    id("build.android.feature")
}

android {
    namespace = "com.olehsh.newsapp.bookmarks"
}

dependencies {
    implementation(projects.core.common)
}