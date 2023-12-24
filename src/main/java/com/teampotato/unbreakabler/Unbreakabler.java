package com.teampotato.unbreakabler;

import com.teampotato.unbreakabler.api.ExtendedItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;

import java.util.Arrays;
import java.util.List;

@Mod(modid = Unbreakabler.MODID, dependencies = "after:spongemixins@[1.1.0,)")
public class Unbreakabler {
    public static final String MODID = "unbreakabler";

    public Unbreakabler() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (!Config.enabled) return;
        boolean isModsSpecific = Config.validMods.length != 0;
        boolean isBlacklistMode = Config.mode.equals("B");
        List<String> validMods = Arrays.asList(Config.validMods);
        List<String> items = Arrays.asList(Config.items);
        GameData.getItemRegistry().typeSafeIterable().forEach(item -> {
            String unlocalizedName = item.getUnlocalizedName();
            if (isModsSpecific) {
                ((ExtendedItem)item).unbreakabler$setShouldBeUnbreakable(validMods.contains(unlocalizedName));
            } else {
                if (isBlacklistMode) {
                    ((ExtendedItem)item).unbreakabler$setShouldBeUnbreakable(!items.contains(unlocalizedName));
                } else {
                    ((ExtendedItem)item).unbreakabler$setShouldBeUnbreakable(items.contains(unlocalizedName));
                }
            }
        });
    }
}
