package net.guizhanss.fastmachines.libs.guizhanlib.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Java-8-safe port of the small slice of GuizhanLib's {@code FileUtil} this addon uses.
 */
public final class FileUtil {

    private FileUtil() {
    }

    public static <T> List<T> listJarEntries(File jarFile, BiPredicate<String, JarEntry> filter,
                                              BiFunction<String, JarEntry, T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (filter.test(entry.getName(), entry)) {
                    result.add(mapper.apply(entry.getName(), entry));
                }
            }
        }
        return result;
    }
}
