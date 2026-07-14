package net.guizhanss.fastmachines.implementation.tasks;

import java.util.Map;
import java.util.logging.Level;

import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.FMRegistry;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.implementation.items.machines.base.FastMachineCache;

public class FastMachineTickingTask {

    public FastMachineTickingTask() {
        for (BaseFastMachine fm : FMRegistry.ENABLED_FAST_MACHINES) {
            FastMachines.scheduler().repeatAsync(FastMachines.getConfigService().getFmTickRate().getValue(), () -> {
                for (Map.Entry<BlockPosition, FastMachineCache> entry : fm.getCaches().entrySet()) {
                    BlockPosition pos = entry.getKey();
                    try {
                        entry.getValue().tick();
                    } catch (Exception ex) {
                        FastMachines.log(Level.SEVERE, ex,
                            "An error has occurred while ticking " + fm.getClass().getSimpleName()
                                + " at " + pos.getWorld().getName() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                    }
                }
            });
        }
    }
}
