package dha.libapp.cache;

public abstract class Cache {
    private static boolean isCreatingInstance = false;

    protected Cache() {
        if (!Cache.isCreatingInstance) {
            throw new IllegalStateException("Cannot instantiate directly. Use CacheFactory.");
        }
    }

    private static void enableInstanceCreation() {
        Cache.isCreatingInstance = true;
    }

    private static void disableInstanceCreation() {
        Cache.isCreatingInstance = false;
    }

    public abstract void clearAll();
}
