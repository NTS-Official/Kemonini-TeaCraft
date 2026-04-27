import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.kotlin.dsl.withGroovyBuilder

plugins {
    id("java-library")
    id("idea")
    id("maven-publish")
    id("net.neoforged.gradle.userdev") version "7.1.21"
    id("org.jetbrains.kotlin.jvm") version "2.3.20"
}

val minecraft_version: String by project
val minecraft_version_range: String by project
val neo_version: String by project
val loader_version_range: String by project
val mod_id: String by project
val mod_name: String by project
val mod_license: String by project
val mod_version: String by project
val mod_group_id: String by project

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}

version = mod_version
group = mod_group_id

sourceSets.named("main") {
    resources {
        srcDir("src/generated/resources")
        exclude("**/*.bbmodel")
        exclude("src/generated/**/.cache")
    }
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

base {
    archivesName.set(mod_id)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

val runs = extensions.getByName("runs") as NamedDomainObjectContainer<*>

runs.configureEach {
    (this as ExtensionAware).withGroovyBuilder {
        "systemProperty"("forge.logging.markers", "REGISTRIES")
        "systemProperty"("forge.logging.console.level", "debug")
        "modSource"(project.sourceSets["main"])
    }
}

runs.maybeCreate("client").withGroovyBuilder {
    "systemProperty"("neoforge.enabledGameTestNamespaces", mod_id)
}

runs.maybeCreate("server").withGroovyBuilder {
    "systemProperty"("neoforge.enabledGameTestNamespaces", mod_id)
    "argument"("--nogui")
}

runs.maybeCreate("gameTestServer").withGroovyBuilder {
    "systemProperty"("neoforge.enabledGameTestNamespaces", mod_id)
}

runs.maybeCreate("clientData").withGroovyBuilder {
    "argument"("--mod")
    "argument"(mod_id)
    "argument"("--all")
    "argument"("--output")
    "argument"(file("src/generated/resources/").absolutePath)
    "argument"("--existing")
    "argument"(file("src/main/resources/").absolutePath)
}

configurations.named("runtimeClasspath") {
    extendsFrom(configurations["localRuntime"])
}

dependencies {
    implementation("net.neoforged:neoforge:$neo_version")
    implementation("org.jetbrains:annotations:15.0")
    implementation("thedarkcolour:kotlinforforge-neoforge:6.2.0")
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "neo_version" to neo_version,
        "loader_version_range" to loader_version_range,
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_license" to mod_license,
        "mod_version" to mod_version,
    )
    inputs.properties(replaceProperties)

    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand(replaceProperties)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri(layout.projectDirectory.dir("repo"))
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
