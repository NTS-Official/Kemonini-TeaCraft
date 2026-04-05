package com.nts.kawaiimod.world.item;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.world.item.DiscFragmentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistries {
    // 物品注册
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KTTCraftMod.MODID);
    public static final DeferredItem<Item> OST = ITEMS.registerSimpleItem("ost");
    public static final DeferredItem<Item> OST_DISC_1 = ITEMS.registerItem("ost_disc_1", p -> new DiscFragmentItem(p.stacksTo(1).rarity(Rarity.RARE)));
    // 主类调用方法
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        KTTCraftMod.LOGGER.info("Registering Items for " + KTTCraftMod.MODID);
    }
}
