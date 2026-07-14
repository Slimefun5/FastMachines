plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
    id("io.github.intisy.github-gradle") version "1.8.3"
}

group = "net.guizhanss"
description = "FastMachines is a Slimefun addon that adds more machines that bulk craft items with all shapeless recipes."

apply(from = "https://raw.githubusercontent.com/Slimefun5/gradle/stable/slimefun-addon.gradle")

dependencies {
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
        minimize()
        exclude("io/github/thebusybiscuit/slimefun5/**")
    }
}
