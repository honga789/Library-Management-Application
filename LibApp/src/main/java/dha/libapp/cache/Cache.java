package dha.libapp.cache;

/**
 * Abstract base class representing a cache for storing data loaded from a database.
 * The cache improves application performance by reducing database access latency.
 */
public abstract class Cache {

    /**
     * Flag to prevent direct instantiation of Cache. Must use CacheFactory to create instances.
     */
    private static boolean isCreatingInstance = false;

    /**
     * Protected constructor to ensure controlled instantiation through CacheFactory.
     * Throws an exception if instantiated directly.
     */
    protected Cache() {
        if (!Cache.isCreatingInstance) {
            throw new IllegalStateException("Cannot instantiate directly. Use CacheFactory.");
        }
    }

    /**
     * Enables instance creation by CacheFactory.
     */
    private static void enableInstanceCreation() {
        Cache.isCreatingInstance = true;
    }

    /**
     * Disables instance creation after the cache object has been created.
     */
    private static void disableInstanceCreation() {
        Cache.isCreatingInstance = false;
    }

    /**
     * Clears all data stored in the cache.
     */
    public abstract void clearAll();
}