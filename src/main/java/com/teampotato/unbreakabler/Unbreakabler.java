package com.teampotato.unbreakabler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = "unbreakabler", useMetadata = true)
public class Unbreakabler {
    public Unbreakabler() {
        MinecraftForge.EVENT_BUS.register(this);
        System.out.println("Unbreakabler loaded.");
    }

    private static final Map<Item, Boolean> ITEMS_CACHE = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!Config.enabled) return;
        for (ItemStack itemStack : event.player.inventory.armorInventory) {
            process(itemStack);
        }
        for (ItemStack itemStack : event.player.inventory.mainInventory) {
            process(itemStack);
        }
    }

    private static void process(ItemStack itemStack) {
        if (itemStack == null) return;
        boolean getCachedValue = ITEMS_CACHE.computeIfAbsent(itemStack.getItem(), item -> {
            if (Config.VALID_MODS.isEmpty()) {
                if (Config.mode.equals("B")) {
                    return !Config.ITEMS.contains(item.getUnlocalizedName());
                } else {
                    return Config.ITEMS.contains(item.getUnlocalizedName());
                }
            } else {
                return Config.VALID_MODS.contains(item.delegate.getResourceName().getResourceDomain());
            }
        });
        if (itemStack.isItemStackDamageable() && getCachedValue) {
            if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
            assert itemStack.getTagCompound() != null;
            itemStack.getTagCompound().setBoolean("Unbreakable", true);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig(event.getSuggestedConfigurationFile());
    }
}