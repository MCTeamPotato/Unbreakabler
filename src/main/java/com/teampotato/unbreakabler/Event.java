package com.teampotato.unbreakabler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private static final Map<Item, Boolean> ITEMS_CACHE = new HashMap<>();
    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        ItemStack itemInUse = event.player.getItemInUse();
        if (itemInUse == null || itemInUse.stackSize == 0) return;
        String name = itemInUse.getItem().getUnlocalizedName();
        System.out.println(name);
        if (!Config.enabled) return;
        if (itemInUse.isItemStackDamageable() && ITEMS_CACHE.computeIfAbsent(itemInUse.getItem(), item -> {
            if (Config.VALID_MODS.isEmpty()) {
                if (Config.mode.equals("B")) {
                    return Config.ITEMS.stream().noneMatch(string -> item.getUnlocalizedName().contains(string));
                } else {
                    return Config.ITEMS.stream().anyMatch(string -> item.getUnlocalizedName().contains(string));
                }
            } else {
                return Config.VALID_MODS.stream().anyMatch(string -> item.getUnlocalizedName().contains(string));
            }
        })) {
            if (!itemInUse.hasTagCompound())itemInUse.setTagCompound(new NBTTagCompound());
            itemInUse.getTagCompound().setBoolean("Unbreakble", true);
        }
    }
}
