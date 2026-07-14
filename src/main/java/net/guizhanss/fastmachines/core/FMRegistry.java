package net.guizhanss.fastmachines.core;

import java.util.LinkedList;

import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;

public final class FMRegistry {

    public static final LinkedList<BaseFastMachine> ENABLED_FAST_MACHINES = new LinkedList<>();

    private FMRegistry() {
    }
}
