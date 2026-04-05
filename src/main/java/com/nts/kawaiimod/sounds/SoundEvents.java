package com.nts.kawaiimod.sounds;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, KTTCraftMod.MODID);
    // 注册声音
    public static final DeferredHolder<SoundEvent, SoundEvent> OST_MUSIC_01 =
            SOUND_EVENTS.register("ost_music_01",() -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(KTTCraftMod.MODID, "ost_music_01")));
    public static final DeferredHolder<SoundEvent, SoundEvent> OST_MUSIC_2 =
            SOUND_EVENTS.register("ost_music_02",() -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(KTTCraftMod.MODID, "ost_music_02")));
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
        KTTCraftMod.LOGGER.info("Registering Sound Events for " + KTTCraftMod.MODID);
    }
}
