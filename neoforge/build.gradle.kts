architectury {
    neoForge()
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentNeoForge"].extendsFrom(this)
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionNeoForge")) {
        isTransitive = false
    }

    val neoforgeVersion: String by project

    neoForge(group = "net.neoforged", name = "neoforge", version = neoforgeVersion)

    forgeRuntimeLibrary("com.teamresourceful:yabn:1.0.3")
    forgeRuntimeLibrary("com.teamresourceful:bytecodecs:1.0.2")
    forgeRuntimeLibrary("com.eliotlash.mclib:mclib:20")
}