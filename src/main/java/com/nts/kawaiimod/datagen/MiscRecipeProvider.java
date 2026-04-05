package com.nts.kawaiimod.datagen;

import com.nts.kawaiimod.KTTCraftMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class MiscRecipeProvider extends RecipeProvider {
    public MiscRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }
    @Override
    protected void buildRecipes() {
    }
    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new MiscRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "KTTCraftMod RecipeGenerator";
        }
    }
}
