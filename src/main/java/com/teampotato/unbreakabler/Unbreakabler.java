package com.teampotato.unbreakabler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Unbreakabler.MODID, dependencies = "after:spongemixins@[1.1.0,)", useMetadata = true)
public class Unbreakabler {
    public static final String MODID = "unbreakabler";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("[Unbreakabler] pre-initialized successfully");
        Config.initConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("[Unbreakabler] initialized successfully");
        Event playerEvent = new Event();
        FMLCommonHandler.instance().bus().register(playerEvent);
    }
}
