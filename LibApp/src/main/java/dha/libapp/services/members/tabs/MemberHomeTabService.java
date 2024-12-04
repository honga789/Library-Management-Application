package dha.libapp.services.members.tabs;

import dha.libapp.cache.Cache;
import dha.libapp.cache.members.HomeTabCache;
import dha.libapp.controllers.members.tabs.MemberHomeTabController;
import dha.libapp.models.Book;
import dha.libapp.services.SessionService;
import dha.libapp.services.members.RecommendationService;
import dha.libapp.syncdao.BookSyncDAO;
import dha.libapp.syncdao.utils.DAOExecuteCallback;

import java.util.List;

public class MemberHomeTabService {
    public static void renderRecommendationBooks() {
        MemberHomeTabController.getInstance().setLoadingRecommendationPaneVisible(true);

        Cache<List<Book>> recommendedCache = HomeTabCache.getInstance().getRecommendationBookList();

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

    public static void renderTopTrendingBooks() {
        MemberHomeTabController.getInstance().setLoadingTrendingPaneVisible(true);

        Cache<List<Book>> trendingBookCache = HomeTabCache.getInstance().getTopTrendingBookList();

        if (trendingBookCache.isSaved()) {
            MemberHomeTabController.getInstance().setLoadingTrendingPaneVisible(false);
            MemberHomeTabController.getInstance().renderTrendingBooks(trendingBookCache.getData());
        } else {
            BookSyncDAO.getTrendingBooksSync(10, new DAOExecuteCallback<List<Book>>() {
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

