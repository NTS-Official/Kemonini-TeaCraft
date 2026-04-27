package com.nts.ktcmod

import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    @JvmField val ENABLE_PITY: ModConfigSpec.BooleanValue
    @JvmField val PITY_THRESHOLD: ModConfigSpec.IntValue
    @JvmField val REPLACE_WANDERING_TRADER_WITH_PEDDLER: ModConfigSpec.BooleanValue

    init {
        BUILDER.push("disc_box")
        ENABLE_PITY = BUILDER
            .define("pity_enabled", true)
        PITY_THRESHOLD = BUILDER
            .defineInRange("pity_threshold", 16, 1, 1000)
        BUILDER.pop()

        BUILDER.push("world_gen")
        REPLACE_WANDERING_TRADER_WITH_PEDDLER = BUILDER
            .define("replace_wandering_trader_with_peddler", true)
        BUILDER.pop()
    }

    val SPEC: ModConfigSpec = BUILDER.build()
}
