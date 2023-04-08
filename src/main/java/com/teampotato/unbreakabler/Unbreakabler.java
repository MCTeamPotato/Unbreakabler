package com.teampotato.unbreakabler;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mod(Unbreakabler.MOD_ID)
public class Unbreakabler {
    public static final String MOD_ID = "unbreakabler";

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue REMOVE_WEAPONS_AND_ARMORS_DURABILITY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS;
    public static final ForgeConfigSpec.ConfigValue<String> MODE;

    static {
        List<? extends String> stringList = Lists.newArrayList();
        Predicate<Object> validator = o -> o instanceof String;

        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.comment("Unbreakabler").push("Unbreakable Age");
        REMOVE_WEAPONS_AND_ARMORS_DURABILITY = CONFIG_BUILDER.define("enable mod", true);
        MODE = CONFIG_BUILDER.comment("Use 'B' for Blacklist, use any other word(s) (e.g.  I love you) for whitelist").define("Blacklist or whitelist", "B");
        BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS = CONFIG_BUILDER.defineList("Unbreakable tag blacklist/whitelist items", stringList, validator);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }

    public static void giveUnbreakableTag(ItemStack itemStack, World world) {
        if (!itemStack.isDamageableItem() || !REMOVE_WEAPONS_AND_ARMORS_DURABILITY.get() || world.isClientSide || itemStack.getItem().getRegistryName() == null) return;
        String regName = itemStack.getItem().getRegistryName().toString();
        Stream<? extends String> list = BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS.get().stream();
        if (MODE.get().equals("B")) {
            if (list.anyMatch(regName::equals)) return;
        } else {
            if (list.noneMatch(regName::equals)) return;
        }
        itemStack.getOrCreateTag().putBoolean("Unbreakable", true);
    }
}
