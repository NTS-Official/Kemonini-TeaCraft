package com.nts.ktcmod.item

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.item.misc.ItemOST
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.JukeboxSong
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistries {
    @JvmField
    val ITEMS = DeferredRegister.createItems(KTCMod.MODID)

    // 这个枚举类是注册物品的一些数据的集合
    enum class KTCModItems(val registryName: String) {
        BISCUIT("biscuit"),
        TOOLKIT_1("beginner_toolkit"),
        TOOLKIT_2("intermidiate_toolkit"),
        TOOLKIT_3("advanced_toolkit"),
        TOOLKIT_4("superior_toolkit"),
        TABLET("vitamin_tablet"),
        COIN("deprecated_coin"),
    }

    @JvmField
    val OST_BOX = ITEMS.registerItem("ost_box", ::ItemOST) { properties ->
        properties
            .stacksTo(DEFAULT_SPECIFIC_STACKSIZE)
            .rarity(Rarity.EPIC)
    }

    @JvmField
    val MISC_ITEMS: Map<KTCModItems, DeferredItem<Item>> = KTCModItems.entries.associateWith { item ->
        ITEMS.registerSimpleItem(item.registryName) { properties ->
            properties
                .stacksTo(DEFAULT_MAX_STACKSIZE)
                .rarity(Rarity.COMMON)
        }
    }

    @JvmField
    val DISCS: MutableList<DeferredItem<Item>> = mutableListOf()
    init {
        for (i in 1..21) {
            val discName = discItemName(i)
            val itemHolder = ITEMS.registerSimpleItem(discName) { properties ->
                properties
                    .stacksTo(MODIFIED_STACKSIZE)
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

    private fun discSongKey(index: Int): ResourceKey<JukeboxSong> {
        return ResourceKey.create(
            Registries.JUKEBOX_SONG,
            Identifier.fromNamespaceAndPath(KTCMod.MODID, discItemName(index))
        )
    }

    private const val DEFAULT_MAX_STACKSIZE = 64
    private const val DEFAULT_SPECIFIC_STACKSIZE = 16
    private const val MODIFIED_STACKSIZE = 4
}
