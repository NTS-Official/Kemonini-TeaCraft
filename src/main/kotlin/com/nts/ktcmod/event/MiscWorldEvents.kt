package com.nts.ktcmod.event

import com.nts.ktcmod.KTCMod
import com.nts.ktcmod.KTCMod.Companion.LOGGER
import com.nts.ktcmod.KTCModConfig
import net.minecraft.world.entity.EntityType
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent

/**
 * 事件处理器：处理世界生成相关事件
 */
@EventBusSubscriber(modid = KTCMod.MODID)
object MiscWorldEvents {


    /**
     * 处理实体加入世界事件
     * 阻止流浪商人自然生成（当配置启用时）
     */
    @SubscribeEvent
    fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
        if (KTCModConfig.DISABLE_WANDERING_TRADER.get() && event.entity.type === EntityType.WANDERING_TRADER) {
            event.isCanceled = true
            LOGGER.debug("Blocked Wandering Trader spawn due to config setting.")
        }
    }
}
