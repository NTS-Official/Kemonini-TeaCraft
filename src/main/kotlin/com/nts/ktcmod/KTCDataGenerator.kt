package com.nts.ktcmod

import com.nts.ktcmod.item.ItemRegistries
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.ModelProvider
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

object KTCDataGenerator {
    private const val DISC_COUNT = 21
    fun gatherClientData(event: GatherDataEvent.Client) {
        event.createProvider { output -> KTCItemModelProvider(output) }
        event.createProvider { output -> KTCLanguageProvider(output, "en_us") { englishTranslations() } }
        event.createProvider { output -> KTCLanguageProvider(output, "zh_cn") { chineseTranslations() } }
        event.createProvider { output -> KTCSoundsProvider(output) }
        event.createProvider { output -> KTCJukeboxSongProvider(output) }
    }

    private class KTCItemModelProvider(output: PackOutput) : ModelProvider(output, KTCMod.MODID) {
        override fun registerModels(blockModels: BlockModelGenerators, itemModels: ItemModelGenerators) {
            itemModels.generateFlatItem(ItemRegistries.OST_BOX.get(), ModelTemplates.FLAT_ITEM)
            for (index in 1..DISC_COUNT) {
                itemModels.generateFlatItem(ItemRegistries.getDisc(index).get(), ModelTemplates.FLAT_ITEM)
            }
            for (miscItem in ItemRegistries.KTCModItems.entries) {
                itemModels.generateFlatItem(ItemRegistries.getMiscItem(miscItem).get(), ModelTemplates.FLAT_ITEM)
            }
        }
    }

    private class KTCLanguageProvider(
        output: PackOutput,
        locale: String,
        private val entriesFactory: () -> Map<String, String>
    ) : DataProvider {
        private val pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang")
        private val localeId = Identifier.fromNamespaceAndPath(KTCMod.MODID, locale)

        override fun run(cache: CachedOutput): CompletableFuture<*> {
            val root = JsonObject()
            entriesFactory().forEach { (key, value) -> root.addProperty(key, value) }
            return DataProvider.saveStable(cache, root, pathProvider.json(localeId))
        }

        override fun getName(): String = "Languages ($localeId)"
    }

    private class KTCSoundsProvider(output: PackOutput) : DataProvider {
        private val pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "")

        override fun run(cache: CachedOutput): CompletableFuture<*> {
            val root = JsonObject()
            for (index in 1..DISC_COUNT) {
                val soundEntry = JsonObject()
                val sounds = JsonArray()
                sounds.add("${KTCMod.MODID}:records/${discSoundName(index)}")
                soundEntry.add("sounds", sounds)
                root.add(discSoundName(index), soundEntry)
            }
            return DataProvider.saveStable(cache, root, pathProvider.json(Identifier.fromNamespaceAndPath(KTCMod.MODID, "sounds")))
        }

