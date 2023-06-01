package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Unbreakabler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemStack.class, priority = 93)
public class MixinItemStack {
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void removeDurability(Level world, Entity entity, int slot, boolean isSelected, CallbackInfo ci) {
        Unbreakabler.giveUnbreakableTag((ItemStack)(Object)this, world);
    }
}
