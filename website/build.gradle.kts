plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":common"))

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")

    //React, React DOM + Wrappers
    implementation("org.jetbrains:kotlin-react:17.0.1-pre.148-kotlin-1.4.21")
    implementation("org.jetbrains:kotlin-react-dom:17.0.1-pre.148-kotlin-1.4.21")
    implementation(npm("react", "17.0.1"))
    implementation(npm("react-dom", "17.0.1"))
    implementation(npm("react-datepicker", "3.6.0"))

    //Kotlin Styled
    implementation("org.jetbrains:kotlin-styled:5.2.1-pre.148-kotlin-1.4.21")
    implementation(npm("styled-components", "~5.2.1"))
}

kotlin {
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                outputFileName = "stick-calendar.js"
                cssSupport.enabled = true
            }
        }

        binaries.executable()
    }
}
