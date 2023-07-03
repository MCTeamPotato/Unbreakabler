package com.teampotato.unbreakabler;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod(Unbreakabler.MOD_ID)
public class Unbreakabler {
    public static final String MOD_ID = "unbreakabler";

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue REMOVE_WEAPONS_AND_ARMORS_DURABILITY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS, VALID_NAMESPACE;
    public static final ForgeConfigSpec.ConfigValue<String> MODE;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.push("Unbreakabler");
        REMOVE_WEAPONS_AND_ARMORS_DURABILITY = CONFIG_BUILDER.define("enable mod", true);
        MODE = CONFIG_BUILDER.comment("Use 'B' for Blacklist mode, use any other word(s) (e.g.  I love you) for whitelist mode").define("Mode", "B");
        BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS = CONFIG_BUILDER.comment("Unbreakable tag blacklist/whitelist items").defineList("Items", Lists.newArrayList(), o -> o instanceof String);
        VALID_NAMESPACE = CONFIG_BUILDER.comment("If this is defined, only the mod that has this modID will be unbreakable.").defineList("ValidMods", Lists.newArrayList(), o -> o instanceof String);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }
    
    public Unbreakabler(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

    public static void giveUnbreakableTag(ItemStack itemStack, Level world) {
        if (itemStack.isEmpty() || !itemStack.isDamageableItem() || !REMOVE_WEAPONS_AND_ARMORS_DURABILITY.get() || world.isClientSide) return;
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if (location == null) return;
        String regName = location.toString();
        if (!VALID_NAMESPACE.get().isEmpty() && !VALID_NAMESPACE.get().contains(location.getNamespace())) return;
        List<? extends String> list = BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS.get();
        if (MODE.get().equals("B")) {
            if (list.contains(regName)) return;
        } else {
            if (!list.contains(regName)) return;
        }
        itemStack.getOrCreateTag().putBoolean("Unbreakable", true);
    }
}