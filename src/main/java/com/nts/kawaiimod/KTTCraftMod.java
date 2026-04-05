package com.nts.kawaiimod;

import com.nts.kawaiimod.sounds.KTTCraftSounds;
import com.nts.kawaiimod.world.item.ItemRegistries;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.nts.kawaiimod.world.item.ItemRegistries.OST;

@Mod(KTTCraftMod.MODID)
public class KTTCraftMod {
    public static final String MODID = "kemono_teatime";
    public static final Logger LOGGER = LogUtils.getLogger();

    // 创造模式物品栏
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KTTCMOD_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("kttcmod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.kemono_teatime"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> OST.get().getDefaultInstance())
            .displayItems((parameters, output) -> {output.accept(OST.get());}).build());

    public KTTCraftMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistries.register(modEventBus);
        KTTCraftSounds.SOUND_EVENTS.register(modEventBus);
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        // 模组配置
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, KTTCraftModConfig.SPEC);
    }

    // 客户端初始化
    private void commonSetup(FMLCommonSetupEvent event) {
        // 声明
        LOGGER.info("YOU'RE USING THE EARLY ACCESS VERSION OF KEMONINI TEACRAFT MOD!");
        LOGGER.info("Please report any bugs in the GitHub Issue page of this mod!");
    }

    @SubscribeEvent
    // 服务端事件总线
    public void onServerStarting(ServerStartingEvent event) {
        // LOGGER.info("<infos to be filled>");
    }
}
