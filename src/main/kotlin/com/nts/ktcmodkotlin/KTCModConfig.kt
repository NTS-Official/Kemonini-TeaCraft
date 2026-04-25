package com.nts.ktcmodkotlin

import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    val ENABLE_PITY: ModConfigSpec.BooleanValue
    val PITY_THRESHOLD: ModConfigSpec.IntValue
    // 保底机制
    init {
        val builder = ModConfigSpec.Builder()
        builder.push("disc_box")
        ENABLE_PITY = builder
            .comment("Enable pity system (guarantee disc25 after N uses without it)")
            .define("enablePity", true)
        PITY_THRESHOLD = builder
            .comment("Number of uses without disc25 before guarantee triggers")
            .defineInRange("pityThreshold", 16, 1, 1000)

        builder.pop()
    }
    val SPEC: ModConfigSpec = BUILDER.build()
}