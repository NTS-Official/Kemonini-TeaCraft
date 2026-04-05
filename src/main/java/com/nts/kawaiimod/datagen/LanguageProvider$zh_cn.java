package com.nts.kawaiimod.datagen;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LanguageProvider$zh_cn extends LanguageProvider {
    public LanguageProvider$zh_cn(PackOutput output) {
        super(output, KTTCraftMod.MODID, "zh_cn");
    }
    @Override
    protected void addTranslations() {
        add("itemGroup.kemono_teatime.misc", "兽娘红茶馆：杂项");
        add("itemGroup.kemono_teatime.blocks", "兽娘红茶馆：方块");

        add("item.kemono_teatime.ost", "《兽娘红茶馆》原声带集");
        add("item.kemono_teatime.ost_disc_1", "01 - A cup of happiness");

        add("item_kemono_teatime.ost_disc_1.desc", "《兽娘红茶馆》原声带集 - A cup of happiness");

        add("logs.kemono_teatime.client", "您正在使用本模组的早期测试版本！如有任何bug请务必向开发者反馈！");
        add("logs.kemono_teatime.server", "<待定文本…>");
    }
}
