plugins {
    kotlin("multiplatform") version "1.4.31"
}

group = "it.woar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://kotlin.bintray.com/kotlinx/")
    maven {
        url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
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
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}
