package net.guizhanss.fastmachines.implementation.items.machines.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.bukkit.entity.Player;

import io.github.thebusybiscuit.slimefun5.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun5.api.researches.Research;
import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.Recipe;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;
import net.guizhanss.fastmachines.libs.guizhanlib.utils.InventoryUtil;
import net.guizhanss.fastmachines.utils.MachineUtils;
import net.guizhanss.fastmachines.utils.items.GuiItems;

public class FastMachineCache {

    private final BaseFastMachine machine;
    private final BlockMenu menu;
    private final BlockPosition pos;

    private int page = -1;
    private int invChecksum = 0;
    private boolean needUpdateMenu = true;
    private Map<Recipe, Integer> availableRecipes = new LinkedHashMap<>();
    private Recipe selectedRecipe = null;

    public FastMachineCache(BaseFastMachine machine, BlockMenu menu) {
        this.machine = machine;
        this.menu = menu;
        this.pos = Items.position(menu.getLocation());

        menu.addMenuClickHandler(BaseFastMachine.SCROLL_UP_SLOT, (p, slot, item, action) -> {
            page--;
            needUpdateMenu = true;
            return false;
        });
        menu.addMenuClickHandler(BaseFastMachine.SCROLL_DOWN_SLOT, (p, slot, item, action) -> {
            page++;
            needUpdateMenu = true;
            return false;
        });
        menu.addMenuClickHandler(BaseFastMachine.CRAFT_SLOT, (p, slot, item, action) -> {
            int amount;
            if (action.isShiftClicked() && action.isRightClicked()) {
                amount = Integer.MAX_VALUE;
            } else if (action.isShiftClicked()) {
                amount = 64;
            } else if (action.isRightClicked()) {
                amount = 16;
            } else {
                amount = 1;
            }
            craft(p, amount);
            return false;
        });
    }

    public void tick() {
        if (!menu.hasViewer()) {
            return;
        }

        generateOutputs();
        if (needUpdateMenu) {
            updateMenu();
        }
    }

    private void generateOutputs() {
        Map<ItemWrapper, Integer> inputs = MachineUtils.countItems(menu, BaseFastMachine.INPUT_SLOTS);

        // if checksum does not need update
        if (inputs.hashCode() == invChecksum) {
            return;
        }
        invChecksum = inputs.hashCode();

        FastMachines.debug("Checking outputs for " + machine.getClass().getSimpleName() + " at " + pos);
        FastMachines.debug("Inputs: " + inputs);
        FastMachines.debug("Recipe count: " + machine.getRecipes().size());

        List<Recipe> possibleRecipes = new ArrayList<>();
        for (Recipe recipe : machine.getRecipes()) {
            if (recipe.isDisabledIn(pos.getWorld())) {
                continue;
            }
            if (allInputsPresent(recipe, inputs)) {
                possibleRecipes.add(recipe);
            }
        }

        FastMachines.debug("Possible recipes: " + possibleRecipes);

        Map<Recipe, Integer> result = new LinkedHashMap<>();
        for (Recipe recipe : possibleRecipes) {
            int amount = minCraftable(recipe, inputs);
            if (amount > 0) {
                result.put(recipe, amount);
            }
        }
        availableRecipes = result;

        FastMachines.debug("Available recipes: " + availableRecipes);
        needUpdateMenu = true;
    }

