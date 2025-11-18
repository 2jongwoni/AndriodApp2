pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "dksekrud"
include(":app")
include(":app:w02")
include(":app:w02:w03")
include(":app:w02:w04")
include(":app:w03")
include(":app:w04")
include(":app:w05")
include(":app:w06")
include(":app:w07")
include(":app:todo_list")
