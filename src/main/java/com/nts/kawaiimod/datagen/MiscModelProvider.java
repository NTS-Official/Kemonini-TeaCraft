package com.nts.kawaiimod.datagen;

import com.nts.kawaiimod.KTTCraftMod;
import com.nts.kawaiimod.world.item.ItemRegistries;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public class MiscModelProvider extends ModelProvider {
    public MiscModelProvider(PackOutput output) {
        super(output, KTTCraftMod.MODID);
    }
    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        super.registerModels(blockModels, itemModels);
        itemModels.generateFlatItem(ItemRegistries.OST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistries.OST_DISC_1.get(), ModelTemplates.FLAT_ITEM);
    }
    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ItemRegistries.ITEMS.getEntries().stream();
    }
    /*
    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks();
    }
     */
}
