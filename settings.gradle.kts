enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "CreeperOverhaul"

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.teamresourceful.com/repository/maven-public/")
        gradlePluginPortal()
    }
}

include("common")
include("fabric")
include("neoforge")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}