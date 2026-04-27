package com.nts.ktcmod

import net.minecraft.network.chat.Component
import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    @JvmField val ENABLE_PITY: ModConfigSpec.BooleanValue
    @JvmField val PITY_THRESHOLD: ModConfigSpec.IntValue
    @JvmField val DISABLE_WANDERING_TRADER: ModConfigSpec.BooleanValue
    @JvmField val REPLACE_WANDERING_TRADER_WITH_PEDDLER: ModConfigSpec.BooleanValue

    init {
        BUILDER.push("disc_box")
        ENABLE_PITY = BUILDER
            .define("pity_enabled", true)
        PITY_THRESHOLD = BUILDER
            .defineInRange("pity_threshold", 16, 1, 1000)
        BUILDER.pop()

        BUILDER.push("world_gen")
        DISABLE_WANDERING_TRADER = BUILDER
            .define("wanderingtrader_disabled", false)
        REPLACE_WANDERING_TRADER_WITH_PEDDLER = BUILDER
            .define("replace_wandering_trader_with_peddler", true)
        BUILDER.pop()
    }

    val SPEC: ModConfigSpec = BUILDER.build()
}
