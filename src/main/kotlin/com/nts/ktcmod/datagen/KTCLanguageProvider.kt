package com.nts.ktcmod.datagen

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.item.ItemRegistries
import com.google.gson.JsonObject
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import java.util.concurrent.CompletableFuture

/**
 * 语言文件数据生成器
 */
class KTCLanguageProvider(
    output: PackOutput,
    private val locale: String,
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

    companion object {
        private const val DISC_COUNT = 21

        /**
         * 英文翻译
         */
        fun englishTranslations(): Map<String, String> {
            val translations = linkedMapOf(
                "item.${KTCMod.MODID}.ost_box" to "Kemono Teatime OST",
                "itemGroup.${KTCMod.MODID}.blocks" to "Kemonini TeaCraft: Blocks",
                "itemGroup.${KTCMod.MODID}.misc" to "Kemonini TeaCraft: Miscs",
                "config.${KTCMod.MODID}.pity1" to "Enable pity system for disc box.",
                "config.${KTCMod.MODID}.pity2" to "Number of uses without disc21 before guarantee triggers",
                "config.${KTCMod.MODID}.disable_trader" to "Disable Wandering Trader spawn. Only affects the trader itself, not naturally spawning llamas.",
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

        /**
         * 中文翻译
         */
        fun chineseTranslations(): Map<String, String> {
            val translations = linkedMapOf(
                "item.${KTCMod.MODID}.ost_box" to "《兽娘红茶馆》原声带集",
                "itemGroup.${KTCMod.MODID}.blocks" to "兽娘红茶馆：方块",
                "itemGroup.${KTCMod.MODID}.misc" to "兽娘红茶馆：杂项",
                "config.${KTCMod.MODID}.pity1" to "Enable pity system for disc box.",
                "config.${KTCMod.MODID}.pity2" to "Number of uses without disc21 before guarantee triggers",
                "config.${KTCMod.MODID}.disable_trader" to "Disable Wandering Trader spawn. Only affects the trader itself, not naturally spawning llamas.",
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

        private fun englishDiscName(index: Int): String =
            if (index == 1) "01 - A cup of happiness" else "%02d - OST Disc %d".format(index, index)

        private fun chineseDiscName(index: Int): String =
            if (index == 1) "01 - A cup of happiness" else "%02d - OST Disc %d".format(index, index)

        private fun discItemName(index: Int): String = "ost_disc_$index"

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
}
