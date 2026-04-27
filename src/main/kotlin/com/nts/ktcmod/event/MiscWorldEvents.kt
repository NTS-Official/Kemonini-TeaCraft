package com.nts.ktcmod.event

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.KTCMod.Companion.LOGGER
import com.nts.ktcmod.KTCModConfig
import com.nts.ktcmod.world.entity.EntityRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.equine.TraderLlama
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent

@EventBusSubscriber(modid = KTCMod.MODID)
object MiscWorldEvents {

    @JvmStatic
    @SubscribeEvent
    fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
        val entity = event.entity

        // Only replace vanilla wandering traders; custom subclasses (like our Peddler) must be ignored
        // to avoid recursive replacement loops when addFreshEntity triggers this event again.
        if (entity !is WanderingTrader || entity.type != EntityType.WANDERING_TRADER) {
            return
        }

        if (entity.isPassenger || entity.isVehicle) {
            return
        }

        if (KTCModConfig.REPLACE_WANDERING_TRADER_WITH_PEDDLER.get()) {
            replaceWanderingTraderWithPeddler(event, entity)
        }
    }

    private fun replaceWanderingTraderWithPeddler(event: EntityJoinLevelEvent, trader: WanderingTrader) {
        val level = trader.level()

        if (level !is ServerLevel) {
            return
        }

        val pos = trader.blockPosition()
        val peddler = EntityRegistries.PEDDLER.get().create(level, EntitySpawnReason.EVENT) ?: run {
            LOGGER.warn("Failed to create Peddler entity")
            return
        }

        peddler.setPos(trader.x, trader.y, trader.z)
        peddler.yRot = trader.yRot
        peddler.xRot = trader.xRot
        peddler.despawnDelay = trader.despawnDelay

        removeTraderLlamas(level, trader)

        level.addFreshEntity(peddler)
        trader.discard()

        event.isCanceled = true
        LOGGER.debug("Replaced Wandering Trader with Suichi at {}", pos)
    }

    private fun removeTraderLlamas(level: ServerLevel, trader: WanderingTrader) {
        val searchRadius = 10
        val traderPos = trader.blockPosition()

        for (entity in level.getAllEntities()) {
            if (entity is TraderLlama) {
                val distance = entity.blockPosition().distSqr(traderPos)
                if (distance <= (searchRadius * searchRadius).toDouble()) {
                    entity.discard()
                    LOGGER.debug("Removed TraderLlama at {}", entity.blockPosition())
                }
            }
        }
    }
}
