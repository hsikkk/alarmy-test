@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("app.kotlin.library")
}

dependencies{
    implementation(libs.coroutine)
    implementation(project(":core:domain"))
}
