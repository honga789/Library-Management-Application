package dha.libapp.cache;

import dha.libapp.cache.members.HomeTabCache;

public class TestCache {
    public static void main(String[] args) {
        Cache htc = CacheFactory.getCache(HomeTabCache.class);
        System.out.println(htc);
    }
}
