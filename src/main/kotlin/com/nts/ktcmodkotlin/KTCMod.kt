package com.nts.ktcmodkotlin

import com.mojang.logging.LogUtils
import com.nts.ktcmodkotlin.item.CreativeModeTabs
import com.nts.ktcmodkotlin.item.ItemRegistries
import com.nts.ktcmodkotlin.sounds.SoundEvents
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.server.ServerStartingEvent
import org.slf4j.Logger

@Mod(KTCMod.MODID)
open class KTCMod(modEventBus: IEventBus, modContainer: ModContainer) {
    init {
        modEventBus.addListener { event: FMLCommonSetupEvent? -> this.commonSetup(event) }
        CreativeModeTabs.register(modEventBus)
        ItemRegistries.register(modEventBus)
        SoundEvents.register(modEventBus)
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this)
        // 模组配置
        modContainer.registerConfig(ModConfig.Type.COMMON, KTCModConfig.SPEC)
    }

    // 客户端初始化
    protected fun commonSetup(event: FMLCommonSetupEvent?) {
        LOGGER.info("Kemonini Teacraft Mod initialized.")
    }

    @SubscribeEvent // 服务端事件总线
    protected fun onServerStarting(event: ServerStartingEvent?) {
    }

    companion object {
        const val MODID: String = "kemono_teatime"
        @JvmField
        val LOGGER: Logger = LogUtils.getLogger()
    }
}