package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.Unbreakabler;
import com.teampotato.unbreakabler.api.ExtendedItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
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

    @Shadow public abstract CompoundTag getOrCreateTag();

    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean isEmpty();

    @Unique private boolean unbreakabler$shouldBeUnbreakable;
    @Unique private boolean unbreakabler$checked;

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void removeDurability(Level world, Entity p_41668_, int p_41669_, boolean p_41670_, CallbackInfo ci) {
        if (this.unbreakabler$shouldBeUnbreakable() && this.isDamageableItem()) this.getOrCreateTag().putBoolean("Unbreakable", true);
        if (!this.unbreakabler$checked()) {
            this.unbreakabler$setChecked(true);
            Item item = this.getItem();
            String desc = item.getDescriptionId();
            if (desc.contains("unregistered_sadface")) return;
            String[] descStrings = desc.split("\\.");
            if (!Unbreakabler.ENABLE_MOD.get() || world.isClientSide() || this.isEmpty() || !this.isDamageableItem()) return;
            if (Unbreakabler.ONLY_WORKS_ON_WEAPONS.get() && !(item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem)) return;
            if (Unbreakabler.ONLY_WORK_ON_ARMORS.get() && !(item instanceof ArmorItem || item instanceof ElytraItem)) return;
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
}
