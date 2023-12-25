package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Config;
import com.teampotato.unbreakabler.api.ExtendedItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ExtendedItemStack {
    @Shadow public abstract Item getItem();
    @Shadow public abstract boolean isItemStackDamageable();
    @Shadow public abstract boolean hasTagCompound();
    @Shadow public abstract NBTTagCompound getTagCompound();
    @Shadow public abstract void setTagCompound(NBTTagCompound p_77982_1_);

    @Unique
    private boolean unbreakabler$isChecked;

    @Override
    public boolean unbreakabler$isChecked() {
        return this.unbreakabler$isChecked;
    }

    @Override
    public void unbreakabler$setIsChecked(boolean isChecked) {
        this.unbreakabler$isChecked = isChecked;
    }

    @Unique
    private static final Map<Item, Boolean> ITEMS_CACHE = new HashMap<>();

    @Inject(method = "damageItem", at = @At("HEAD"))
    private void onDamageItem(int p_77972_1_, EntityLivingBase p_77972_2_, CallbackInfo ci) {
        if (this.unbreakabler$isChecked()) return;
        this.unbreakabler$setIsChecked(true);
        if (!Config.enabled) return;
        boolean getCachedValue = ITEMS_CACHE.computeIfAbsent(this.getItem(), item -> {
            if (Config.VALID_MODS.isEmpty()) {
                if (Config.mode.equals("B")) {
                    return !Config.ITEMS.contains(item.getUnlocalizedName());
                } else {
                    return Config.ITEMS.contains(item.getUnlocalizedName());
                }
            } else {
                return Config.VALID_MODS.contains(item.delegate.name().split(":")[0]);
            }
        });
        if (this.isItemStackDamageable() && getCachedValue) {
            if (!this.hasTagCompound()) this.setTagCompound(new NBTTagCompound());
            this.getTagCompound().setBoolean("Unbreakable", true);
        }
    }
}
