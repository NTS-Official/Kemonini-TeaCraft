package com.teamast.ktcmod.world.entity.k

import com.teamast.ktcmod.KTCMod
import com.teamast.ktcmod.world.entity.EntityRegistries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent

@EventBusSubscriber(modid = KTCMod.MODID) object KAttributes {
    @JvmStatic @SubscribeEvent fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
        event.put(EntityRegistries.K.get(), createPeddlerAttributes().build())
    }
    private fun createPeddlerAttributes(): AttributeSupplier.Builder {
        return LivingEntity.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 18.0)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .add(Attributes.FOLLOW_RANGE, 48.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
    }
}