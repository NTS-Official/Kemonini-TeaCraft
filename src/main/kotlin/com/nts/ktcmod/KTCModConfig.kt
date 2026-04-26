package com.nts.ktcmod

import net.neoforged.neoforge.common.ModConfigSpec

object KTCModConfig {
    private val BUILDER = ModConfigSpec.Builder()
    @JvmField val ENABLE_PITY: ModConfigSpec.BooleanValue
    @JvmField val PITY_THRESHOLD: ModConfigSpec.IntValue
    @JvmField val DISABLE_WANDERING_TRADER: ModConfigSpec.BooleanValue

    init {
        BUILDER.push("disc_box")
        ENABLE_PITY = BUILDER
            .comment("config.${KTCMod.MODID}.pity1")
            .define("enablePity", true)
        PITY_THRESHOLD = BUILDER
            .comment("config.${KTCMod.MODID}.pity2")
            .defineInRange("pityThreshold", 16, 1, 1000)
        BUILDER.pop()

        BUILDER.push("world_gen")
        DISABLE_WANDERING_TRADER = BUILDER
            .comment("config.${KTCMod.MODID}.disable_trader")
            .define("disableWanderingTrader", false)
        BUILDER.pop()
    }

    val SPEC: ModConfigSpec = BUILDER.build()
}
