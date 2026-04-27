package com.nts.ktcmod.world.entity.peddler

import com.nts.ktcmod.world.item.ItemRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.trading.ItemCost
import net.minecraft.world.item.trading.MerchantOffer
import net.minecraft.world.item.trading.MerchantOffers
import net.minecraft.world.item.trading.TradeSets
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level

class Peddler(entityType: EntityType<out Peddler>, level: Level) : WanderingTrader(entityType, level) {

    override fun updateTrades(level: ServerLevel) {
        val offers = getOffers()
        offers.clear()

        // Keep the trading UX close to villagers by reusing vanilla villager trade sets.
        addOffersFromTradeSet(level, offers, TradeSets.FARMER_LEVEL_1)
        addOffersFromTradeSet(level, offers, TradeSets.FARMER_LEVEL_2)
        addOffersFromTradeSet(level, offers, TradeSets.FARMER_LEVEL_3)

        // Add themed mod trades on top of the villager-like base trades.
        addSellOffer(offers, 2, ItemRegistries.getMiscItem(ItemRegistries.KTCModItems.BISCUIT).get(), 4, 16, 2)
        addSellOffer(offers, 4, ItemRegistries.getMiscItem(ItemRegistries.KTCModItems.TABLET).get(), 2, 12, 3)
        addSellOffer(offers, 7, ItemRegistries.getMiscItem(ItemRegistries.KTCModItems.TOOLKIT_1).get(), 1, 8, 4)
        addSellOffer(offers, 14, ItemRegistries.OST_BOX.get(), 1, 5, 8)
        addSellOffer(offers, 18, ItemRegistries.getDisc(1).get(), 1, 4, 10)
        addSellOffer(offers, 48, ItemRegistries.getDisc(21).get(), 1, 1, 20)

        // Optional sink: collect old coins back for emeralds.
        offers.add(
            MerchantOffer(
                ItemCost(ItemRegistries.getMiscItem(ItemRegistries.KTCModItems.COIN).get(), 8),
                ItemStack(Items.EMERALD),
                16,
                2,
                PRICE_MULTIPLIER
            )
        )
    }

    private fun addSellOffer(
        offers: MerchantOffers,
        emeraldCost: Int,
        sellItem: ItemLike,
        sellCount: Int,
        maxUses: Int,
        tradeXp: Int
    ) {
        offers.add(
            MerchantOffer(
                ItemCost(Items.EMERALD, emeraldCost),
                ItemStack(sellItem, sellCount),
                maxUses,
                tradeXp,
                PRICE_MULTIPLIER
            )
        )
    }

    companion object {
        private const val PRICE_MULTIPLIER = 0.05f
    }
}
