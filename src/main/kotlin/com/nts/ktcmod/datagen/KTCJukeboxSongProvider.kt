package com.nts.ktcmod.datagen

import com.nts.ktcmod.KTCMod
import com.google.gson.JsonObject
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import java.util.concurrent.CompletableFuture

/**
 * 点唱机歌曲数据生成器
 */
class KTCJukeboxSongProvider(output: PackOutput) : DataProvider {

    companion object {
        private const val DISC_COUNT = 21
    }

    private val pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "jukebox_song")

    override fun run(cache: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(
            *(1..DISC_COUNT).map { index ->
                DataProvider.saveStable(cache, jukeboxSongJson(index), pathProvider.json(Identifier.fromNamespaceAndPath(KTCMod.MODID, discItemName(index))))
            }.toTypedArray()
        )
    }

    override fun getName(): String = "Jukebox Songs - ${KTCMod.MODID}"

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

    private fun discItemName(index: Int): String = "ost_disc_$index"

    private fun discSoundName(index: Int): String = "disc_music_$index"

    private fun discComparatorValue(index: Int): Int = (index - 1) % 15 + 1
}
