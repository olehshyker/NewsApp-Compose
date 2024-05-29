// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven("https://plugins.gradle.org/m2/")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}