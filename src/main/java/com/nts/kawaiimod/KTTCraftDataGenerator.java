package com.nts.kawaiimod;

import com.nts.kawaiimod.datagen.LanguageProvider$en_us;
import com.nts.kawaiimod.datagen.LanguageProvider$zh_cn;
import com.nts.kawaiimod.datagen.MiscModelProvider;
import com.nts.kawaiimod.datagen.MiscRecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = KTTCraftMod.MODID)
public class KTTCraftDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(MiscRecipeProvider.Runner::new);
        event.createProvider(MiscModelProvider::new);
        event.createProvider(LanguageProvider$en_us::new);
        event.createProvider(LanguageProvider$zh_cn::new);
    }
}
