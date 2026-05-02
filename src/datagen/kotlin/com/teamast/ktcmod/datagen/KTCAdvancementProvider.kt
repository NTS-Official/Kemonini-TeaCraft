package com.teamast.ktcmod.datagen

import com.teamast.ktcmod.KTCMod
import com.teamast.ktcmod.world.item.ItemRegistries
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.core.HolderLookup
import net.minecraft.data.advancements.AdvancementSubProvider
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.util.function.Consumer

class KTCAdvancementProvider : AdvancementSubProvider {
    override fun generate(registries: HolderLookup.Provider, output: Consumer<AdvancementHolder>) {
        Advancement.Builder.advancement()
            .display(
                ItemRegistries.getDisc(21).get(),
                Component.translatable("advancements.${KTCMod.MODID}.final_disc.title"),
                Component.translatable("advancements.${KTCMod.MODID}.final_disc.description"),
                Identifier.withDefaultNamespace("gui/advancements/backgrounds/adventure"),
                AdvancementType.GOAL,
                true,
                true,
                false
            )
            .addCriterion("has_final_disc", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistries.getDisc(21).get()))
            .save(output, "final_disc")
    }
}

