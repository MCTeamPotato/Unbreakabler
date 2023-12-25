package com.teampotato.unbreakabler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Unbreakabler.MODID, useMetadata = true)
public class Unbreakabler {
    public static final String MODID = "unbreakabler";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig(event.getSuggestedConfigurationFile());
    }
}
