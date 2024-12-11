package dha.libapp.services.members.tabs;

import dha.libapp.cache.CacheFactory;
import dha.libapp.cache.CacheItem;
import dha.libapp.cache.members.HomeTabCache;
import dha.libapp.controllers.members.tabs.MemberHomeTabController;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.RecommendationService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;

/**
 * Service class responsible for rendering the home tab for a member.
 * It provides functionality for rendering book recommendations and trending books.
 */
public class MemberHomeTabService {

    /**
     * Renders the recommended books for the currently logged-in member.
     * If the recommended books are cached, it uses the cache; otherwise, it fetches recommendations from the RecommendationService.
     * <p>
     * The service retrieves book recommendations based on the user's preferences and displays them on the home tab.
     */
    public static void renderRecommendationBooks() {
        MemberHomeTabController.getInstance().setLoadingRecommendationPaneVisible(true);

//        CacheItem<List<Book>> recommendedCache = HomeTabCache.getInstance().getRecommendationBookList();
        CacheItem<List<Book>> recommendedCache = CacheFactory.getCache(HomeTabCache.class).getRecommendationBookList();

        if (recommendedCache.isSaved()) {
            MemberHomeTabController.getInstance().setLoadingRecommendationPaneVisible(false);
            MemberHomeTabController.getInstance().renderRecommendationBooks(recommendedCache.getData());
        } else {
            RecommendationService.getRecommendedBooksForUser(SessionService.getInstance().getUser(), new RecommendationService.RecommendCallback() {
                @Override
                public void onSuccess(List<Book> recommendedBook) {
                    recommendedCache.setData(recommendedBook);

                    MemberHomeTabController.getInstance().setLoadingRecommendationPaneVisible(false);
                    MemberHomeTabController.getInstance().renderRecommendationBooks(recommendedBook);
                }
            }, 10);
        }
    }

    /**
     * Renders the top trending books.
     * If the trending books are cached, it uses the cache; otherwise, it fetches the top trending books from the database.
     * <p>
     * The service retrieves the top trending books and displays them on the home tab.
     */
    public static void renderTopTrendingBooks() {
        MemberHomeTabController.getInstance().setLoadingTrendingPaneVisible(true);

//        CacheItem<List<Book>> trendingBookCache = HomeTabCache.getInstance().getTopTrendingBookList();
        CacheItem<List<Book>> trendingBookCache = CacheFactory.getCache(HomeTabCache.class).getTopTrendingBookList();

        if (trendingBookCache.isSaved()) {
            MemberHomeTabController.getInstance().setLoadingTrendingPaneVisible(false);
            MemberHomeTabController.getInstance().renderTrendingBooks(trendingBookCache.getData());
        } else {
            BookSyncDAO.getTrendingBooksSync(10, new DAOExecuteCallback<>() {
                @Override
                public void onSuccess(List<Book> result) {
                    trendingBookCache.setData(result);

                    MemberHomeTabController.getInstance().setLoadingTrendingPaneVisible(false);
                    MemberHomeTabController.getInstance().renderTrendingBooks(result);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }
}

