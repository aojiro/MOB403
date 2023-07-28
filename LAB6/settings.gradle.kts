pluginManagement {
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
    repositories {
        maven { url = uri("https://oss.sonatype.org/content/repositories/ksoap2-android-releases/") }
        // Other repositories, if any
    }
}

rootProject.name = "LAB6"
include(":app")
