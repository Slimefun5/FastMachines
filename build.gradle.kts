import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.3.2"
    id("io.github.intisy.github-gradle") version "1.8.3"
}

group = "net.guizhanss"
description = "FastMachines is a Slimefun addon that adds more machines that bulk craft items with all shapeless recipes."

apply(from = "https://raw.githubusercontent.com/Slimefun5/gradle/stable/slimefun-addon.gradle")

kotlin {
    compilerOptions {
        // Kotlin 2.1's stdlib is Java-8 bytecode; targeting JVM_1_8 keeps our own .class files at
        // bytecode major version 52 too, so the whole jar loads on a Java 8 runtime (1.8.8 - 26.x).
        jvmTarget = JvmTarget.JVM_1_8
    }
}

dependencies {
    // Kotlin stdlib must be bundled (not relocated - relocating breaks @Metadata/intrinsics): Paper's
    // library loader (used by the upstream build to fetch it at runtime) is 1.16.5+ and absent on 1.8.8.
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.bstats:bstats-bukkit:3.1.0")

    // InfinityExpansion has already been ported to this fork (same addons/ dir); depend on its release.
    githubCompileOnly("Slimefun5:InfinityExpansion:v1.1.3.7")

    // SlimefunTranslation, SlimeFrame and InfinityExpansion2 have NOT been ported to this fork yet (no
    // slimefun5-compatible release exists), so those three soft-integrations are gated/removed - see
    // IntegrationService and the removed machine classes for details.
}

tasks {
    shadowJar {
        relocate("org.bstats", "net.guizhanss.fastmachines.libs.bstats")
        // Keep the full Kotlin stdlib + reflect: reflection impl is loaded dynamically, so minimize()
        // can't see it's needed and would strip kotlin-reflect (KotlinReflectionNotSupportedError at runtime).
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*:.*"))
        }
        exclude("io/github/thebusybiscuit/slimefun5/**")
    }
}
