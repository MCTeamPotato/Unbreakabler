package com.teampotato.unbreakabler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
    }
}
