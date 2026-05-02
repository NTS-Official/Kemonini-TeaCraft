package com.teamast.ktcmod.world.effect.virus

import com.teamast.ktcmod.KTCMod
import com.teamast.ktcmod.world.effect.EffectRegistries
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent

@EventBusSubscriber(modid = KTCMod.MODID, value = [Dist.CLIENT])
class KeyMutator : MobEffect(MobEffectCategory.HARMFUL, -26215) {
    init {
        this.addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            Identifier.fromNamespaceAndPath(KTCMod.MODID, "effect.key_mutator_0"),
            -0.008,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )
        this.addAttributeModifier(
            Attributes.ATTACK_DAMAGE,
            Identifier.fromNamespaceAndPath(KTCMod.MODID, "effect.key_mutator_1"),
            -0.05,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )
        this.addAttributeModifier(
            Attributes.ATTACK_SPEED,
            Identifier.fromNamespaceAndPath(KTCMod.MODID, "effect.key_mutator_2"),
            -0.02,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )
    }

    companion object {
        @JvmStatic
        @SubscribeEvent
        fun registerMobEffectExtensions(event: RegisterClientExtensionsEvent) {
            event.registerMobEffect(object : IClientMobEffectExtensions {
                override fun isVisibleInInventory(effect: MobEffectInstance): Boolean {
                    return false
                }

                override fun renderInventoryText(
                    instance: MobEffectInstance,
                    screen: AbstractContainerScreen<*>,
                    guiGraphics: GuiGraphicsExtractor,
                    x: Int,
                    y: Int,
                    blitOffset: Int
                ): Boolean {
                    return false
                }

                override fun isVisibleInGui(effect: MobEffectInstance): Boolean {
                    return true
                }
            }, EffectRegistries.KEY_MUTATOR.get())
        }
    }
}
