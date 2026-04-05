package com.nts.kawaiimod.sounds;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class KTTCraftSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, KTTCraftMod.MODID);
    // 注册声音
    //public static final DeferredHolder<SoundEvent, SoundEvent> OST_MUSIC_1 =
    //        SOUND_EVENTS.register("ost_music_1",() -> SoundEvent.createVariableRangeEvent()));
}
