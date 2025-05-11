pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        // Tambahkan repositori Xposed API
        maven {
            url = uri("https://api.xposed.info/")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Pastikan urutan: mavenCentral sebelum Xposed
        maven {
            url = uri("https://api.xposed.info/")
        }
    }
}

rootProject.name = "tes-log-html"
include(":app")
