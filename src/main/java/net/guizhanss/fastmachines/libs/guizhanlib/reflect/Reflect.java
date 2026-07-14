package net.guizhanss.fastmachines.libs.guizhanlib.reflect;

import java.lang.reflect.Method;

/**
 * Java-8-safe port of GuizhanLib-kt's reflection helper.
 */
public final class Reflect {

    private Reflect() {
    }

    /**
     * Invokes a method of the given {@code name} on {@code target} via reflection, matching by name and
     * argument count only. Used to reach methods that only exist on some Bukkit API versions/implementations
     * (e.g. a shared {@code getResult()} that different {@code Recipe} subclasses only gained in newer API
     * versions) without a hard compile-time reference to a specific type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object target, String name, Object... args) {
        for (Method method : target.getClass().getMethods()) {
            if (method.getName().equals(name) && method.getParameterCount() == args.length) {
                try {
                    return (T) method.invoke(target, args);
                } catch (ReflectiveOperationException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
