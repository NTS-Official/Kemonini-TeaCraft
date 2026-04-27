package com.nts.ktcmod

import com.nts.ktcmod.world.entity.EntityRegistries
import com.nts.ktcmod.world.entity.peddler.PeddlerRenderer
import net.minecraft.client.gui.screens.Screen
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.*
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.gui.*

@Mod(value = KTCMod.MODID, dist = [Dist.CLIENT])
@EventBusSubscriber(modid = KTCMod.MODID, value = [Dist.CLIENT])
class KTCModClient(container: ModContainer?) {
    init {
        container?.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { mod: ModContainer?,
                                   parent: Screen? -> mod?.let { parent?.let { it1 -> ConfigurationScreen(it, it1) } }!! })
    }

    companion object {
        @JvmStatic
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent?) {
            KTCMod.LOGGER.info("Setting up client configuration for " + KTCMod.MODID + "...")
        }

        @JvmStatic
        @SubscribeEvent
        fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            event.registerEntityRenderer(EntityRegistries.PEDDLER.get(), ::PeddlerRenderer)
        }
    }
}
