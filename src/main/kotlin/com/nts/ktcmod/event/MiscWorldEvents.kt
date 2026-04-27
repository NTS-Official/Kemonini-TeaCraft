package com.nts.ktcmod.event

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.KTCMod.Companion.LOGGER
import com.nts.ktcmod.KTCModConfig
import com.nts.ktcmod.entity.EntityRegistries
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntitySpawnReason
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

        if (entity !is WanderingTrader) {
            return
        }

        val level = entity.level()

        if (entity.isPassenger || entity.isVehicle) {
            return
        }

        if (KTCModConfig.REPLACE_WANDERING_TRADER_WITH_PEDDLER.get()) {
            replaceWanderingTraderWithPeddler(event, entity)
        } else if (KTCModConfig.DISABLE_WANDERING_TRADER.get()) {
            event.isCanceled = true
            LOGGER.debug("Blocked Wandering Trader spawn due to config setting.")
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
