package dha.libapp.services.members.tabs;

import dha.libapp.cache.Cache;
import dha.libapp.cache.members.HomeTabCache;
import dha.libapp.controllers.members.tabs.MemberHomeTabController;
import dha.libapp.models.Book;
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
            BookSyncDAO.getAllBookSync(new DAOExecuteCallback<List<Book>>() {
                @Override
                public void onSuccess(List<Book> result) {

                    System.out.println("Get " + result.size() + " books!");

                    recommendedCache.setData(result);

                    MemberHomeTabController.getInstance().setLoadingRecommendationPaneVisible(false);
                    MemberHomeTabController.getInstance().renderRecommendationBooks(result);
                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }
            });
        }
    }
}

