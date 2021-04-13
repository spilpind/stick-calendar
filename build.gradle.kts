allprojects {
    repositories {
        mavenCentral()
        maven { setUrl("https://kotlin.bintray.com/kotlinx/") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    }
}

plugins {
    kotlin("multiplatform") version "1.4.32" apply false
}
