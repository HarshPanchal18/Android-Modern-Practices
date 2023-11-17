buildscript {
    val koinVersion = "2.2.3"
    dependencies {
        classpath("io.insert-koin:koin-gradle-plugin:$koinVersion")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
