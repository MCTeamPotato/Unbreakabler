package com.teampotato.unbreakabler;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

@Mod(Unbreakabler.MOD_ID)
public class Unbreakabler {
    public static final String MOD_ID = "unbreakabler";

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue ENABLE_MOD, ONLY_WORKS_ON_ARMORS_AND_WEAPONS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS, VALID_NAMESPACE;
    public static final ForgeConfigSpec.ConfigValue<String> MODE;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.push("Unbreakabler");
        ENABLE_MOD = CONFIG_BUILDER.define("EnableMod", true);
        ONLY_WORKS_ON_ARMORS_AND_WEAPONS = CONFIG_BUILDER.define("OnlyWorksOnArmorsAndWeapons", false);
        MODE = CONFIG_BUILDER.comment("Use 'B' for Blacklist mode, use any other word(s) (e.g.  I love you) for whitelist mode").define("Mode", "B");
        BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS = CONFIG_BUILDER.comment("Unbreakable tag blacklist/whitelist items").defineList("Items", new ObjectArrayList<>(), o -> o instanceof String);
        VALID_NAMESPACE = CONFIG_BUILDER.comment("If this is defined, only the mod that has this modID will be unbreakable.").defineList("ValidMods", new ObjectArrayList<>(), o -> o instanceof String);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }

    public Unbreakabler() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG, MOD_ID + ".toml");
    }
}