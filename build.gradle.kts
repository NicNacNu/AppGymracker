// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.library") version "8.2.2" apply false // Use the latest stable version
    id("org.jetbrains.kotlin.android") version "2.1.10" apply false // Use the same stable version as in the module level
    id("com.google.devtools.ksp") version "2.1.10-1.0.30" apply false
    // ... other plugins


    alias(libs.plugins.android.application) apply false
}
