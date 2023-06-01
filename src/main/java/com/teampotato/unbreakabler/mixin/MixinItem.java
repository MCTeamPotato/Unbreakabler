package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Unbreakabler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemStack.class, priority = 93)
public abstract class MixinItem {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void removeDurability(World world, Entity entity, int slot, boolean isSelected, CallbackInfo ci) {
        Unbreakabler.giveUnbreakableTag((ItemStack) (Object)this, world);
    }
}
