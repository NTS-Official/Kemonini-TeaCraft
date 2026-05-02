package com.teamast.ktcmod.sounds

import com.teamast.ktcmod.KTCMod
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object SoundEvents {
    @JvmStatic
    val SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, KTCMod.MODID)

    @JvmStatic
    val DISC_SOUNDS: MutableList<DeferredHolder<SoundEvent, SoundEvent>> = mutableListOf()

    init {
        for (i in 1..21) {
            val discMusicName = "disc_music_$i"
            val discSound = SOUND_EVENTS.register(discMusicName) { id ->
                SoundEvent.createVariableRangeEvent(id)
            }
            DISC_SOUNDS += discSound
        }
    }

    fun getDiscSounds(i: Int): SoundEvent = DISC_SOUNDS[i - 1].get()

    fun register(eventBus: IEventBus) {
        SOUND_EVENTS.register(eventBus)
        KTCMod.LOGGER.info("Registering Sound Events for " + KTCMod.MODID + "...")
    }
}
