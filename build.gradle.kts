// Top-level build file. Module configuration lives in app/build.gradle.kts.
// Note: AGP 9 has built-in Kotlin, so org.jetbrains.kotlin.android is intentionally NOT applied.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
