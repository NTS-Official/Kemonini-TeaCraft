package com.nts.ktcmod.world.entity

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.world.entity.peddler.Peddler
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object EntityRegistries {
    @JvmField
    val ENTITY_TYPES: DeferredRegister<EntityType<*>> = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        KTCMod.MODID
    )

    @JvmField
    val PEDDLER: DeferredHolder<EntityType<*>, EntityType<Peddler>> = ENTITY_TYPES.register(
        "peddler"
    ) { ->
        EntityType.Builder.of(::Peddler, MobCategory.MISC)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(10)
            .fireImmune()
            .build(ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(KTCMod.MODID, "peddler")
            ))
    }

    @JvmStatic
    fun register(eventBus: net.neoforged.bus.api.IEventBus) {
        ENTITY_TYPES.register(eventBus)
    }
}
