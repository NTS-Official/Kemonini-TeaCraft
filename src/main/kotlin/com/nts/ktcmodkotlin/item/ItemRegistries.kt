package com.nts.ktcmodkotlin.item

import com.nts.ktcmodkotlin.KTCMod
import com.nts.ktcmodkotlin.item.misc.ItemOST
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.JukeboxSong
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.UnaryOperator

object ItemRegistries {
    @JvmField
    val ITEMS = DeferredRegister.createItems(KTCMod.MODID)

    @JvmField
    val OST = ITEMS.registerItem("ost", ::ItemOST, UnaryOperator.identity())

    // 这个枚举类是注册物品的一些数据的集合
    enum class KTCModItems(val itemRegistryName: String) {
        BISCUIT("biscuit"),
        TOOLKIT_1("beginner_toolkit"),
        TOOLKIT_2("intermidiate_toolkit"),
        TOOLKIT_3("advanced_toolkit"),
        TOOLKIT_4("superior_toolkit"),
        TABLET("vitamin_tablet"),
        COIN("deprecated_coin"),
    }

    @JvmField
    val MISC_ITEMS: Map<KTCModItems, DeferredItem<Item>> = KTCModItems.entries.associateWith { item ->
        ITEMS.registerSimpleItem(item.itemRegistryName)
    }

    @JvmField
    val DISCS: MutableList<DeferredItem<Item>> = mutableListOf()
    init {
        for (i in 1..21) {
            val discName = discItemName(i)
            val itemHolder = ITEMS.registerItem(
                discName,
                ::Item
            ) { properties ->
                properties
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(discSongKey(i))
            }
            DISCS += itemHolder
        }
    }

    fun getDisc(index: Int): DeferredItem<Item> = DISCS[index - 1]

    fun getMiscItem(item: KTCModItems): DeferredItem<Item> = MISC_ITEMS.getValue(item)

    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
        KTCMod.LOGGER.info("Registering Items for " + KTCMod.MODID + "...")
    }

    private fun discItemName(index: Int): String = "ost_disc_$index"

    private fun discSongKey(index: Int): ResourceKey<JukeboxSong> =
        ResourceKey.create(
            Registries.JUKEBOX_SONG,
            Identifier.fromNamespaceAndPath(KTCMod.MODID, discItemName(index))
        )
}
