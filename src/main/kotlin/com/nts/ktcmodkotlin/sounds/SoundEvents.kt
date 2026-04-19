package com.nts.ktcmodkotlin.sounds

import com.nts.ktcmodkotlin.KTCMod
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.*

object SoundEvents {
    val SOUND_EVENTS: DeferredRegister<SoundEvent> =
        DeferredRegister.create(Registries.SOUND_EVENT, KTCMod.MODID)

    // 注册声音
    val OST_MUSIC_01: DeferredHolder<SoundEvent, SoundEvent> =
        SOUND_EVENTS.register(
            "ost_music_01",
            java.util.function.Supplier {
                SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(
                        KTCMod.MODID,
                        "ost_music_01"
                    )
                )
            })
    val OST_MUSIC_2: DeferredHolder<SoundEvent, SoundEvent> =
        SOUND_EVENTS.register(
            "ost_music_02",
            java.util.function.Supplier {
                SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(
                        KTCMod.MODID,
                        "ost_music_02"
                    )
                )
            })

    fun register(eventBus: IEventBus) {
        SOUND_EVENTS.register(eventBus)
        KTCMod.LOGGER.info("Registering Sound Events for " + KTCMod.MODID + "...")
    }
}
