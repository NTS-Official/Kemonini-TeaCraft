package com.nts.ktcmod.world.entity

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.world.entity.k.K
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
    val ENTITY_TYPES = DeferredRegister.create(
        Registries.ENTITY_TYPE,
        KTCMod.MODID
    )
    @JvmField
    val PEDDLER = ENTITY_TYPES.register("peddler") { ->
        EntityType.Builder.of(::Peddler, MobCategory.MISC)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(16)
            .fireImmune()
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KTCMod.MODID, "peddler"))) }
    val K = ENTITY_TYPES.register("k") { ->
        EntityType.Builder.of(::K, MobCategory.MISC)
        .sized(0.6f, 1.80f)
        .clientTrackingRange(16)
        .fireImmune()
        .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KTCMod.MODID, "k"))) }

    @JvmStatic
    fun register(eventBus: net.neoforged.bus.api.IEventBus) {
        ENTITY_TYPES.register(eventBus)
    }
}
