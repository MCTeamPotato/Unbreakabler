package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Unbreakabler;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Item.class, priority = 93)
public class MixinItem {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void removeDurability(ItemStack stack, World world, Entity entity, int slot, boolean isSelected, CallbackInfo ci) {
        Unbreakabler.giveUnbreakableTag(stack, world);
    }
}
