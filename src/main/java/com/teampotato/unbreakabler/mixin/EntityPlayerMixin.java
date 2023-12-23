package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Config;
import com.teampotato.unbreakabler.api.ExtendedItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {
    @Shadow private ItemStack itemInUse;

    public EntityPlayerMixin(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onUsingTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;I)V"))
    private void onUseItem(CallbackInfo ci) {
        if (!Config.enabled) return;
        if (this.itemInUse.isItemStackDamageable() && ((ExtendedItem)this.itemInUse.getItem()).unbreakabler$shouldBeUnbreakable()) {
            if (!this.itemInUse.hasTagCompound()) this.itemInUse.setTagCompound(new NBTTagCompound());
            this.itemInUse.getTagCompound().setBoolean("Unbreakble", true);
        }
    }
}
