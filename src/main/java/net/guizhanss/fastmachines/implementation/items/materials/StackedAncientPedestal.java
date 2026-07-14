package net.guizhanss.fastmachines.implementation.items.materials;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun5.core.services.sounds.SoundEffect;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.ItemUtils;

import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;
import net.guizhanss.fastmachines.libs.guizhanlib.utils.InventoryUtil;
import net.guizhanss.fastmachines.utils.items.SlimefunItemExt;

public class StackedAncientPedestal extends UnplaceableBlock {

    public StackedAncientPedestal(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();
            Player p = e.getPlayer();
            SlimefunItemStack pedestal = SlimefunItems.ANCIENT_PEDESTAL;
            if (SlimefunItemExt.isDisabledIn(pedestal.item(), p.getWorld())) {
                return;
            }
            ItemUtils.consumeItem(e.getItem(), true);
            InventoryUtil.push(p, Items.edit(pedestal, editor -> editor.amount(4)));
            SoundEffect.ANCIENT_ALTAR_START_SOUND.playFor(p);
        };
    }
}
