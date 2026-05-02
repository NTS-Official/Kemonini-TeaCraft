package com.teamast.ktcmod

import com.teamast.ktcmod.datagen.KTCItemModelProvider
import com.teamast.ktcmod.datagen.KTCLanguageProvider
import com.teamast.ktcmod.datagen.KTCSoundsProvider
import com.teamast.ktcmod.datagen.KTCJukeboxSongProvider
import com.teamast.ktcmod.datagen.KTCAdvancementProvider
import net.minecraft.data.advancements.AdvancementProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber

/**
 * 数据生成器主入口
 * 负责注册各个数据生成器
 */
@EventBusSubscriber(modid = KTCMod.MODID)
object KTCDataGenerator {
    @JvmStatic
    @SubscribeEvent
    fun gatherClientData(event: GatherDataEvent.Client) {
        event.createProvider { output -> KTCItemModelProvider(output) }

        // 英文
        event.createProvider { output ->
            KTCLanguageProvider(output, "en_us") { KTCLanguageProvider.englishTranslations() }
        }
        // 中文
        event.createProvider { output ->
            KTCLanguageProvider(output, "zh_cn") { KTCLanguageProvider.chineseTranslations() }
        }

        event.createProvider { output -> KTCSoundsProvider(output) }
        event.createProvider { output -> KTCJukeboxSongProvider(output) }
        event.createProvider { output, lookupProvider ->
            AdvancementProvider(output, lookupProvider, listOf(KTCAdvancementProvider()))
        }
    }
}
