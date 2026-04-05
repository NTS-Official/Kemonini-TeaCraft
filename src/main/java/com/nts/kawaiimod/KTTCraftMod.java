package com.nts.kawaiimod;

import com.nts.kawaiimod.sounds.SoundEvents;
import com.nts.kawaiimod.world.item.CreativeModeTabs;
import com.nts.kawaiimod.world.item.ItemRegistries;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.network.chat.Component;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(KTTCraftMod.MODID)
public class KTTCraftMod {
    public static final String MODID = "kemono_teatime";
    public static final Logger LOGGER = LogUtils.getLogger();


    public KTTCraftMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        CreativeModeTabs.register(modEventBus);
        ItemRegistries.register(modEventBus);
        SoundEvents.register(modEventBus);
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        // 模组配置
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, KTTCraftConfig.SPEC);
    }

    // 客户端初始化
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info(Component.translatable("logs.kemono_teatime.client").getString());
    }

    @SubscribeEvent
    // 服务端事件总线
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info(Component.translatable("logs.kemono_teatime.server").getString());
    }
}
