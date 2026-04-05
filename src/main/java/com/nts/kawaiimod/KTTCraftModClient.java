package com.nts.kawaiimod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = KTTCraftMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = KTTCraftMod.MODID, value = Dist.CLIENT)
public class KTTCraftModClient {
    public KTTCraftModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // 客户端初始化
         KTTCraftMod.LOGGER.info("Client setup for " + KTTCraftMod.MODID);
    }
}
