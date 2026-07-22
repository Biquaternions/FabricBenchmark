plugins {
    id("net.fabricmc.fabric-loom") version "1.18.0-alpha.9"
    id("maven-publish")
}

version = project.findProperty("mod_version") as String
group = project.findProperty("maven_group") as String

val archivesBaseName = project.findProperty("archives_base_name") as String
val minecraftVersion = project.findProperty("minecraft_version") as String
val loaderVersion = project.findProperty("loader_version") as String

base {
    archivesName.set(archivesBaseName)
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")

}

val targetJavaVersion = 25
tasks {

    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraft_version", minecraftVersion)
        inputs.property("loader_version", loaderVersion)
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version,
                "minecraft_version" to minecraftVersion,
                "loader_version" to loaderVersion,
            )
        }
    }

    withType<JavaCompile>().configureEach {
        // ensure that the encoding is set to UTF-8, no matter what the system default is
        // this fixes some edge cases with special characters not displaying correctly
        // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
        // If Javadoc is generated, this must be specified in that task too.
        this.options.encoding = "UTF-8"
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            this.options.release.set(targetJavaVersion)
        }
    }

    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }
        // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
        // if it is present.
        // If you remove this line, sources will not be generated.
        withSourcesJar()
    }

    jar {
        from("LICENSE") {
            rename { "${it}_$archivesBaseName" }
        }
    }

    // configure the maven publication
    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifactId = archivesBaseName
                from(components["java"])
            }
        }

        // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
        repositories {
            // Add repositories to publish to here.
            // Notice: This block does NOT have the same function as the block in the top level.
            // The repositories here will be used for publishing your artifact, not for
            // retrieving dependencies.
        }
    }

}

