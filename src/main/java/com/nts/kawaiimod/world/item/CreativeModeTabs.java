package com.nts.kawaiimod.world.item;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.nts.kawaiimod.KTTCraftMod.MODID;
import static com.nts.kawaiimod.world.item.ItemRegistries.OST;
import static com.nts.kawaiimod.world.item.ItemRegistries.OST_DISC_1;

public class CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KTTCMOD_CREATIVE_TAB_1 =
            CREATIVE_MODE_TABS.register("kttcmod_tab_1", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kemono_teatime.misc"))
                    .icon(() -> OST.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(OST.get());
                        output.accept(OST_DISC_1.get());
                    })
                    .build()
            );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KTTCMOD_CREATIVE_TAB_2 =
            CREATIVE_MODE_TABS.register("kttcmod_tab_2", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kemono_teatime.blocks"))
                    .withTabsBefore(CreativeModeTabs.KTTCMOD_CREATIVE_TAB_1.getId())
                    //.icon(() -> BISCUIT_CONT.get().getDefaultInstance())
                    /*
                    .displayItems((parameters, output) -> {
                        output.accept(BISCUIT_CONT.get());
                    })
                    */
                    .build()
            );
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
        KTTCraftMod.LOGGER.info("Registering Creative Mode Tabs for " + KTTCraftMod.MODID);
    }
}
