@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("app.kotlin.android.library")
}

android {
    namespace = "com.hsikkk.delightroom.datasource"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.coroutine)
    implementation(libs.media3.common)
    implementation(libs.media3.session)

    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:mediaplayer"))
}
