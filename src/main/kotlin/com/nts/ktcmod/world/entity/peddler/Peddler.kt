package com.nts.ktcmod.world.entity.peddler

import com.nts.ktcmod.world.item.ItemRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.trading.ItemCost
import net.minecraft.world.item.trading.MerchantOffer
import net.minecraft.world.level.Level

class Peddler(entityType: EntityType<out Peddler>, level: Level) : WanderingTrader(entityType, level) {
    enum class PeddlerOffers(inp: ItemCost, res: ItemStack, use: Int, xp: Int, mult: Float) {
        COIN_FOR_EMERALD(
            ItemCost(ItemRegistries.getMiscItem(ItemRegistries.KTCModItems.COIN).get(), 8),
            ItemStack(Items.EMERALD),
            16,
            2,
            PRICE_MULTIPLIER
        );
        val offer: MerchantOffer = MerchantOffer(inp, res, use, xp, mult)
    }

    override fun updateTrades(level: ServerLevel) {
        val offers = getOffers()
        offers.clear()
        // Optional sink: collect old coins back for emeralds.
        offers.add(PeddlerOffers.COIN_FOR_EMERALD.offer)
    }

    companion object {
        private const val PRICE_MULTIPLIER = 0.05f
    }
}
