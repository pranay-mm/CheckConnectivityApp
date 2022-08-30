
plugins {
    kotlin("multiplatform")
    application
}
fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

kotlin {
    js(IR) {
        browser{
            binaries.executable()
            runTask {
                devServer = devServer?.copy(port = 3000)
            }
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.5")
            }
        }
    }
    dependencies {
        commonMainImplementation(enforcedPlatform(kotlinw("wrappers-bom:1.0.0-pre.340")))
        commonMainImplementation(kotlinw("react"))
        commonMainImplementation(kotlinw("react-dom"))
        commonMainImplementation(kotlinw("react-router-dom"))
        commonMainImplementation(kotlinw("mui"))
        commonMainImplementation(kotlinw("emotion"))

    }
}
