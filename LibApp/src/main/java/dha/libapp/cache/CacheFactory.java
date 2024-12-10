package dha.libapp.cache;

import java.lang.reflect.Method;
import java.util.HashMap;

public class CacheFactory {

    private static final HashMap<Class<?>, Cache> instances = new HashMap<>();

    public static <T extends Cache> T getCache(Class<T> clazz) {

        if (!instances.containsKey(clazz)) {
            try {
                Method enableMethod = Cache.class.getDeclaredMethod("enableInstanceCreation");
                enableMethod.setAccessible(true);
                enableMethod.invoke(null);

                T instance = clazz.getDeclaredConstructor().newInstance();
                instances.put(clazz, instance);

                Method disableMethod = Cache.class.getDeclaredMethod("disableInstanceCreation");
                disableMethod.setAccessible(true);
                disableMethod.invoke(null);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create cache instance for: " + clazz.getSimpleName(), e);
            }
        }

        return (T) instances.get(clazz);
    }

    public static void clearAllCache() {
        instances.values().forEach(Cache::clearAll);
    }
}
