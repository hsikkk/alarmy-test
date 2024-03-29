pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "delightroom-test"
include(":app")
include(":core:domain")
include(":core:data")
include(":core:datasource")
include(":core:di")
include(":core:mediaplayer")
include(":core:designsystem")
include(":core:common")
include(":feature:browser:ui")
include(":feature:browser:navigation")
include(":feature:player")
include(":core:navigation")
include(":core:viewmodel")
