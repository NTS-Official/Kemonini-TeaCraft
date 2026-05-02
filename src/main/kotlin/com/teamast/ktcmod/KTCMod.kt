package com.teamast.ktcmod

import com.mojang.logging.LogUtils
import com.teamast.ktcmod.sounds.SoundEvents
import com.teamast.ktcmod.world.effect.EffectRegistries
import com.teamast.ktcmod.world.entity.EntityRegistries
import com.teamast.ktcmod.world.item.CreativeModeTabs
import com.teamast.ktcmod.world.item.ItemRegistries
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import org.slf4j.Logger

@Mod(KTCMod.MODID) class KTCMod(modEventBus: IEventBus, modContainer: ModContainer) {
    init {
        modEventBus.addListener { event: FMLCommonSetupEvent? -> this.commonSetup(event) }
        CreativeModeTabs.register(modEventBus)
        ItemRegistries.register(modEventBus)
        SoundEvents.register(modEventBus)
        EntityRegistries.register(modEventBus)
        EffectRegistries.register(modEventBus)
        modContainer.registerConfig(ModConfig.Type.COMMON, KTCModConfig.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent?) {
        LOGGER.info("Kemonini Teacraft Mod initialized.")
    }

    companion object {
        const val MODID: String = "kemono_teatime"
        @JvmField
        val LOGGER: Logger = LogUtils.getLogger()
    }
}
