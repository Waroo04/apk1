pluginManagement {
    repositories {
        google()  // ✅ Simplified Google repository inclusion
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)  // ✅ Allows settings and project repositories
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "apk1"
include(":app")
