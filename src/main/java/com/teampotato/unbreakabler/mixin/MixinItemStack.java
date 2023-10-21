package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Unbreakabler;
import com.teampotato.unbreakabler.api.ExtendedItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements ExtendedItemStack {

    @Shadow public abstract boolean isDamageableItem();

    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean isEmpty();

    @Shadow public abstract CompoundNBT getOrCreateTag();

    @Unique private boolean unbreakabler$shouldBeUnbreakable;
    @Unique private boolean unbreakabler$checked;
    @Unique private boolean unbreakabler$unbreakable;

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void removeDurability(World world, Entity p_77945_2_, int p_77945_3_, boolean p_77945_4_, CallbackInfo ci) {
        if (this.unbreakabler$shouldBeUnbreakable() && !this.unbreakabler$unbreakable()) {
            this.getOrCreateTag().putBoolean("Unbreakable", true);
            this.unbreakabler$setUnbreakable(true);
        }
        if (!this.unbreakabler$checked()) {
            this.unbreakabler$setChecked(true);
            Item item = this.getItem();
            String desc = item.getDescriptionId();
            if (desc.contains("unregistered_sadface")) return;
            String[] descStrings = desc.split("\\.");
            if (Unbreakabler.ONLY_WORKS_ON_ARMORS_AND_WEAPONS.get() && !(item instanceof ArmorItem || item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem || item instanceof ElytraItem)) return;
            if (!Unbreakabler.ENABLE_MOD.get() || world.isClientSide() || this.isEmpty() || !this.isDamageableItem()) return;
            if (!Unbreakabler.VALID_NAMESPACE.get().isEmpty() && !Unbreakabler.VALID_NAMESPACE.get().contains(descStrings[1])) return;
            String regName = descStrings[1] + ":" + descStrings[2];
            List<? extends String> list = Unbreakabler.BLACKLIST_OR_WHITELIST_DAMAGEABLE_ITEMS.get();
            if (Unbreakabler.MODE.get().equals("B")) {
                if (list.contains(regName)) return;
            } else {
                if (!list.contains(regName)) return;
            }
            this.unbreakabler$setShouldBeUnbreakable(true);
        }
    }

    @Override
    public void unbreakabler$setShouldBeUnbreakable(boolean shouldBeUnbreakable) {
        this.unbreakabler$shouldBeUnbreakable = shouldBeUnbreakable;
    }

    @Override
    public boolean unbreakabler$shouldBeUnbreakable() {
        return this.unbreakabler$shouldBeUnbreakable;
    }

    @Override
    public void unbreakabler$setChecked(boolean checked) {
        this.unbreakabler$checked = checked;
    }

    @Override
    public boolean unbreakabler$checked() {
        return this.unbreakabler$checked;
    }

    @Override
    public void unbreakabler$setUnbreakable(boolean unbreakable) {
        this.unbreakabler$unbreakable = unbreakable;
    }

    @Override
    public boolean unbreakabler$unbreakable() {
        return this.unbreakabler$unbreakable;
    }
}
