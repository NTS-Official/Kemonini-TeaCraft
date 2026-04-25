package com.nts.ktcmod

import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    val ENABLE_PITY: ModConfigSpec.BooleanValue
    val PITY_THRESHOLD: ModConfigSpec.IntValue

    init {
        BUILDER.push("disc_box")
        ENABLE_PITY = BUILDER
            .comment("Enable pity system (guarantee disc21 after N uses without it)")
            .define("enablePity", true)
        PITY_THRESHOLD = BUILDER
            .comment("Number of uses without disc21 before guarantee triggers")
            .defineInRange("pityThreshold", 16, 1, 1000)
        BUILDER.pop()
    }

    val SPEC: ModConfigSpec = BUILDER.build()
}
