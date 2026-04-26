package com.nts.ktcmod

import com.nts.ktcmod.datagen.KTCItemModelProvider
import com.nts.ktcmod.datagen.KTCLanguageProvider
import com.nts.ktcmod.datagen.KTCSoundsProvider
import com.nts.ktcmod.datagen.KTCJukeboxSongProvider
import net.neoforged.neoforge.data.event.GatherDataEvent

/**
 * 数据生成器主入口
 * 负责注册各个数据生成器
 */
object KTCDataGenerator {
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
    }
}
