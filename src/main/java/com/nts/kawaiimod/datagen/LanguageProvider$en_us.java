package com.nts.kawaiimod.datagen;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LanguageProvider$en_us extends LanguageProvider {
    public LanguageProvider$en_us(PackOutput output) {
        super(output, KTTCraftMod.MODID, "en_us");
    }
    @Override
    protected void addTranslations() {
        add("itemGroup.kemono_teatime.misc", "Kemonini TeaCraft: Miscs");
        add("itemGroup.kemono_teatime.blocks", "Kemonini TeaCraft: Blocks");

        add("item.kemono_teatime.ost", "Kemono Teatime OST");
        add("item.kemono_teatime.ost_disc_1", "01 - A cup of happiness");

        add("item_kemono_teatime.ost_disc_1.desc", "Melodious Moments: Kemono Teatime OST - A cup of happiness");

        add("logs.kemono_teatime.client", "YOU'RE USING THE EARLY ACCESS VERSION! THERE MAY HAVE SOME BUGS THAT COULD LEAD TO GAME CRASHES! PLEASE REPORT THEM TO THE DEVELOPER!");
        add("logs.kemono_teatime.server", "<infos to be filled here>");
    }
}
