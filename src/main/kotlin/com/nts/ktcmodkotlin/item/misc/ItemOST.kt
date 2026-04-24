package com.nts.ktcmodkotlin.item.misc

import com.nts.ktcmodkotlin.item.ItemRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemOST(props: Properties) : Item(props) {
    // 覆写use方法，用于随机发放1~3个唱片
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        if (!level.isClientSide) {
            val serverPlayer = player as? ServerPlayer ?: return InteractionResult.PASS
            stack.shrink(1)
            val count = level.random.nextInt(3) + 1
            val selectedToGiven = getRandomDiscs(level, count)
            for (item in selectedToGiven) {
                val stackToGive = ItemStack(item)
                if (!serverPlayer.inventory.add(stackToGive)) {
                    serverPlayer.drop(stackToGive, false)
                }
            }
            serverPlayer.inventory.setChanged()
            return InteractionResult.SUCCESS_SERVER
        }
        return InteractionResult.SUCCESS
    }

    private fun getRandomDiscs(level: Level, count: Int): List<Item> {
        val random = level.random

        // 构建权重池，#21为1%概率外，其余唱片平分剩余概率
        val pool = mutableListOf<Pair<Item, Double>>()

        val allDiscs = ItemRegistries.DISCS.map { it.get() }

        val normalWeight = 0.99 / (allDiscs.size - 1)

        for ((index, item) in allDiscs.withIndex()) {
            if (index == 20) { // 即唱片#21
                pool.add(item to 0.01)
            } else {
                pool.add(item to normalWeight)
            }
        }

        val result = mutableListOf<Item>()

        repeat(count) {
            val totalWeight = pool.sumOf { it.second }
            val r = random.nextDouble() * totalWeight

            var cumulative = 0.0
            var selectedIndex = 0

            for ((i, pair) in pool.withIndex()) {
                cumulative += pair.second
                if (r <= cumulative) {
                    selectedIndex = i
                    break
                }
            }

            val selected = pool.removeAt(selectedIndex)
            result.add(selected.first)
        }

        return result
    }
}
