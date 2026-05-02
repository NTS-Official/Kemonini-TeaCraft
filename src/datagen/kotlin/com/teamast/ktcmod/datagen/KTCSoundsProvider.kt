package com.teamast.ktcmod.datagen

import com.teamast.ktcmod.KTCMod
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import java.util.concurrent.CompletableFuture

/**
 * 声音数据生成器
 */
class KTCSoundsProvider(output: PackOutput) : DataProvider {

    companion object {
        private const val DISC_COUNT = 21
    }

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

    private fun discSoundName(index: Int): String = "disc_music_$index"
}
