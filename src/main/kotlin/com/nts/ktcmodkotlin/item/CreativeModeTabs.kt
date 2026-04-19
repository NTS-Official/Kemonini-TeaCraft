package com.nts.ktcmodkotlin.item

import com.nts.ktcmodkotlin.KTCMod
import com.nts.ktcmodkotlin.item.ItemRegistries.OST
import com.nts.ktcmodkotlin.item.ItemRegistries.OST_DISC_1
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.*
import java.util.function.Supplier

object CreativeModeTabs {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KTCMod.MODID)
    val KTTCMOD_CREATIVE_TAB_1: DeferredHolder<CreativeModeTab, CreativeModeTab> =
        CREATIVE_MODE_TABS.register(
            "kttcmod_tab_1",
            Supplier {
                CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kemono_teatime.misc"))
                    .icon(Supplier { OST.get().defaultInstance })
                    .displayItems { parameters: CreativeModeTab.ItemDisplayParameters?, output: CreativeModeTab.Output? ->
                        output?.accept(OST.get())
                        output?.accept(OST_DISC_1.get())
                    }
                    .build()
            }
        )
    val KTTCMOD_CREATIVE_TAB_2: DeferredHolder<CreativeModeTab, CreativeModeTab> =
        CREATIVE_MODE_TABS.register(
            "kttcmod_tab_2",
            Supplier {
                CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kemono_teatime.blocks"))
                    .withTabsBefore(KTTCMOD_CREATIVE_TAB_1.id) //.icon(() -> BISCUIT_CONT.get().getDefaultInstance())
                    /*
                    .displayItems((parameters, output) -> {
                        output.accept(BISCUIT_CONT.get());
                    })
                    */
                    .build()
            }
        )

    fun register(eventBus: IEventBus) {
        CREATIVE_MODE_TABS.register(eventBus)
        KTCMod.LOGGER.info("Registering Creative Mode Tabs for " + KTCMod.MODID)
    }
}
