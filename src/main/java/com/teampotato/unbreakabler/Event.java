package com.teampotato.unbreakabler;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private static final Map<Item, Boolean> ITEMS_CACHE = new HashMap<>();

    private static final Logger LOGGER = LogManager.getLogger(Event.class);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        ItemStack currentItem = event.player.inventory.getCurrentItem();
        if (currentItem == null) return;
        if (!Config.enabled) return;
        boolean generateCache = ITEMS_CACHE.computeIfAbsent(currentItem.getItem(), item -> {
            if (Config.VALID_MODS.isEmpty()) {
                if (!Config.mode.equals("B")) {
                    return !Config.ITEMS.contains(item.getUnlocalizedName());
                } else {
                    return Config.ITEMS.contains(item.getUnlocalizedName());
                }
            } else {
                return !Config.VALID_MODS.contains(item.delegate.name().split(":")[0]);
            }
        });
        if (currentItem.isItemStackDamageable() && generateCache) {
            if (!currentItem.hasTagCompound()) currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setBoolean("Unbreakable", true);
        }
    }
}
