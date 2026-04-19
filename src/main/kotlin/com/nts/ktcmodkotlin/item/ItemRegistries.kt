package com.nts.ktcmodkotlin.item

import com.nts.ktcmodkotlin.KTCMod
import net.minecraft.world.item.DiscFragmentItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Function

object ItemRegistries {
    // 物品注册
    @JvmField
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(KTCMod.MODID)
    @JvmField
    val OST: DeferredItem<Item> = ITEMS.registerSimpleItem("ost")
    @JvmField
    val OST_DISC_1: DeferredItem<Item> = ITEMS.registerItem(
        "ost_disc_1",
        Function { p: Item.Properties? -> DiscFragmentItem(p!!.stacksTo(1).rarity(Rarity.RARE)) })

    // 主类调用方法
    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
        KTCMod.LOGGER.info("Registering Items for " + KTCMod.MODID)
    }
}
