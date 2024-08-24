import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.architectury.plugin.ArchitectPluginExtension
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask

plugins {
    java
    id("maven-publish")
    id("dev.architectury.loom") version "1.6-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

architectury {
    val minecraftVersion: String by project
    minecraft = minecraftVersion
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")
    apply(plugin = "com.github.johnrengelman.shadow")

    val minecraftVersion: String by project
    val modLoader = project.name
    val modId = rootProject.name
    val isCommon = modLoader == rootProject.projects.common.name
    val loom: LoomGradleExtensionAPI by project

    base {
        archivesName.set("$modId-$modLoader-$minecraftVersion")
    }

    loom.silentMojangMappingsLicense()

    repositories {
        mavenCentral()
        maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
        maven(url = "https://maven.neoforged.net/releases/")
    }

    dependencies {
        val resourcefulLibVersion: String by project
        val resourcefulConfigVersion: String by project
        val geckolibVersion: String by project

        "minecraft"("::$minecraftVersion")
        "mappings"(loom.officialMojangMappings())

        "modApi"(group = "com.teamresourceful.resourcefullib", name = "resourcefullib-$modLoader-$minecraftVersion", version = resourcefulLibVersion)
        "modApi"(group = "com.teamresourceful.resourcefulconfig", name = "resourcefulconfig-$modLoader-$minecraftVersion", version = resourcefulConfigVersion)

        "modImplementation"(group = "software.bernie.geckolib", name = "geckolib-$modLoader-$minecraftVersion", version = geckolibVersion)

        "include"(implementation(group = "com.teamresourceful", name = "resourceful-cosmetics-4j", version = "1.0.3"))
    }

    java {
        withSourcesJar()
    }


    tasks.jar {
        archiveClassifier.set("dev")
    }

    tasks.named<RemapJarTask>("remapJar") {
        archiveClassifier.set(null as String?)
    }

    tasks.processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        filesMatching(listOf("META-INF/neoforge.mods.toml", "fabric.mod.json")) {
            expand("version" to project.version)
        }
    }

    if (!isCommon) {
        configure<ArchitectPluginExtension> {
            platformSetupLoomIde()
        }

        val shadowCommon by configurations.creating

        tasks {
            "shadowJar"(ShadowJar::class) {
                archiveClassifier.set("dev-shadow")
                configurations = listOf(shadowCommon)
            }

            "remapJar"(RemapJarTask::class) {
                dependsOn("shadowJar")
                inputFile.set(named<ShadowJar>("shadowJar").flatMap { it.archiveFile })
                archiveClassifier.set(null as String?)
            }
        }
    } else {
        tasks.named<RemapJarTask>("remapJar") {
            archiveClassifier.set(null as String?)
        }
    }
}