    private static boolean allInputsPresent(Recipe recipe, Map<ItemWrapper, Integer> inputs) {
        for (RecipeChoice choice : recipe.getInputs()) {
            boolean matched = false;
            for (ItemWrapper inputItem : inputs.keySet()) {
                if (choice.isValidItem(inputItem.getBaseItem())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    private static int minCraftable(Recipe recipe, Map<ItemWrapper, Integer> inputs) {
        int min = Integer.MAX_VALUE;
        boolean any = false;
        for (RecipeChoice choice : recipe.getInputs()) {
            min = Math.min(min, choice.maxCraftableAmount(inputs));
            any = true;
        }
        return any ? min : 0;
    }

    private void updateMenu() {
        needUpdateMenu = false;

        // no available recipes, clear preview
        if (availableRecipes.isEmpty()) {
            for (int slot : BaseFastMachine.PREVIEW_SLOTS) {
                menu.replaceExistingItem(slot, ChestMenuUtils.getBackground());
                menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
            }
            updateSelectedRecipe();
            return;
        }

        List<Map.Entry<Recipe, Integer>> recipesList = new ArrayList<>(availableRecipes.entrySet());
        int totalPages = (recipesList.size() + BaseFastMachine.ITEMS_PER_PAGE - 1) / BaseFastMachine.ITEMS_PER_PAGE;
        page = Math.max(1, Math.min(page, totalPages));
        int startIndex = (page - 1) * BaseFastMachine.ITEMS_PER_PAGE;

        for (int index = 0; index < BaseFastMachine.PREVIEW_SLOTS.length; index++) {
            int slot = BaseFastMachine.PREVIEW_SLOTS[index];
            int recipeIndex = startIndex + index;
            if (recipeIndex >= recipesList.size()) {
                menu.replaceExistingItem(slot, ChestMenuUtils.getBackground());
                menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
                continue;
            }

            Recipe recipe = recipesList.get(recipeIndex).getKey();
            menu.replaceExistingItem(slot, GuiItems.toDisplayItem(recipe.getOutput(pos.getWorld())));
            menu.addMenuClickHandler(slot, (p, s, item, action) -> {
                selectedRecipe = recipe;
                updateSelectedRecipe();
                return false;
            });
        }

        updateSelectedRecipe();
    }

    private void updateSelectedRecipe() {
        org.bukkit.inventory.ItemStack displayItem = selectedRecipe != null
            ? GuiItems.toDisplayItem(selectedRecipe.getOutput(pos.getWorld()))
            : BaseFastMachine.NO_ITEM;
        menu.replaceExistingItem(BaseFastMachine.CHOICE_SLOT, displayItem);
    }

    private void craft(Player p, int expectCrafts) {
        Map<ItemWrapper, Integer> inputs = MachineUtils.countItems(menu, BaseFastMachine.INPUT_SLOTS);

        // check if a recipe is selected
        Recipe recipe = selectedRecipe;
        if (recipe == null) {
            return;
        }

        // calculate and check craftable times
        int maxCraftable = minCraftable(recipe, inputs);

        int actualCrafts = Math.min(maxCraftable, expectCrafts);
        if (actualCrafts <= 0) {
            FastMachines.getLocalization().sendMessage(p, "not-enough-materials");
            return;
        }

        // check if recipe is available for the player
        if (FastMachines.getConfigService().getFmRequireSfResearch().getValue()) {
            Set<Research> researches = new HashSet<>();
            for (org.bukkit.inventory.ItemStack output : recipe.getOutputs()) {
                if (Items.isSlimefunItem(output)) {
                    Research research = Items.getSlimefunItem(output).getResearch();
                    if (research != null) {
                        researches.add(research);
                    }
                }
            }

            if (!researches.isEmpty()) {
                Optional<PlayerProfile> pp = PlayerProfile.find(p);
                if (!pp.isPresent()) {
                    FastMachines.getLocalization().sendMessage(p, "profile-not-loaded");
                    PlayerProfile.request(p);
                    return;
                }

                PlayerProfile profile = pp.get();
                for (Research research : researches) {
                    if (!profile.hasUnlocked(research)) {
                        FastMachines.getLocalization().sendMessage(p, "no-research");
                        return;
                    }
                }
            }
        }

        // check if the machine has enough energy
        if (FastMachines.getConfigService().getFmUseEnergy().getValue()) {
            int maxCraftByEnergy = machine.getCapacity() / machine.getEnergyPerUse();
            actualCrafts = Math.min(actualCrafts, maxCraftByEnergy);
            int energyNeeded = actualCrafts * machine.getEnergyPerUse();
            int currentEnergy = machine.getCharge(pos.toLocation());

            if (currentEnergy < energyNeeded) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-energy");
                return;
            }

            // deduct energy
            machine.setCharge(pos.toLocation(), currentEnergy - energyNeeded);
        }

        // deduct inputs
        for (RecipeChoice choice : recipe.getInputs()) {
            MachineUtils.consumeChoice(menu, choice, actualCrafts, BaseFastMachine.INPUT_SLOTS);
        }

        // add outputs
        for (int i = 0; i < actualCrafts; i++) {
            org.bukkit.inventory.ItemStack outputItem = recipe.getOutput(pos.getWorld()).clone();

            if (menu.fits(outputItem, BaseFastMachine.OUTPUT_SLOTS)) {
                menu.pushItem(outputItem, BaseFastMachine.OUTPUT_SLOTS);
            } else {
                InventoryUtil.push(p, outputItem);
            }
        }

        needUpdateMenu = true;
    }
}
