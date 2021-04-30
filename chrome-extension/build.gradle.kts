plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":common"))

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
}

kotlin {
    js(LEGACY) {
        browser{
            commonWebpackConfig{
                outputFileName = "stick-calendar.js"
            }
        }
        binaries.executable()
    }
}
