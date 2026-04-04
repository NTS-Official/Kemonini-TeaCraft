package com.nts.kawaiimod.item;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistries {
    // 物品注册
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KTTCraftMod.MODID);

    public static final DeferredItem<Item> LOGO = ITEMS.registerSimpleItem(
            "logo",p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2.0F).build()));

    // 主类调用方法
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        KTTCraftMod.LOGGER.info("Registering Items for " + KTTCraftMod.MODID);
    }
}
