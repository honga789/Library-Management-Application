package dha.libapp.cache;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Factory class for creating and managing singleton instances of Cache subclasses.
 */
public class CacheFactory {

    /**
     * A map to store singleton instances of Cache subclasses.
     */
    private static final HashMap<Class<?>, Cache> instances = new HashMap<>();

    /**
     * Retrieves or creates a singleton instance of the specified Cache subclass.
     *
     * @param clazz the Cache subclass type of class Cache to retrieve or create
     * @param <T>   the type parameter extending Cache
     * @return the singleton instance of the specified Cache subclass
     * @throws RuntimeException if instance creation fails
     */
    public static <T extends Cache> T getCache(Class<T> clazz) {
        if (!instances.containsKey(clazz)) {
            try {
                // Use reflection to access enableInstanceCreation method to create instance.
                Method enableMethod = Cache.class.getDeclaredMethod("enableInstanceCreation");
                enableMethod.setAccessible(true);
                enableMethod.invoke(null);

                T instance = clazz.getDeclaredConstructor().newInstance();
                instances.put(clazz, instance);

                // Use reflection to access disableInstanceCreation method to prevent creating instance.
                Method disableMethod = Cache.class.getDeclaredMethod("disableInstanceCreation");
                disableMethod.setAccessible(true);
                disableMethod.invoke(null);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create cache instance for: " + clazz.getSimpleName(), e);
            }
        }
        return (T) instances.get(clazz);
    }

    /**
     * Clears all data in all cache instances.
     */
    public static void clearAllCache() {
        instances.values().forEach(Cache::clearAll);
    }
}