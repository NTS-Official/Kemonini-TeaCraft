package com.nts.ktcmod

import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    val ENABLE_PITY: ModConfigSpec.BooleanValue
    val PITY_THRESHOLD: ModConfigSpec.IntValue
    @JvmField val DISABLE_WANDERING_TRADER: ModConfigSpec.BooleanValue

    init {
        BUILDER.push("disc_box")
        ENABLE_PITY = BUILDER
            .comment("Enable pity system for disc box.")
            .define("enablePity", true)
        PITY_THRESHOLD = BUILDER
            .comment("Number of uses without disc21 before guarantee triggers")
            .defineInRange("pityThreshold", 16, 1, 1000)
        BUILDER.pop()

        BUILDER.push("world_gen")
        DISABLE_WANDERING_TRADER = BUILDER
            .comment("Disable Wandering Trader spawn. Only affects the trader itself, not naturally spawning llamas.")
            .define("disableWanderingTrader", false)
        BUILDER.pop()
    }

    val SPEC: ModConfigSpec = BUILDER.build()
}
