package com.teampotato.unbreakabler.mixin;

import com.teampotato.unbreakabler.api.ExtendedItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class MixinItem implements ExtendedItem {
    @Unique private boolean unbreakabler$shouldBeUnbreakable;

    @Override
    public void unbreakabler$setShouldBeUnbreakable(boolean shouldBeUnbreakable) {
        this.unbreakabler$shouldBeUnbreakable = shouldBeUnbreakable;
    }

    @Override
    public boolean unbreakabler$shouldBeUnbreakable() {
        return this.unbreakabler$shouldBeUnbreakable;
    }
}