        override fun getName(): String = "Sounds - ${KTCMod.MODID}"
    }

    private class KTCJukeboxSongProvider(output: PackOutput) : DataProvider {
        private val pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "jukebox_song")

        override fun run(cache: CachedOutput): CompletableFuture<*> {
            return CompletableFuture.allOf(
                *(1..DISC_COUNT).map { index ->
                    DataProvider.saveStable(cache, jukeboxSongJson(index), pathProvider.json(Identifier.fromNamespaceAndPath(KTCMod.MODID, discItemName(index))))
                }.toTypedArray()
            )
        }

        override fun getName(): String = "Jukebox Songs - ${KTCMod.MODID}"
    }

    private fun englishTranslations(): Map<String, String> {
        val translations = linkedMapOf(
            "item.${KTCMod.MODID}.ost_box" to "Kemono Teatime OST",
            "itemGroup.${KTCMod.MODID}.blocks" to "Kemonini TeaCraft: Blocks",
            "itemGroup.${KTCMod.MODID}.misc" to "Kemonini TeaCraft: Miscs",
            "logs.${KTCMod.MODID}.client" to "YOU'RE USING THE EARLY ACCESS VERSION! THERE MAY HAVE SOME BUGS THAT COULD LEAD TO GAME CRASHES! PLEASE REPORT THEM TO THE DEVELOPER!",
        )

        for (miscItem in ItemRegistries.KTCModItems.entries) {
            translations["item.${KTCMod.MODID}.${miscItem.registryName}"] = englishMiscName(miscItem)
        }

        for (index in 1..DISC_COUNT) {
            val discName = englishDiscName(index)
            translations["item.${KTCMod.MODID}.${discItemName(index)}"] = discName
            translations["item.${KTCMod.MODID}.${discItemName(index)}.desc"] = "Melodious Moments: Kemono Teatime OST - $discName"
        }
        return translations
    }

    private fun chineseTranslations(): Map<String, String> {
        val translations = linkedMapOf(
            "item.${KTCMod.MODID}.ost_box" to "《兽娘红茶馆》原声带集",
            "itemGroup.${KTCMod.MODID}.blocks" to "兽娘红茶馆：方块",
            "itemGroup.${KTCMod.MODID}.misc" to "兽娘红茶馆：杂项",
            "logs.${KTCMod.MODID}.client" to "您正在使用本模组的早期测试版本！如有任何bug请务必向开发者反馈！",
        )

        for (miscItem in ItemRegistries.KTCModItems.entries) {
            translations["item.${KTCMod.MODID}.${miscItem.registryName}"] = chineseMiscName(miscItem)
        }

        for (index in 1..DISC_COUNT) {
            val discName = chineseDiscName(index)
            translations["item.${KTCMod.MODID}.${discItemName(index)}"] = discName
            translations["item.${KTCMod.MODID}.${discItemName(index)}.desc"] = "《兽娘红茶馆》原声带集 - $discName"
        }
        return translations
    }

    private fun jukeboxSongJson(index: Int): JsonObject {
        val root = JsonObject()
        root.addProperty("sound_event", "${KTCMod.MODID}:${discSoundName(index)}")
        root.add(
            "description",
            JsonObject().apply {
                addProperty("translate", "item.${KTCMod.MODID}.${discItemName(index)}.desc")
            }
        )
        root.addProperty("length_in_seconds", 180.0)
        root.addProperty("comparator_output", discComparatorValue(index))
        return root
    }

    private fun englishDiscName(index: Int): String =
        if (index == 1) "01 - A cup of happiness" else "%02d - OST Disc %d".format(index, index)

    private fun chineseDiscName(index: Int): String =
        if (index == 1) "01 - A cup of happiness" else "%02d - OST Disc %d".format(index, index)

    private fun discItemName(index: Int): String = "ost_disc_$index"

    private fun discSoundName(index: Int): String = "disc_music_$index"

    private fun discComparatorValue(index: Int): Int = (index - 1) % 15 + 1

    private fun englishMiscName(item: ItemRegistries.KTCModItems): String = when (item) {
        ItemRegistries.KTCModItems.BISCUIT -> "Biscuit"
        ItemRegistries.KTCModItems.TOOLKIT_1 -> "Beginner's Toolkit"
        ItemRegistries.KTCModItems.TOOLKIT_2 -> "Intermediate Toolkit"
        ItemRegistries.KTCModItems.TOOLKIT_3 -> "Advanced Toolkit"
        ItemRegistries.KTCModItems.TOOLKIT_4 -> "Superior Toolkit"
        ItemRegistries.KTCModItems.TABLET -> "Vitamin Tablet"
        ItemRegistries.KTCModItems.COIN -> "Deprecated Coin"
    }

    private fun chineseMiscName(item: ItemRegistries.KTCModItems): String = when (item) {
        ItemRegistries.KTCModItems.BISCUIT -> "饼干"
        ItemRegistries.KTCModItems.TOOLKIT_1 -> "新手工具包"
        ItemRegistries.KTCModItems.TOOLKIT_2 -> "中级工具包"
        ItemRegistries.KTCModItems.TOOLKIT_3 -> "高级工具包"
        ItemRegistries.KTCModItems.TOOLKIT_4 -> "卓越工具包"
        ItemRegistries.KTCModItems.TABLET -> "维生素片"
        ItemRegistries.KTCModItems.COIN -> "废弃硬币"
    }
}
