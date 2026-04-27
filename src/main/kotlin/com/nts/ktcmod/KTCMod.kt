package com.nts.ktcmod

import com.mojang.logging.LogUtils
import com.nts.ktcmod.world.item.CreativeModeTabs
import com.nts.ktcmod.world.item.ItemRegistries
import com.nts.ktcmod.sounds.SoundEvents
import com.nts.ktcmod.entity.EntityRegistries
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import org.slf4j.Logger

@Mod(KTCMod.MODID)
class KTCMod(modEventBus: IEventBus, modContainer: ModContainer) {
    init {
        modEventBus.addListener { event: FMLCommonSetupEvent? -> this.commonSetup(event) }
        modEventBus.addListener(KTCDataGenerator::gatherClientData)
        CreativeModeTabs.register(modEventBus)
        ItemRegistries.register(modEventBus)
        SoundEvents.register(modEventBus)
        EntityRegistries.register(modEventBus)
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
