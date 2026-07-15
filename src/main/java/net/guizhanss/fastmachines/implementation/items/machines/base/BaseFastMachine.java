package net.guizhanss.fastmachines.implementation.items.machines.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun5.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.FMRegistry;
import net.guizhanss.fastmachines.core.recipes.Recipe;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;
import net.guizhanss.fastmachines.libs.guizhanlib.slimefun.machines.MenuBlock;
import net.guizhanss.fastmachines.utils.constants.HeadTexture;

/**
 * The base fast machine.
 */
@SuppressWarnings("deprecation")
public abstract class BaseFastMachine extends MenuBlock implements EnergyNetComponent {

    public static final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        27, 28, 29, 30, 31, 32, 33, 34, 35,
    };
    public static final int[] OUTPUT_SLOTS = {
        27, 28, 29, 30, 31, 32, 33, 34, 35,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        0, 1, 2, 3, 4, 5, 6, 7, 8,
    };
    public static final int[] PREVIEW_SLOTS = {
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50,
    };

    public static final int SCROLL_UP_SLOT = 42;
    public static final int SCROLL_DOWN_SLOT = 51;
    public static final int CHOICE_SLOT = 52;
    public static final int CRAFT_SLOT = 53;
    public static final int INFO_SLOT = 43;
    public static final int ENERGY_SLOT = 44;

    static final int ITEMS_PER_PAGE = PREVIEW_SLOTS.length;

    static final ItemStack NO_ITEM = FastMachines.getLocalization().getItem("NO_ITEM", Material.BARRIER).item();
    static final ItemStack SCROLL_UP_ITEM =
        FastMachines.getLocalization().getItem("SCROLL_UP", HeadTexture.ARROW_UP.getTexture()).item();
    static final ItemStack SCROLL_DOWN_ITEM =
        FastMachines.getLocalization().getItem("SCROLL_DOWN", HeadTexture.ARROW_DOWN.getTexture()).item();
    static final ItemStack INFO_ITEM =
        FastMachines.getLocalization().getItem("INFO", HeadTexture.INFO.getTexture()).item();

    private final List<Recipe> recipes = new ArrayList<>();
    private final Map<BlockPosition, FastMachineCache> caches = new LinkedHashMap<>();
    private boolean recipeLocked = false;

    private final IntRangeSetting energyCapacitySetting;
    private final IntRangeSetting energyConsumptionSetting;

    protected BaseFastMachine(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType,
                              ItemStack[] recipe, int energyCapacity, int energyConsumption) {
        super(itemGroup, itemStack, recipeType, recipe);
        this.energyCapacitySetting = new IntRangeSetting(this, "energy-capacity", 0, energyCapacity, Integer.MAX_VALUE);
        this.energyConsumptionSetting = new IntRangeSetting(this, "energy-per-use", 0, energyConsumption, Integer.MAX_VALUE);
        addItemSetting(energyCapacitySetting, energyConsumptionSetting);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Map<BlockPosition, FastMachineCache> getCaches() {
        return caches;
    }

    public abstract Material getCraftItemMaterial();

    // default to use a material. override to use a custom item
    public ItemStack getCraftItem() {
        return FastMachines.getLocalization().getItem("CRAFT", getCraftItemMaterial()).item();
    }

    public abstract RecipeLoader getRecipeLoader();

    public void addRecipe(Recipe recipe) {
        if (recipeLocked) {
            throw new IllegalStateException("Cannot add recipes after recipes are locked");
        }
        recipes.add(recipe);
    }

    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    public int getEnergyPerUse() {
        return FastMachines.getConfigService().getFmUseEnergy().getValue() ? energyConsumptionSetting.getValue() : 0;
    }

    @Override
    public final int getCapacity() {
        return FastMachines.getConfigService().getFmUseEnergy().getValue() ? energyCapacitySetting.getValue() : 0;
    }

    @Override
    protected final void setup(BlockMenuPreset preset) {
        for (int slot : PREVIEW_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(CHOICE_SLOT, NO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(SCROLL_UP_SLOT, SCROLL_UP_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(SCROLL_DOWN_SLOT, SCROLL_DOWN_ITEM, ChestMenuUtils.getEmptyClickHandler());

        preset.addItem(CRAFT_SLOT, getCraftItem(), ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(
            ENERGY_SLOT,
            Items.edit(io.github.thebusybiscuit.slimefun5.utils.HeadTexture.ENERGY_CONNECTOR.getAsItemStack(), e -> {
                e.name(" ");
                e.lore("&8⇨ &e⚡ &7" + getEnergyPerUse() + " J"
                    + FastMachines.getLocalization().getString("lores.per-craft"));
            }),
            ChestMenuUtils.getEmptyClickHandler()
        );
    }

    @Override
    protected final int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected final int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    protected final void onNewInstance(BlockMenu menu, Block b) {
        BlockPosition pos = new BlockPosition(b);
        caches.put(pos, new FastMachineCache(this, menu));
    }

    @Override
    protected final void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        org.bukkit.Location loc = menu.getLocation();
        menu.dropItems(loc, INPUT_SLOTS);
        caches.remove(new BlockPosition(loc));
    }

    /**
     * Override this method to add custom preconditions for registration, like checking if a plugin is enabled.
     */
    public boolean registerPrecondition() {
        return true;
    }

    @Override
    public void register(SlimefunAddon addon) {
        if (!registerPrecondition()) {
            FastMachines.debug("Skipping registration of " + getId() + " due to precondition failure.");
            return;
        }
        FastMachines.debug("Registering " + getId() + "...");
        super.register(addon);
    }

    @Override
    public void postRegister() {
        super.postRegister();
        if (!isDisabled()) {
            FMRegistry.ENABLED_FAST_MACHINES.add(this);
        }
    }
}
