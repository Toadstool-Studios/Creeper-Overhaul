import com.teamresourceful.utils.Platform
import com.teamresourceful.utils.getPlatform
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.kotlin.dsl.configure

plugins {
    java
    alias(libs.plugins.resourceful.loom)
}

subprojects {
    configure<LoomGradleExtensionAPI> {
        val accesswidenerFile = project.file("src/main/resources/creeperoverhaul.accesswidener")
        if (accesswidenerFile.exists()) {
            accessWidenerPath = accesswidenerFile
        } else {
            accessWidenerPath = project(":common").extensions.getByName<LoomGradleExtensionAPI>("loom").accessWidenerPath
        }
    }

    dependencies {
        when (getPlatform()) {
            Platform.COMMON -> {
                "modApi"(rootProject.libs.rlib.common)
                "modApi"(rootProject.libs.rconfig.common)
                "modImplementation"(rootProject.libs.geckolib.common)
            }
            Platform.FABRIC -> {
                "modApi"(rootProject.libs.rlib.fabric)
                "modApi"(rootProject.libs.rconfig.fabric)
                "modImplementation"(rootProject.libs.geckolib.fabric)
            }
            Platform.NEOFORGE -> {
                "modApi"(rootProject.libs.rlib.neoforge)
                "modApi"(rootProject.libs.rconfig.neoforge)
                "modImplementation"(rootProject.libs.geckolib.neoforge)

                "forgeRuntimeLibrary"("com.teamresourceful:yabn:1.0.3")
                "forgeRuntimeLibrary"("com.teamresourceful:bytecodecs:1.1.2")
                "forgeRuntimeLibrary"("com.eliotlash.mclib:mclib:20")
            }
        }

        implementation(rootProject.libs.rcosmetics) {
            "include"(this)
        }
    }
}