package com.teamast.ktcmod.world.item.misc

import com.teamast.ktcmod.KTCMod
import com.teamast.ktcmod.KTCModConfig
import com.teamast.ktcmod.world.item.ItemRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
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
            val useTimes = if (player.isShiftKeyDown) stack.count else 1
            stack.shrink(useTimes)

            repeat(useTimes) {
                val count = level.random.nextInt(3) + 1
                val selectedToGiven = if (KTCModConfig.ENABLE_PITY.get()) {
                    getRandomDiscsWithPity(serverPlayer, level, count)
                } else {
                    getRandomDiscs(level, count)
                }
                for (item in selectedToGiven) {
                    val stackToGive = ItemStack(item)
                    if (!serverPlayer.inventory.add(stackToGive)) {
                        serverPlayer.drop(stackToGive, false)
                    }
                }
            }
            serverPlayer.inventory.setChanged()
            level.playSound(
                null,
                player.x,
                player.y,
                player.z,
                SoundEvents.ITEM_PICKUP,
                SoundSource.PLAYERS,
                0.2F,
                ((player.random.nextFloat() - player.random.nextFloat()) * 0.7F + 1.0F) * 2.0F
            )
            return InteractionResult.SUCCESS_SERVER
        }
        return InteractionResult.SUCCESS
    }

    // 普通随机发放（无保底）
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

    // 带权重随机抽取并从池中移除
    private fun weightedPickWithoutReplacement(
        pool: MutableList<Item>,
        specialItem: Item,
        random: RandomSource
    ): Item {
        if (pool.isEmpty()) {
            throw IllegalStateException("Cannot pick from empty pool")
        }
        if (pool.size == 1) {
            return pool.removeAt(0)
        }

        val normalWeight = 0.99 / (pool.size - 1)
        val weights = pool.map { if (it == specialItem) 0.01 else normalWeight }

        val total = weights.sum()
        val r = random.nextDouble() * total

        var cumulative = 0.0
        for (i in pool.indices) {
            cumulative += weights[i]
            if (r <= cumulative) {
                return pool.removeAt(i)
            }
        }

        return pool.removeAt(pool.lastIndex)
    }

    // 保底机制建立
    private fun getRandomDiscsWithPity(
        player: ServerPlayer,
        level: Level,
        count: Int
    ): List<Item> {
        val random = level.random
        val result = mutableListOf<Item>()
        val allDiscs = ItemRegistries.DISCS.map { it.get() }.toMutableList()
        val disc21 = allDiscs[20]
        var pity = getPity(player)
        val threshold = KTCModConfig.PITY_THRESHOLD.get()
        var guaranteedUsed = false
        repeat(count) {
            // 判断是否触发保底（确保 disc21 仍在池中）
            val force21 = !guaranteedUsed && pity >= threshold && allDiscs.contains(disc21)

            val selected = if (force21) {
                guaranteedUsed = true
                allDiscs.remove(disc21)
                disc21
            } else {
                weightedPickWithoutReplacement(allDiscs, disc21, random)
            }

            result.add(selected)
        }
        // 更新 pity 计数
        if (result.contains(disc21)) {
            setPity(player, 0)
        } else {
            setPity(player, pity + 1)
        }
        return result
    }

    private fun getPity(player: ServerPlayer): Int {
        return player.persistentData.getIntOr(PITY_KEY, 0)
    }

    private fun setPity(player: ServerPlayer, value: Int) {
        player.persistentData.putInt(PITY_KEY, value)
    }

    companion object {
        private const val PITY_KEY = "${KTCMod.MODID}_disc21_pity"
    }
}
